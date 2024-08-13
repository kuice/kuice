package io.github.kuice

import io.github.kuice.ktor.routes.Route

abstract class Registry<Value> {
    private val register: MutableList<Value> = mutableListOf()

    fun register(value: Value) = register.add(value)

    fun values() = register.toList()

    companion object {
        fun <T> create(): Registry<T> = object : Registry<T>() { }
    }
}
