package io.github.kuice

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.kuice.config.getOptional
import io.github.kuice.guice.getInstance
import io.github.kuice.ktor.plugins.BaseApplicationPluginWithRoutes
import io.github.kuice.ktor.plugins.BasePlugin
import io.github.kuice.ktor.plugins.PluginScope
import io.github.kuice.ktor.routes.InjectedRoute
import io.github.kuice.ktor.routes.PlainRoute
import io.github.kuice.ktor.routes.Route
import io.github.kuice.ktor.routes.RouteScope
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.Pipeline
import org.slf4j.LoggerFactory

internal val applicationLogger = LoggerFactory.getLogger("io.github.kuice.Application")

open class ApplicationScope(
    private val routeRegistry: Registry<Route>,
    private val pluginRegistry: Registry<BasePlugin<io.ktor.server.application.Application, *, *>>
) {
    fun routes(configure: RouteScope.() -> Unit) = configure(RouteScope(routeRegistry))
    fun plugins(configure: PluginScope.() -> Unit) = configure(PluginScope(pluginRegistry))
}

/**
 * ```kotlin
 * application {
 *     routes {
 *         get("/greeting") {
 *             call.respondText("Hello, world")
 *         }
 *     }
 * }
 * ```
 */
fun application(
    configure: ApplicationScope.() -> Unit,
) =
    Application.invoke(ConfigFactory.load(), configure)
        .start(wait = true)

class Application private constructor(
    private val config: Config,
    private val injector: Injector,
    private val configure: ApplicationScope.() -> Unit,
) {
    private val engine = injector.getInstance<ApplicationEngine>()

    private val routeRegistry = Registry.create<Route>()
    private val pluginRegistry = Registry.create<BasePlugin<io.ktor.server.application.Application, *, *>>()

    companion object {
        fun invoke(config: Config, loadApplication: ApplicationScope.() -> Unit): Application {
            val pluginModules = getModules(config, "ktor.pluginModules")
            val modules = getModules(config, "ktor.modules")

            val injector = Guice.createInjector(listOf(KtorGuiceModule(config)) + pluginModules + modules)

            return Application(config, injector, loadApplication)
        }

        private fun getModules(config: Config, path: String): List<Module> =
            (config.getOptional(path, config::getStringList) ?: emptyList())
                .map { Class.forName(it).kotlin.objectInstance }
                .filterIsInstance<Module>()
    }

    fun start(wait: Boolean) {
        val plugins = getPlugins(injector, config)

        engine.application.apply {
            plugins.forEach { plugin ->
                applicationLogger.debug("Installing plugin: ${plugin.javaClass.simpleName}")
                plugin.install(injector, this)
            }
        }

        configure(ApplicationScope(routeRegistry, pluginRegistry))

        engine.application.apply {
            pluginRegistry.values().forEach { plugin ->
                plugin.install(injector, this)
            }

            routing {
                routeRegistry.values().forEach {
                    when (it) {
                        is PlainRoute -> it.getRoute().invoke(this)
                        is InjectedRoute -> it.getRoute(injector).invoke(this)
                    }
                }

                plugins
                    .filterIsInstance<BaseApplicationPluginWithRoutes<*, *>>()
                    .forEach { it.setupRoutes(this) }
            }
        }

        engine.start(wait)
    }

    private fun getPlugins(injector: Injector, config: Config): List<BasePlugin<Pipeline<*, ApplicationCall>, *, *>> =
        (config.getOptional("ktor.plugins", config::getStringList) ?: emptyList())
            .map { Class.forName(it) }
            .map { injector.getInstance(it) }
            .filterIsInstance<BasePlugin<Pipeline<*, ApplicationCall>, *, *>>()
}
