group = "io.github.kuice"
version = "0.0.1"

plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":kuice-core"))

    implementation("ch.qos.logback:logback-classic:1.4.14")
}

kotlin {
    jvmToolchain(17)
}