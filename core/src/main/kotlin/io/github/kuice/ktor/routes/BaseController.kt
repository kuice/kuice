package io.github.kuice.ktor.routes

import io.ktor.server.application.call
import io.ktor.server.response.respond

interface BaseController<Handler> {
    fun request(handler: Handler): Handler = handler
}


/**
 * A helper interface for easily defining requests without having to specify the type
 * ```kotlin
 * object Controller: ApplicationController {
 *     // Without type
 *     val r1 = request {
 *         call.respond("Hello, World!")
 *     }
 *
 *     // With type
 *     val r2: RequestHandler = {
 *         call.respond("Hello, World!")
 *     }
 * }
 * ```
 */
interface ApplicationController : BaseController<RequestHandler>
