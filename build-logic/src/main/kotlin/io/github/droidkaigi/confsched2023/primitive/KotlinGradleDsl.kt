package io.github.droidkaigi.confsched2023.primitive

import com.android.build.gradle.TestedExtension
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import java.awt.AWTEventMulticaster.add
import java.util.Optional

fun DependencyHandlerScope.kapt(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("kapt", artifact.get())
}

fun TestedExtension.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

fun DependencyHandlerScope.ksp(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("ksp", artifact.get())
}

fun DependencyHandlerScope.kaptTest(
    artifact: Optional<Provider<MinimalExternalModuleDependency>>
) {
    add("kaptTest", artifact.get())
}