plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.1")
}