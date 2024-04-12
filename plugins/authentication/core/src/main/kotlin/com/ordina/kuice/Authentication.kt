package com.ordina.kuice

import com.ordina.kuice.ktor.routes.InjectedParentRoute
import com.ordina.kuice.ktor.routes.Route
import com.ordina.kuice.ktor.routes.RouteScope
import io.ktor.server.auth.authenticate

fun RouteScope.authenticate(
    vararg configurations: String? = arrayOf(null),
    optional: Boolean = false,
    f: RouteScope.() -> Unit,
) {
    val authenticatedRegistry = object : Registry<Route>() { }
    val childScope = RouteScope(authenticatedRegistry)

    f(childScope)

    fun getParentRoute(route: io.ktor.server.routing.Route, build: io.ktor.server.routing.Route.() -> Unit): io.ktor.server.routing.Route =
        route.authenticate(
            configurations = configurations,
            optional = optional,
            build = build,
        )

    registry.register(
        InjectedParentRoute(::getParentRoute, authenticatedRegistry.values()),
    )
}
