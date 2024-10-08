import org.jreleaser.model.Active
import org.jreleaser.model.Signing

group = "io.github.kuice"
version = "0.0.2-SNAPSHOT"

plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
    signing
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jreleaser")
    id("org.jetbrains.dokka")

}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.ktor:ktor-bom:2.3.12"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
}

val emptyJar = tasks.register<Jar>("emptyJar") {
    archiveAppendix.set("empty")
}

publishing {
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.kuice"

            from(components["kotlin"])

            artifact(emptyJar) {
                classifier = "javadoc"
            }

            artifact(emptyJar) {
                classifier = "sources"
            }

            pom {
                name = artifactId
                description = "Kuice is a set of libraries to easily integrate Guice with the Ktor framework"
                url = "https://github.com/kuice/kuice"
                inceptionYear = "2023"

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

                scm {
                    connection = "scm:git:git@github.com:kuice/kuice.git"
                    developerConnection = "scm:git:git@github.com:kuice/kuice.git"
                    url = "https://github.com/kuice/kuice"
                }
            }
        }
    }
}

jreleaser {
    gitRootSearch = true

    signing {
        active.set(Active.ALWAYS)
        setMode(Signing.Mode.COMMAND.name)
        armored = true
        verify = false

        passphrase = System.getenv("KUICE_GPG_PASSPHRASE")

        command {
            homeDir = System.getenv("KUICE_GPG_HOME") ?: "/Users/donovan/.gnupg"
        }
    }

    release {
        github {
            token = System.getenv("KUICE_GITHUB_TOKEN")
        }
    }

    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("build/staging-deploy")
                    username = System.getenv("KUICE_SONATYPE_USERNAME")
                    password = System.getenv("KUICE_SONATYPE_PASSWORD")
                    retryDelay = 30
                }
            }
        }
    }
}
