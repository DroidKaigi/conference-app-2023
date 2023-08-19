package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            //setupDetekt(extensions.getByType<DetektExtension>())

            dependencies {
                "detektPlugins"(libs.findLibrary("detekt-formatting").get())
                "detektPlugins"(libs.findLibrary("twitter-compose-rule").get())
            }
        }
    }
}
