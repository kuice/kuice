package io.github.kuice.ktor.routes

import io.ktor.server.application.ApplicationCall

typealias RequestHandler = suspend io.ktor.util.pipeline.PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit

interface Route
