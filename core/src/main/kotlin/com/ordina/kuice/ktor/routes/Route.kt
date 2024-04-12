package com.ordina.kuice.ktor.routes

import com.google.inject.Injector
import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Route as KRoute

typealias RequestHandler = suspend io.ktor.util.pipeline.PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit

interface Route
