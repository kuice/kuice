package com.ordina.example

import com.ordina.kuice.application

fun main() {
    application {
        routes {
            route("nested-route") {
                get<SimpleController>("/foo") { getFoo }
            }
        }
    }
}