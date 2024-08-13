package io.github.kuice.ktor.routes

import io.github.kuice.Registry
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

    fun post(path: String, requestHandler: RequestHandler) =
        registry.register(
            PlainBaseRoute(requestHandler) { handler -> { this.post(path, handler) } },
        )

    inline fun <reified T> put(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.put(path, handler) } }, T::class.java),
        )

    fun put(path: String, requestHandler: RequestHandler) =
        registry.register(
            PlainBaseRoute(requestHandler) { handler -> { this.put(path, handler) } },
        )

    inline fun <reified T> delete(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.delete(path, handler) } }, T::class.java),
        )

    fun delete(path: String, requestHandler: RequestHandler) =
        registry.register(
            PlainBaseRoute(requestHandler) { handler -> { this.delete(path, handler) } },
        )

    inline fun <reified T> patch(path: String, noinline getRequestHandler: T.() -> RequestHandler) =
        registry.register(
            InjectedBaseRoute(getRequestHandler, { handler -> { this.patch(path, handler) } }, T::class.java),
        )

    fun patch(path: String, requestHandler: RequestHandler) =
        registry.register(
            PlainBaseRoute(requestHandler) { handler -> { this.patch(path, handler) } },
        )


    fun route(path: String, f: RouteScope.() -> Unit) {
        val childRegistry = Registry.create<Route>()
        val childScope = RouteScope(childRegistry)

        f(childScope)

        fun getParentRoute(route: io.ktor.server.routing.Route, build: io.ktor.server.routing.Route.() -> Unit): io.ktor.server.routing.Route =
            route.route(path, build)

        registry.register(
            InjectedParentRoute(::getParentRoute, childRegistry.values()),
        )
    }
}
