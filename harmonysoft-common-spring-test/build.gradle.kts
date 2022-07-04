plugins {
    id("harmonysoft-library-conventions")
}

dependencies {
    api(project(":harmonysoft-common-spring"))
    api(project(":harmonysoft-common-test"))

    implementation("org.junit.jupiter:junit-jupiter:${Version.JUNIT}")
    implementation("org.springframework.boot:spring-boot-starter-test")
}