package com.ordina.example

import io.github.kuice.application

fun main() {
    io.github.kuice.application {
        routes {
            post<SimpleController>("/foo") { getX }
            get<SimpleController>("/bar") { getY }
        }
    }
}




