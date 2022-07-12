plugins {
    id("harmonysoft-library-conventions")
}

dependencies {
    api(project(":harmonysoft-common-test"))

    implementation("io.cucumber:cucumber-java:${Version.CUCUMBER}")
}