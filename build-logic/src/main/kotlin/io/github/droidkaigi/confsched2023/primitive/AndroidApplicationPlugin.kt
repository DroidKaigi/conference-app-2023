package io.github.droidkaigi.confsched2023.primitive

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            java {
                toolchain.languageVersion.set(JavaLanguageVersion.of(17))
            }

            androidApplication {
                namespace?.let {
                    this.namespace = it
                }
                compileSdk = 33
                defaultConfig {
                    minSdk = 23
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                    isCoreLibraryDesugaringEnabled = true
                }

                kotlinOptions {
                    // Treat all Kotlin warnings as errors (disabled by default)
                    allWarningsAsErrors = properties["warningsAsErrors"] as? Boolean ?: false

                    freeCompilerArgs = freeCompilerArgs + listOf(
//              "-opt-in=kotlin.RequiresOptIn",
                        // Enable experimental coroutines APIs, including Flow
//              "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    )
                }

                dependencies {
                    add("coreLibraryDesugaring", libs.findLibrary("androidDesugarJdkLibs").get())
                }

                defaultConfig.targetSdk = 32
                packagingOptions {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                lint {
                    // shell friendly
                    val filename = target.getDisplayName().replace(":", "_").replace("[\\s']".toRegex(), "")

                    xmlReport = true
                    xmlOutput = target.rootProject.layout.buildDirectory.file("lint-reports/lint-results-${filename}.xml").get().asFile

                    htmlReport = true
                    htmlOutput = target.rootProject.layout.buildDirectory.file("lint-reports/lint-results-${filename}.html").get().asFile

                    // for now
                    sarifReport = false
                }
            }
        }
    }
}

