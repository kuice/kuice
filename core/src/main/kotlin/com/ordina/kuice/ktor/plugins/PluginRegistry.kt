package com.ordina.kuice.ktor.plugins

import com.ordina.kuice.Registry

object PluginRegistry : Registry<BaseRouteScopedPlugin<*, *>>()
