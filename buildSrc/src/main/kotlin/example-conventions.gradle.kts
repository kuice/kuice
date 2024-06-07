group = "io.github.kuice"
version = "0.0.1"

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))

    implementation("ch.qos.logback:logback-classic:1.4.14")
}

kotlin {
    jvmToolchain(17)
}