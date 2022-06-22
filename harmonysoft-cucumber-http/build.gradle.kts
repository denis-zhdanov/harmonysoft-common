plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":harmonysoft-test-common"))
    api(project(":harmonysoft-http-client"))

    implementation("io.cucumber:cucumber-java:${project.extra.get("version.cucumber")}")
    implementation("io.cucumber:cucumber-junit:${project.extra.get("version.cucumber")}")
    implementation("io.cucumber:cucumber-spring:${project.extra.get("version.cucumber")}")
}