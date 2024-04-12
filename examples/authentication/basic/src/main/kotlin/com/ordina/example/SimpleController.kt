package com.ordina.example

import com.ordina.kuice.ktor.routes.ApplicationController
import com.ordina.kuice.ktor.routes.BaseController
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
        call.respond("foo")
    }
}
