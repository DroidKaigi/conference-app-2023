package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("unused")
class KmpIosPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                val isCD = System.getenv()["CD"]?.toBoolean() ?: false
                val isCI = System.getenv()["CI"]?.toBoolean() ?: false
                val iosMain = sourceSets.create("iosMain") {
                    dependsOn(sourceSets.getByName("commonMain"))
                }
                // make build speed faster on CD
                if (isCD) {
                    iosArm64()
                    sourceSets.getByName("iosArm64Main") {
                        dependsOn(iosMain)
                    }
                // make build speed faster on CI
                } else if (isCI) {
                    iosX64()
                    sourceSets.getByName("iosX64Main") {
                        dependsOn(iosMain)
                    }
                } else {
                    iosArm64()
                    iosSimulatorArm64()
                    iosX64()
                    sourceSets.getByName("iosArm64Main") {
                        dependsOn(iosMain)
                    }
                    sourceSets.getByName("iosSimulatorArm64Main") {
                        dependsOn(iosMain)
                    }
                    sourceSets.getByName("iosX64Main") {
                        dependsOn(iosMain)
                    }
                }
                targets.withType<KotlinNativeTarget>().configureEach {
                    binaries.all {
                        binaryOptions["memoryModel"] = "experimental"
                    }
                }
            }
        }
    }
}
