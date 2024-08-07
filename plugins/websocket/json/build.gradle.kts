plugins {
    id("library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":plugins:serialization:serialization-json"))
    implementation("io.ktor:ktor-serialization-kotlinx-json")
}
