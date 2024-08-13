plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))
    api("io.ktor:ktor-server-auth")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
