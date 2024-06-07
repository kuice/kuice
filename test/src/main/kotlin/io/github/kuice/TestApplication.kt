package io.github.kuice

import io.github.kuice.ktor.routes.Route
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class TestApplicationScope(override val routeRegistry: Registry<Route>) : ApplicationScope {
    val client: HttpClient = HttpClient(CIO)
}

fun testApplication(
    f: TestApplicationScope.() -> Unit,
) {
//    Application
//        .invoke(ConfigFactory.load(), { f() })
//        .start(wait = true)
}
