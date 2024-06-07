group = "io.github.kuice"
version = "0.0.1"

plugins {
    `java-library`
    `maven-publish`
    signing
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
    repositories {
        maven {
            credentials {
                username = project.findProperty("ossrh.username") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("ossrh.password") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            artifactId = "kuice-${project.name}"
            from(components["kotlin"])

            pom {
                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0-standalone.html"
                    }
                }

                developers {
                    developer {
                        id = "donovan"
                        name = "Donovan de Kuiper"
                        email = "donovan.de.kuiper@ordina.nl"
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications.findByName("maven"))
}
