package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

@Suppress("unused")
class KmpAndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }
            kotlin {
                sourceSets.getByName("androidMain") {
                    val kaptConfiguration = configurations["kapt"]
                    kaptConfiguration.dependencies.add(
                        libs.findLibrary("daggerHiltAndroidCompiler").map {
                            DefaultExternalModuleDependency(
                                it.get().module.group,
                                it.get().module.name,
                                it.get().versionConstraint.requiredVersion
                            )
                        }.get()
                    )
                    dependencies {
                        implementation(libs.findLibrary("daggerHiltAndroid").get())
                        // https://issuetracker.google.com/issues/237567009
                        implementation(libs.findLibrary("androidxFragment").get())
                    }
                }
            }
            extensions.configure<KaptExtension> {
                correctErrorTypes = true
            }
        }
    }
}
