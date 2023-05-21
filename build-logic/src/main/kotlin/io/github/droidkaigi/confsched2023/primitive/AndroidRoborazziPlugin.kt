package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidRoborazziPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("io.github.takahirom.roborazzi")
            }
            android {
//                testOptions {
//                    unitTests {
//                        all {
//                        }
//                    }
//                }
            }
            dependencies {
                testImplementation(libs.findLibrary("androidxTestEspressoEspressoCore"))
                testImplementation(libs.findLibrary("junit"))
                testImplementation(libs.findLibrary("robolectric"))
                testImplementation(libs.findLibrary("androidxTestExtJunit"))
                testImplementation(libs.findLibrary("roborazzi"))
            }
        }
    }
}
