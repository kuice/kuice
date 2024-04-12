import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.implementation
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.testImplementation

group = "com.ordina.kuice"
version = "0.0.1"

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(jdkVersion = 17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "kuice-${project.name}"
            from(components["kotlin"])
        }
    }
}