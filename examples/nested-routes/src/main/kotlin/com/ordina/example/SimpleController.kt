package com.ordina.example

import com.typesafe.config.Config
import io.github.kuice.ktor.routes.ApplicationController
import io.ktor.server.application.call
import io.ktor.server.response.respond
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class SimpleController @Inject constructor(private val config: Config) : ApplicationController {
    val getFoo = request {
        call.respond("foo")
    }
}

