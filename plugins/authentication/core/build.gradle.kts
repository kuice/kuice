plugins {
    id("library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    api("io.ktor:ktor-server-auth")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}
