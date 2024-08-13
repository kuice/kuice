plugins {
    id("library-conventions")
}


dependencies {
    implementation(project(":kuice-core"))
    implementation(project(":plugins:serialization:kuice-serialization-json"))
    implementation("io.ktor:ktor-serialization-kotlinx-json")
}
