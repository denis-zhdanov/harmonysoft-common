plugins {
    id("harmonysoft-library-conventions")
}

dependencies {
    implementation("org.quartz-scheduler:quartz:2.3.2")

    testImplementation(project(":harmonysoft-common-spring-test"))

    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Version.JACKSON}")
}