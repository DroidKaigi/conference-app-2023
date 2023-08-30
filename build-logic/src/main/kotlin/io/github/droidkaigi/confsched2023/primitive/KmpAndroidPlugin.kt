package io.github.droidkaigi.confsched2023.primitive

import com.google.devtools.ksp.gradle.KspTaskMetadata
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

@Suppress("unused")
class KmpAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }
            kotlin {
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = "11"
                        }
                    }
                }
            }
            android {
                setupAndroid()
                sourceSets {
                    getByName("main") {
                        assets.srcDirs("src/androidMain/assets")
                        java.srcDirs("src/androidMain/kotlin", "src/commonMain/kotlin")
                        res.srcDirs("src/androidMain/res")
                    }
                    getByName("test") {
                        assets.srcDirs("src/androidUnitTest/assets")
                        java.srcDirs("src/androidUnitTest/kotlin", "src/commonTest/kotlin")
                        res.srcDirs("src/androidUnitTest/res")
                    }
                }
            }
            // https://slack-chats.kotlinlang.org/t/13166064/been-discovering-that-the-task-kspcommonmainkotlinmetadata-i#9a50fa1b-1ec5-47c2-9172-2a5780a1900e
            tasks.withType<KspTaskMetadata>().configureEach {
                notCompatibleWithConfigurationCache("Configuration cache not supported due to serialization")
            }
        }
    }
}
