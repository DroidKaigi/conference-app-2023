package io.github.droidkaigi.confsched2023.primitive

import io.github.droidkaigi.confsched2023.primitive.Arch.ALL
import io.github.droidkaigi.confsched2023.primitive.Arch.ARM
import io.github.droidkaigi.confsched2023.primitive.Arch.X86
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.util.Properties

@Suppress("unused")
class KmpIosPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                val activeArch = Arch.findByArch(
                    rootProject.layout.projectDirectory.file("local.properties").asFile.takeIf { it.exists() }
                        ?.let {
                            Properties().apply {
                                load(it.reader(Charsets.UTF_8))
                            }.getProperty("arch")
                        } ?: System.getenv("arch")
                )
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
                    val iosSourceSets = listOf(
                        "iosArm64",
                        "iosX64",
                        "iosSimulatorArm64",
                    )
                    create("iosMain") {
                        dependsOn(getByName("commonMain"))
                    }
                    iosSourceSets.forEach { iosSourceSet ->
                        maybeCreate(iosSourceSet + "Main") {
                            dependsOn(getByName("commonMain"))
                        }
                    }

                    create("iosTest") {
                        dependsOn(getByName("commonTest"))
                    }
                    iosSourceSets.forEach { iosSourceSet ->
                        maybeCreate(iosSourceSet + "Test") {
                            dependsOn(getByName("commonMain"))
                        }
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

enum class Arch(val arch: String?) {
    ARM("arm64"),
    X86("x86_64"),
    ALL(null),
    ;

    companion object {
        fun findByArch(arch: String?): Arch {
            return values().firstOrNull { it.arch == arch } ?: ALL
        }
    }
}
