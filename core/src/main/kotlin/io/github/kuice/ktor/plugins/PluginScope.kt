package io.github.kuice.ktor.plugins

import io.github.kuice.Registry
import io.ktor.server.application.Application
import io.ktor.server.application.Plugin

class PluginScope(private val registry: Registry<BasePlugin<Application, *, *>>) {

    fun <B: Any, F: Any> install(plugin: Plugin<Application, B, F>, configure: B.() -> Unit = {}) {
        val basePlugin : BaseApplicationPlugin<B, F> = object : BaseApplicationPlugin<B, F>(plugin, configure) { }

        registry.register(basePlugin)
    }
}