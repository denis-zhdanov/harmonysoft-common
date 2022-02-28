plugins {
    `java-library`
    kotlin("jvm") version "1.6.10"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.dokka") version "1.6.10"
    `maven-publish`
    if (System.getenv("CI_ENV").isNullOrBlank()) {
        signing
    }
}

object Version {
    const val APP = "1.0.1"
    const val JUNIT = "5.8.2"
}

group = "tech.harmonysoft"
version = Version.APP

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.JUNIT}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.JUNIT}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.dokkaJavadoc.configure {
    outputDirectory.set(buildDir.resolve("dokkaJavadoc"))
}

val dokkaJar by tasks.creating(Jar::class) {
    dependsOn(":dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from(buildDir.resolve("dokkaJavadoc"))
}

val sourceJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    archives(dokkaJar)
    archives(sourceJar)
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

publishing {
    publications {
        create<MavenPublication>("main") {
            artifactId = project.name
            from(components["java"])
            artifact(sourceJar)
            artifact(dokkaJar)

            pom {
                name.set(project.name)
                description.set("Miscellaneous JVM utility")
                url.set("http://harmonysoft-common.oss.harmonysoft.tech")

                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("http://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("denis")
                        name.set("Denis Zhdanov")
                        email.set("denzhdanov@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/denis-zhdanov/harmonysoft-common.git")
                    developerConnection.set("scm:git:git://github.com/denis-zhdanov/harmonysoft-common.git")
                    url.set("https://github.com/denis-zhdanov/harmonysoft-common")
                }
            }
        }
    }
}

if (System.getenv("CI_ENV").isNullOrBlank()) {
    signing {
        sign(publishing.publications["main"])
    }
}