plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":core"))

    api(project(":plugins:authentication:authentication-core"))

    testImplementation(project(":test"))
}
