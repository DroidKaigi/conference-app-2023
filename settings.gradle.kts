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
    ":feature:about",
    ":feature:sessions",
    ":feature:contributors",
    ":core:designsystem",
    ":core:data",
    ":core:model",
    ":core:ui",
    ":core:testing",
    ":core:common",
)
