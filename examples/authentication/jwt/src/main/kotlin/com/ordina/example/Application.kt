package com.ordina.example

import com.ordina.kuice.authenticate
import com.ordina.kuice.application

fun main() {
    application {
        routes {
            authenticate {
                get<SimpleController>("/foo") { getX }
            }
        }
    }
}




