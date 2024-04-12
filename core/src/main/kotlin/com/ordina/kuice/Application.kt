package com.ordina.kuice

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.ordina.kuice.config.getOptional
import com.ordina.kuice.guice.getInstance
import com.ordina.kuice.ktor.plugins.BaseApplicationPluginWithRoutes
import com.ordina.kuice.ktor.plugins.BasePlugin
import com.ordina.kuice.ktor.routes.InjectedRoute
import com.ordina.kuice.ktor.routes.PlainRoute
import com.ordina.kuice.ktor.routes.Route
import com.ordina.kuice.ktor.routes.RouteScope
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.ApplicationCall
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.Pipeline
import org.slf4j.LoggerFactory

internal val applicationLogger = LoggerFactory.getLogger("com.ordina.kuice.Application")



interface ApplicationScope {
    val routeRegistry: Registry<Route>
    fun routes(f: RouteScope.() -> Unit) = f.invoke(RouteScope(routeRegistry))
}

fun application(
    f: ApplicationScope.() -> Unit,
) =
    Application
        .invoke(ConfigFactory.load(), f)
        .start(wait = true)


class Application private constructor(
    private val config: Config,
    private val injector: Injector,
    private val loadApplication: ApplicationScope.() -> Unit
) {
    private val engine = injector.getInstance<ApplicationEngine>()

    companion object {
        fun invoke(config: Config, loadApplication: ApplicationScope.() -> Unit) : Application {
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

        val routeRegistry = object : Registry<Route>() {}

        loadApplication(object : ApplicationScope {
            override val routeRegistry: Registry<Route> =routeRegistry
        })

        engine.application.apply {
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