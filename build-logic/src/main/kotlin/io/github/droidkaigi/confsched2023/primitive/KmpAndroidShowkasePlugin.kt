package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

@Suppress("unused")
class KmpAndroidShowkasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kspGradlePlugin").get().get().pluginId)
            }
            kotlin {
                sourceSets.getByName("androidMain") {
                    dependencies {
                        implementation(libs.findLibrary("showkaseRuntime").get())
                    }
                }
            }
            dependencies {
                this.add("kspAndroid", libs.findLibrary("showkaseProcessor").get())
            }
        }
    }
}
