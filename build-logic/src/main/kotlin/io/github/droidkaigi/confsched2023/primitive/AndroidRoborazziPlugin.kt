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
                testImplementation(libs.findLibrary("androidxTestEspressoEspressoCore"))
                testImplementation(libs.findLibrary("junit"))
                testImplementation(libs.findLibrary("robolectric"))
                testImplementation(libs.findLibrary("androidxTestExtJunit"))
                testImplementation(libs.findLibrary("roborazzi"))
                testImplementation(libs.findLibrary("roborazziCompose"))
                // For preview screenshot tests
                implementation(libs.findLibrary("showkaseRuntime"))
                ksp(libs.findLibrary("showkaseProcessor"))
            }
        }
    }
}
