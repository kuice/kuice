package io.github.kuice

import io.github.kuice.ktor.plugins.BasePlugin
import io.github.kuice.ktor.routes.Route
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.server.application.Application

class TestApplicationScope(routeRegistry: Registry<Route>,
                           pluginRegistry: Registry<BasePlugin<Application, *, *>>
) : ApplicationScope(routeRegistry, pluginRegistry) {
    val client: HttpClient = HttpClient(CIO)
}

fun testApplication(
    f: suspend TestApplicationScope.() -> Unit,
) {

//    Application
//        .invoke(ConfigFactory.load(), { f() })
//        .start(wait = true)
}
