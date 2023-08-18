package io.github.droidkaigi.confsched2023.primitive

import io.github.droidkaigi.confsched2023.primitive.Arch.ALL
import io.github.droidkaigi.confsched2023.primitive.Arch.ARM
import io.github.droidkaigi.confsched2023.primitive.Arch.X86
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

@Suppress("unused")
class KmpIosPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                when (activeArch) {
                    ARM -> iosSimulatorArm64()
                    X86 -> iosX64()
                    ALL -> {
                        iosArm64()
                        iosX64()
                        iosSimulatorArm64()
                    }
                }
                with(sourceSets) {
                    create("iosMain") {
                        dependsOn(getByName("commonMain"))
                        maybeCreate("iosArm64Main").dependsOn(this)
                        maybeCreate("iosX64Main").dependsOn(this)
                        maybeCreate("iosSimulatorArm64Main").dependsOn(this)
                    }

                    create("iosTest") {
                        dependsOn(getByName("commonTest"))
                        maybeCreate("iosArm64Test").dependsOn(this)
                        maybeCreate("iosX64Test").dependsOn(this)
                        maybeCreate("iosSimulatorArm64Test").dependsOn(this)
                    }
                }

                targets.withType<KotlinNativeTarget> {
                    // export kdoc to header file
                    // https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
                    compilations["main"].kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
                }
            }
        }
    }
}
