package com.ordina.kuice.ktor.routes

interface BaseController<Handler> {
    fun request(handler: Handler): Handler = handler
}

interface ApplicationController : BaseController<RequestHandler>
