package io.github.droidkaigi.confsched2023.primitive

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            setupDetekt(extensions.getByType<DetektExtension>())

            dependencies {
                "detektPlugins"(libs.findLibrary("detektFormatting").get())
            }
        }
    }
}
