package io.github.kuice.ktor.routes

interface PlainRoute : Route {
    fun getRoute(): io.ktor.server.routing.Route.() -> Unit
}

class PlainBaseRoute(
    private val requestHandler: RequestHandler,
    private val getRouteFromHandler: (RequestHandler) -> io.ktor.server.routing.Route.() -> Unit,
) : PlainRoute {
    override fun getRoute(): io.ktor.server.routing.Route.() -> Unit {
        return getRouteFromHandler(requestHandler)
    }
}
