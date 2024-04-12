package com.ordina.example

import com.ordina.kuice.application

fun main() {
    application {
        routes {
            post<SimpleController>("/foo") { getX }
            get<SimpleController>("/bar") { getY }
        }
    }
}




