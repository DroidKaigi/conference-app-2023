package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

@Suppress("unused")
class KmpAndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }
            kotlin {
                sourceSets.getByName("androidMain") {
                    val kspConfiguration = configurations["ksp"]
                    kspConfiguration.dependencies.add(
                        libs.library("daggerHiltAndroidCompiler").let {
                            DefaultExternalModuleDependency(
                                it.module.group,
                                it.module.name,
                                it.versionConstraint.requiredVersion
                            )
                        }
                    )
                    dependencies {
                        implementation(libs.library("daggerHiltAndroid"))
                        // https://issuetracker.google.com/issues/237567009
                        implementation(libs.library("androidxFragment"))
                    }
                }
            }

            dependencies {
                (listOf("CommonMainMetadata", "Android")).forEach {
                    add("ksp$it", libs.library("daggerHiltAndroid"))
                }
            }
        }
    }
}
