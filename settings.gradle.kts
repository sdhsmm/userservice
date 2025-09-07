rootProject.name = "userservice"
include(":jwt-auth-lib")
project(":jwt-auth-lib").projectDir = File("../jwt-auth-lib")
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
