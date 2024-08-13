plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
