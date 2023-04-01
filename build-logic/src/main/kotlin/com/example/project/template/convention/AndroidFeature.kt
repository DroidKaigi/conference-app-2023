package com.example.project.template.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeature : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.example.primitive.android")
                apply("com.example.primitive.android.compose")
                apply("com.example.primitive.android.hilt")
                apply("com.example.primitive.spotless")
            }
        }
    }
}