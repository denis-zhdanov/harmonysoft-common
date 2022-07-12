plugins {
    id("harmonysoft-library-conventions")
}

dependencies {
    implementation("org.quartz-scheduler:quartz:2.3.2")

    testImplementation(project(":harmonysoft-common-test-spring"))

    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Version.JACKSON}")
}