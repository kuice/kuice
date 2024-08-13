plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))

    api(project(":plugins:authentication:kuice-authentication-core"))
    api("io.ktor:ktor-server-auth-jwt")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
