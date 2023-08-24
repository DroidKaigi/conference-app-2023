package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure

fun DependencyHandlerScope.implementation(
    artifact: MinimalExternalModuleDependency,
) {
    add("implementation", artifact)
}

fun DependencyHandlerScope.implementation(
    project: Project
) {
    add("implementation", project)
}

fun DependencyHandlerScope.debugImplementation(
    artifact: MinimalExternalModuleDependency,
) {
    add("debugImplementation", artifact)
}

fun DependencyHandlerScope.androidTestImplementation(
    artifact: MinimalExternalModuleDependency,
) {
    add("androidTestImplementation", artifact)
}

fun DependencyHandlerScope.testImplementation(
    artifact: MinimalExternalModuleDependency,
) {
    add("testImplementation", artifact)
}

fun DependencyHandlerScope.implementationPlatform(
    artifact: MinimalExternalModuleDependency,
) {
    add("implementation", platform(artifact))
}

fun DependencyHandlerScope.lintChecks(
    artifact: MinimalExternalModuleDependency,
) {
    add("lintChecks", artifact)
}

private fun DependencyHandlerScope.api(
    artifact: MinimalExternalModuleDependency,
) {
    add("api", artifact)
}

fun Project.java(action: JavaPluginExtension.() -> Unit) {
    extensions.configure(action)
}
