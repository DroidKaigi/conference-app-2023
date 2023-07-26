package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName

@Suppress("unused")
class KmpKtorfitPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kspGradlePlugin").get().get().pluginId)
                apply("de.jensklingenberg.ktorfit")
            }
            kotlin {
                android()
            }

            configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
                version = "1.4.1"
            }

            kotlin {
                sourceSets.get("commonMain").apply {
                    dependencies {
                        implementation(libs.findLibrary("ktorfitLib").get())
                    }
                }
            }
            dependencies {
                this.add(
                    "kspCommonMainMetadata",
                    "de.jensklingenberg.ktorfit:ktorfit-ksp:1.4.1"
                )
                this.add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.4.1")
                val iosSourceSets = listOf(
                    "IosArm64",
                    "IosX64",
                    "IosSimulatorArm64",
                )
                iosSourceSets.forEach {
                    this.add("ksp$it", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.4.1")
                }
            }
        }
    }
}