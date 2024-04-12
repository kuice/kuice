plugins {
    id("library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    // Reflection
    implementation(kotlin("reflect"))

    // KTOR
    api("io.ktor:ktor-server-core-jvm:2.3.7")
    api("io.ktor:ktor-server-host-common-jvm:2.3.7")
    api("io.ktor:ktor-server-netty-jvm:2.3.7")

    // Dependency Injection
    api("com.google.inject:guice:7.0.0")

    // Configuration
    api("com.typesafe:config:1.4.3")
}
