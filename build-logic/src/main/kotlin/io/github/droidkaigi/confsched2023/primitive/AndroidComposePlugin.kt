package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            android {
                buildFeatures.compose = true
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findLibrary("composeCompiler").get()
                        .get().versionConstraint.requiredVersion
                }
            }
            dependencies {
                implementation(libs.findLibrary("androidxCoreKtx"))
                implementation(libs.findLibrary("composeUi"))
                implementation(libs.findLibrary("composeMaterial"))
                implementation(libs.findLibrary("composeUiToolingPreview"))
                implementation(libs.findLibrary("androidxLifecycleLifecycleRuntimeKtx"))
                implementation(libs.findLibrary("androidxActivityActivityCompose"))
                testImplementation(libs.findLibrary("junit"))
                testImplementation(libs.findLibrary("androidxTestExtJunit"))
                testImplementation(libs.findLibrary("androidxTestEspressoEspressoCore"))
                testImplementation(libs.findLibrary("composeUiTestJunit4"))
                debugImplementation(libs.findLibrary("composeUiTooling"))
                debugImplementation(libs.findLibrary("composeUiTestManifest"))
                lintChecks(libs.findLibrary("composeLintCheck"))
            }
        }
    }
}
