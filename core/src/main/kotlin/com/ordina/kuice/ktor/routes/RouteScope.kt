package com.ordina.kuice.ktor.routes

import com.ordina.kuice.Registry
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

class RouteScope(val registry: Registry<Route>) {

    inline fun <reified T> get(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.get(path, handler) } }, T::class.java),
        )

    fun get(path: String, requestHandler: RequestHandler) =
        registry.register(
            PlainBaseRoute(requestHandler) { handler -> { this.get(path, handler) } },
        )

    inline fun <reified T> post(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.post(path, handler) } }, T::class.java),
        )

    inline fun <reified T> put(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.put(path, handler) } }, T::class.java),
        )

    inline fun <reified T> delete(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.delete(path, handler) } }, T::class.java),
        )

    inline fun <reified T> patch(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.patch(path, handler) } }, T::class.java),
        )

    fun route(path: String, f: RouteScope.() -> Unit) {
        val childRegistry = object : Registry<Route>() { }
        val childScope = RouteScope(childRegistry)

        f(childScope)

        fun getParentRoute(route: io.ktor.server.routing.Route, build: io.ktor.server.routing.Route.() -> Unit): io.ktor.server.routing.Route =
            route.route(path, build)

        registry.register(
            InjectedParentRoute(::getParentRoute, childRegistry.values()),
        )
    }
}