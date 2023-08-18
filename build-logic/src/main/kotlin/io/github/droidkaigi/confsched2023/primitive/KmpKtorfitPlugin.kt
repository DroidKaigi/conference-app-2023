package io.github.droidkaigi.confsched2023.primitive

import io.github.droidkaigi.confsched2023.primitive.Arch.ALL
import io.github.droidkaigi.confsched2023.primitive.Arch.ARM
import io.github.droidkaigi.confsched2023.primitive.Arch.X86
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

@Suppress("unused")
class KmpKtorfitPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kspGradlePlugin").get().get().pluginId)
                apply("de.jensklingenberg.ktorfit")
            }
            kotlin {
                androidTarget()
            }

            configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
                version = "1.5.0"
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
                    "de.jensklingenberg.ktorfit:ktorfit-ksp:1.5.0"
                )
                this.add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.5.0")
                val iosConfigs = when (activeArch) {
                    ARM -> listOf("IosSimulatorArm64")
                    X86 -> listOf("IosX64")
                    ALL -> listOf(
                        "IosArm64",
                        "IosX64",
                        "IosSimulatorArm64",
                    )
                }
                iosConfigs.forEach {
                    this.add("ksp$it", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.5.0")
                }
            }
        }
    }
}
