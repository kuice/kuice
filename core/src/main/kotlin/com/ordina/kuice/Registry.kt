package com.ordina.kuice

abstract class Registry<Value> {
    private val register: MutableList<Value> = mutableListOf()

    fun register(value: Value) = register.add(value)

    fun values() = register.toList()
}
