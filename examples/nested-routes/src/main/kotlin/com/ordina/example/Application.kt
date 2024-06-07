package com.ordina.example

fun main() {
    io.github.kuice.application {
        routes {
            route("nested-route") {
                get<SimpleController>("/foo") { getFoo }
            }
        }
    }
}