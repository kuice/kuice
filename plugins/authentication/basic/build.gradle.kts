description = "Basic Authentication Plugin"

plugins {
    id("library-conventions")
}

dependencies {
    implementation(project(":kuice-core"))

    api(project(":plugins:authentication:kuice-authentication-core"))

    testImplementation(project(":kuice-test"))
}
