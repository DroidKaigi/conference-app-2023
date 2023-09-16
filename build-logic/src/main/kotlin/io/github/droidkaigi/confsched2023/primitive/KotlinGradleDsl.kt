package io.github.droidkaigi.confsched2023.primitive

import com.android.build.gradle.TestedExtension
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun DependencyHandlerScope.kapt(
    artifact: MinimalExternalModuleDependency,
) {
    add("kapt", artifact)
}

fun TestedExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun DependencyHandlerScope.ksp(
    artifact: MinimalExternalModuleDependency,
) {
    add("ksp", artifact)
}

fun DependencyHandlerScope.kspTest(
    artifact: MinimalExternalModuleDependency,
) {
    add("kspTest", artifact)
}
