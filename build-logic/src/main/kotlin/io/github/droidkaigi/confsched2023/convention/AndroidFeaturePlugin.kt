package io.github.droidkaigi.confsched2023.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

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
        }
    }
}
