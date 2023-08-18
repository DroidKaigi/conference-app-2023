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
                apply("com.google.devtools.ksp")
            }
            android {
                testOptions {
                    unitTests {
                        all {
                            it.maxParallelForks = Runtime.getRuntime().availableProcessors()
                            // -Pscreenshot to filter screenshot tests
                            it.useJUnit {
                                if (project.hasProperty("screenshot")) {
                                    project.logger.lifecycle("Screenshot tests are included")
                                    includeCategories("io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests")
                                }
                            }
                        }
                    }
                }
            }
            dependencies {
                testImplementation(libs.library("androidxTestEspressoEspressoCore"))
                testImplementation(libs.library("junit"))
                testImplementation(libs.library("robolectric"))
                testImplementation(libs.library("androidxTestExtJunit"))
                testImplementation(libs.library("roborazzi"))
                testImplementation(libs.library("roborazziCompose"))
                // For preview screenshot tests
                implementation(libs.library("showkaseRuntime"))
                ksp(libs.library("showkaseProcessor"))
            }
        }
    }
}
