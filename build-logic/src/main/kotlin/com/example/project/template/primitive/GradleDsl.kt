package com.example.project.template.primitive

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.*

fun DependencyHandlerScope.implementation(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("implementation", artifact.get())
}

fun DependencyHandlerScope.debugImplementation(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("debugImplementation", artifact.get())
}

fun DependencyHandlerScope.androidTestImplementation(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("androidTestImplementation", artifact.get())
}

fun DependencyHandlerScope.testImplementation(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("testImplementation", artifact.get())
}

private fun DependencyHandlerScope.api(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("api", artifact.get())
}

fun DependencyHandlerScope.kapt(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("api", artifact.get())
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
