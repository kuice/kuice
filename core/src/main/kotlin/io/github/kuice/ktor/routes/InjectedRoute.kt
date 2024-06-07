package io.github.kuice.ktor.routes

import com.google.inject.Injector

interface InjectedRoute : Route {
    fun getRoute(injector: Injector): io.ktor.server.routing.Route.() -> Unit
}

class InjectedBaseRoute<Controller, RequestHandler>(
    private val getRequestHandler: Controller.() -> RequestHandler,
    private val getRouteFromHandler: (RequestHandler) -> io.ktor.server.routing.Route.() -> Unit,
    private val clazz: Class<Controller>,
) : InjectedRoute {
    override fun getRoute(injector: Injector): io.ktor.server.routing.Route.() -> Unit {
        val controller = injector.getInstance(clazz)
        val requestHandler = getRequestHandler.invoke(controller)

        return getRouteFromHandler(requestHandler)
    }
}

class InjectedParentRoute(
    private val parentRoute: (io.ktor.server.routing.Route, io.ktor.server.routing.Route.() -> Unit) -> Unit,
    private val childrenRoutes: List<Route>,
) : InjectedRoute {

    override fun getRoute(injector: Injector): io.ktor.server.routing.Route.() -> Unit = {
        parentRoute.invoke(this) {
            childrenRoutes.forEach {
                when (it) {
                    is PlainRoute -> it.getRoute().invoke(this)
                    is InjectedRoute -> it.getRoute(injector).invoke(this)
                }
            }
        }
    }
}
