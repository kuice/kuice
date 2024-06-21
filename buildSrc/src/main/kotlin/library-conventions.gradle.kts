import org.jreleaser.model.Active

group = "io.github.kuice"
version = "0.0.1"

plugins {
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jreleaser")
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

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }

    publications {
        create<MavenPublication>("maven") {
            artifactId = "kuice-${project.name}"
            from(components["kotlin"])

            pom {
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
            }
        }
    }
}

jreleaser {
    signing {
        active.set(Active.ALWAYS)
        armored = true
    }

    deploy {
        maven {
            mavenCentral {
                create("maven-central") {
                    active.set(Active.ALWAYS)
                    url.set("https://s01.oss.sonatype.org/service/local")
                }
            }
        }
    }
}
