plugins {
    id("example-conventions")
    kotlin("plugin.serialization") version "1.9.21"
}

dependencies {
    implementation(project(":plugins:serialization:serialization-json"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
}
