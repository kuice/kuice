plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":core"))
    implementation("io.ktor:ktor-client-cio:2.3.9")

    api("io.ktor:ktor-client-core:2.3.9")
}
