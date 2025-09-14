rootProject.name = "userservice"
include(":jwt-auth-lib")
project(":jwt-auth-lib").projectDir = File("../jwt-auth-lib")
include(":auth-api")
project(":auth-api").projectDir = File("../auth-api")
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
