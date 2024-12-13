pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://jogamp.org/deployment/maven")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
    }
}

rootProject.name = "Im Not Paying Translation Apis"
include(":app")
include(":Translator-Core")
include(":Translator-Android")
include(":Translator-Desktop")
include(":desktopApp")
