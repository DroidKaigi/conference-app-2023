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
                testOptions {
                    unitTests {
                        all {
                            // -Pscreenshot to switch screenshot tests
                            it.filter {
                                if (project.hasProperty("screenshot")) {
                                    project.logger.lifecycle("Screenshot tests are included")
                                    includeTestsMatching("*ScreenshotTest*")
                                } else {
                                    project.logger.lifecycle("Screenshot tests are excluded")
                                    excludeTestsMatching("*ScreenshotTest*")
                                }
                            }
                        }
                    }
                }
            }
            dependencies {
                testImplementation(libs.findLibrary("roborazzi"))
            }
        }
    }
}
