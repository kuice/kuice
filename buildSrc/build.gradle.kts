plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("io.ktor.plugin:plugin:2.3.12")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.1")
    implementation("org.jreleaser:jreleaser-gradle-plugin:1.12.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
}
