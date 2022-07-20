plugins {
    id("harmonysoft-library-conventions")
}

dependencies {
    api(project(":harmonysoft-common"))

    api("com.github.jsqlparser:jsqlparser:${Version.JSQL_PARSER}")
}