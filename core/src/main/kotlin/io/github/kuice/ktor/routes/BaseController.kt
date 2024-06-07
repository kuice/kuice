package io.github.kuice.ktor.routes

interface BaseController<Handler> {
    fun request(handler: Handler): Handler = handler
}

interface ApplicationController : BaseController<RequestHandler>
