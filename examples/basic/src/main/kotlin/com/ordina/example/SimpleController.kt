package com.ordina.example

import io.github.kuice.ktor.routes.ApplicationController
import io.github.kuice.ktor.routes.BaseController
import com.typesafe.config.Config
import io.ktor.server.application.call
import io.ktor.server.request.*
import io.ktor.server.response.respond
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.serialization.Serializable

@Singleton
class SimpleController @Inject constructor(private val config: Config) : ApplicationController {
    val getX = request {
        call.receive<RequestInput>()
        call.respond("foo")
    }

    val getY = request {
        call.respond("bar")
    }
}

@Serializable
data class RequestInput(val s: String, val i: Int)