plugins {
    id("library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    api(project(":plugins:authentication:authentication-core"))
    api("io.ktor:ktor-server-auth-jwt")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
