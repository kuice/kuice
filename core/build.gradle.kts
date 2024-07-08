import org.jetbrains.kotlin.utils.findIsInstanceAnd

description = "The core library for integrating KTOR with Guice"

plugins {
    id("library-conventions")
}

dependencies {
    // Reflection
    implementation(kotlin("reflect"))

    // KTOR
    api("io.ktor:ktor-server-core-jvm")
    api("io.ktor:ktor-server-host-common-jvm")
    api("io.ktor:ktor-server-netty-jvm")

    // Dependency Injection
    api("com.google.inject:guice:7.0.0")

    // Configuration
    api("com.typesafe:config:1.4.3")
}


