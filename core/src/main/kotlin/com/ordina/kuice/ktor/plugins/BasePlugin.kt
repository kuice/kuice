package com.ordina.kuice.ktor.plugins

import com.google.inject.Injector
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.Plugin
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import io.ktor.util.pipeline.Pipeline


interface BasePlugin<TPipeline : Pipeline<*, ApplicationCall>, TConfiguration : Any, TPlugin : Any>  {
    fun install(pipeline: TPipeline)
    fun install(injector: Injector, pipeline: TPipeline) = install(pipeline)
}

abstract class BaseApplicationPlugin<TConfiguration : Any, TPlugin : Any>(
    private val plugin: Plugin<Application, TConfiguration, TPlugin>,
    private val configure: TConfiguration.() -> Unit,
) : BasePlugin<Application, TConfiguration, TPlugin> {
    override fun install(pipeline: Application) {
        pipeline.install(plugin, configure)
    }
}

abstract class BaseApplicationPluginWithRoutes<TConfiguration : Any, TPlugin : Any>(
    private val plugin: Plugin<Application, TConfiguration, TPlugin>,
    private val configure: TConfiguration.() -> Unit,
) : BasePlugin<Application, TConfiguration, TPlugin> {
    override fun install(pipeline: Application) {
        pipeline.install(plugin, configure)
    }

    abstract fun setupRoutes(routing: Routing)
}

abstract class BaseRouteScopedPlugin<TConfiguration : Any, TPlugin : Any>(
    private val plugin: Plugin<ApplicationCallPipeline, TConfiguration, TPlugin>,
    private val configure: TConfiguration.() -> Unit,
) : BasePlugin<ApplicationCallPipeline, TConfiguration, TPlugin> {
    override fun install(pipeline: ApplicationCallPipeline) {
        pipeline.install(plugin, configure)
    }
}

abstract class BaseApplicationPluginWithInjector<TConfiguration : Any, TPlugin : Any>(
    private val plugin: Plugin<Application, TConfiguration, TPlugin>,
    private val configure: (Injector) -> TConfiguration.() -> Unit,
) : BasePlugin<Application, TConfiguration, TPlugin> {

    override fun install(pipeline: Application) { }

    override fun install(injector: Injector, pipeline: Application,) {
        pipeline.install(plugin, configure(injector))
    }
}
