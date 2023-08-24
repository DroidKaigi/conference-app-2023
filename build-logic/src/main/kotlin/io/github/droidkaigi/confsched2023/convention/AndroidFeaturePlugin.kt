package io.github.droidkaigi.confsched2023.convention

import io.github.droidkaigi.confsched2023.primitive.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("droidkaigi.primitive.android")
                apply("droidkaigi.primitive.android.kotlin")
                apply("droidkaigi.primitive.android.compose")
                apply("droidkaigi.primitive.android.hilt")
                apply("droidkaigi.primitive.android.roborazzi")
                apply("droidkaigi.primitive.kover")
                apply("droidkaigi.primitive.detekt")
            }

            dependencies {
                implementation(project(":core:model"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:ui"))
            }
        }
    }
}
