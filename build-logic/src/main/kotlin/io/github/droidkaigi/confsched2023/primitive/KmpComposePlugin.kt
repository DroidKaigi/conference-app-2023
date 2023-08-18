package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get

@Suppress("unused")
class KmpComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.compose")
            }
            android {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.version("composeCompiler")
                }
            }
            val compose = extensions.get("compose") as org.jetbrains.compose.ComposeExtension
            kotlin {
                with(sourceSets) {
                    getByName("commonMain").apply {
                        dependencies {
                            implementation(compose.dependencies.runtime)
                            implementation(compose.dependencies.foundation)
                            implementation(compose.dependencies.material3)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.dependencies.components.resources)
                        }
                    }
                    getByName("androidMain").apply {
                        dependencies {
                            implementation(libs.library("androidxActivityActivityCompose"))
                            implementation(libs.library("androidxLifecycleLifecycleRuntimeKtx"))
                            implementation(libs.library("composeUiToolingPreview"))
                        }
                    }
                }
            }

        }
    }
}
