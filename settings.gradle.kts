pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "bookstore"
include("app")
include(":core:database")
include(":core:network")
include(":core:data")
include(":core:model")
include(":core:common")
include(":core:domain")
include(":feature:books")
include(":core:designsystem")
include(":feature:bookdetails")
include(":feature:cart")
include(":core:navigation")
