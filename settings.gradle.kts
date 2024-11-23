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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ZimranGithub"
include(":app")
include(":core")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":auth:di")
include(":gitrepos:data")
include(":gitrepos:presentation")
include(":gitrepos:di")
include(":gitrepos:domain")
