plugins {
    `java-library`
    kotlin("jvm")
    id("org.springframework.boot")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

group = "tech.harmonysoft"
version = Version.APP

repositories {
    mavenCentral()
}

tasks.getByName("bootJar").enabled = false

dependencies {
    api("tech.harmonysoft:inpertio-client-jvm:${Version.INPERTIO}")
    api("javax.inject:javax.inject:${Version.INJECT}")

    implementation("com.google.guava:guava:${Version.GUAVA}")
    implementation("org.springframework.boot:spring-boot-starter")

    testImplementation("org.assertj:assertj-core:${Version.ASSERTJ}")
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.JUNIT}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.JUNIT}")
}