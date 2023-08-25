package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpNativeCoroutinesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("kspGradlePlugin").pluginId)
                apply("com.rickclephas.kmp.nativecoroutines")
            }
            kotlin {
                sourceSets.all {
                    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
                }
            }
        }
    }
}
