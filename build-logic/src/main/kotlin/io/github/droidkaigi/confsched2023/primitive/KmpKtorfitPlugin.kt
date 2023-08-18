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
                apply(libs.plugin("kspGradlePlugin").pluginId)
                apply("de.jensklingenberg.ktorfit")
            }

            configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
                version = libs.library("ktorfitKsp").versionConstraint.requiredVersion
            }

            kotlin {
                sourceSets["commonMain"].apply {
                    dependencies {
                        implementation(libs.library("ktorfitLib"))
                    }
                }
            }

            dependencies {
                val iosConfigs = when (activeArch) {
                    ARM -> listOf("IosSimulatorArm64")
                    X86 -> listOf("IosX64")
                    ALL -> listOf(
                        "IosArm64",
                        "IosX64",
                        "IosSimulatorArm64",
                    )
                }
                (listOf("CommonMainMetadata", "Android") + iosConfigs).forEach {
                    add("ksp$it", libs.library("ktorfitKsp"))
                }
            }
        }
    }
}
