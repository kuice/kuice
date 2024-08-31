plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))
    implementation("io.ktor:ktor-client-cio")

    api("io.ktor:ktor-client-core")
}
