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
                    kotlinCompilerExtensionVersion = libs.findLibrary("composeCompiler").get()
                        .get().versionConstraint.requiredVersion
                }
            }
            val compose = extensions.get("compose") as org.jetbrains.compose.ComposeExtension
            kotlin {
                with(sourceSets) {
                    get("commonMain").apply {
                        dependencies {
                            implementation(compose.dependencies.runtime)
                            implementation(compose.dependencies.foundation)
                            implementation(compose.dependencies.material3)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.dependencies.components.resources)
                        }
                    }
                    get("androidMain").apply {
                        dependencies {
                            implementation(libs.findLibrary("androidxActivityActivityCompose").get())
                            implementation(libs.findLibrary("androidxLifecycleLifecycleRuntimeKtx").get())
                        }
                    }
                }
            }

        }
    }
}
