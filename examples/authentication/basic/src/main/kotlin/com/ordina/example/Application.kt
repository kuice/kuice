package com.ordina.example

import io.github.kuice.authenticate

fun main() {
    io.github.kuice.application {
        routes {
            authenticate {
                get<SimpleController>("/foo") { getX }
            }
        }
    }
}




