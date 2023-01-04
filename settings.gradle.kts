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
include(":core:ui")
include(":core:navigation")
include(":feature:books")
include(":feature:bookdetails")
include(":feature:cart")
include(":feature:preferences")
include(":feature:editsubjects")
include(":feature:subjectbooks")
include(":feature:bookmarks")
include(":core:datastore")
include(":feature:languagepicker")
include(":core:filestore")
include(":feature:payments")
include(":feature:auth")
