package io.github.kuice.guice

import com.google.inject.Injector

internal inline fun <reified T> Injector.getInstance(): T = getInstance(T::class.java)
