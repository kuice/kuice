package io.github.kuice.ktor.plugins

import io.github.kuice.Registry

object PluginRegistry : Registry<BaseRouteScopedPlugin<*, *>>()
