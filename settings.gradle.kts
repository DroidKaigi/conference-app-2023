pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()

    }
}
rootProject.name = "conference-app-2023"
include(
    ":app-android",
    ":app-ios-shared",
    ":feature:main",
    ":feature:sessions",
    ":feature:about",
    ":feature:sponsors",
    ":feature:floor-map",
    ":feature:contributors",
    ":core:designsystem",
    ":core:data",
    ":core:model",
    ":core:ui",
    ":core:testing",
    ":core:common",
)
