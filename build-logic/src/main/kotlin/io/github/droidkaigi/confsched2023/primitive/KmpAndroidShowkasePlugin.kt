package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class KmpAndroidShowkasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("kspGradlePlugin").pluginId)
            }
            kotlin {
                sourceSets.getByName("androidMain") {
                    dependencies {
                        implementation(libs.library("showkaseRuntime"))
                    }
                }
            }
            dependencies {
                add("kspAndroid", libs.library("showkaseProcessor"))
            }
        }
    }
}
