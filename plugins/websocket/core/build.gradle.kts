plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))
    api("io.ktor:ktor-server-websockets-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
