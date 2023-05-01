package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }
            kotlin {
                android()
            }
            android {
                setupAndroid()
                sourceSets {
                    getByName("main") {
                        setRoot("src/androidMain")
                        assets.srcDirs(file("src/androidMain/assets"))
                        manifest.srcFile("src/androidMain/AndroidManifest.xml")
                        java.srcDirs(file("src/androidMain/kotlin"))
                        res.srcDirs(file("src/androidMain/res"))
                    }
                    // FIXME: Without this, kaptDebugKotlinAndroid can't see commonMain
                    // If you can run ./gradlew kaptDebugKotlinAndroid without this,
                    // please delete this
                    sourceSets.all {
                        java.srcDir("src/commonMain/kotlin")
                    }
                }

                kotlinOptions {
                    jvmTarget = "11"
                }
            }
        }
    }
}
