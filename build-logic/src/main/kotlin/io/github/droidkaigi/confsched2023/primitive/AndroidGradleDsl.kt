package io.github.droidkaigi.confsched2023.primitive

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.setupAndroid() {
    android {
        namespace?.let {
            this.namespace = it
        }
        compileSdkVersion(34)

        defaultConfig {
            minSdk = 23
            targetSdk = 33
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }
        dependencies {
            add("coreLibraryDesugaring", libs.library("androidDesugarJdkLibs"))
        }
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        (this as CommonExtension<*,*,*,*,*>).lint {
            // shell friendly
            val filename = displayName.replace(":", "_").replace("[\\s']".toRegex(), "")

            xmlReport = true
            xmlOutput = rootProject.layout.buildDirectory.file("lint-reports/lint-results-${filename}.xml").get().asFile

            htmlReport = true
            htmlOutput = rootProject.layout.buildDirectory.file("lint-reports/lint-results-${filename}.html").get().asFile

            // for now
            sarifReport = false
        }
    }
}

fun Project.setupDetekt(extension: DetektExtension) {
    extension.apply {
        // 並列処理
        parallel = true
        // Detektの設定ファイル
        config = files("${project.rootDir}/config/detekt/detekt.yml")
        // baseline 設定ファイル
        baseline = file("${project.rootDir}/config/detekt/baseline.xml")
        // デフォルト設定の上に自分の設定ファイルを適用する
        buildUponDefaultConfig = true
        // ルール違反があった場合にfailさせない
        ignoreFailures = false
        // ルール違反の自動修正を試みる
        autoCorrect = false
    }

    val reportMerge = if (!rootProject.tasks.names.contains("reportMerge")) {
        rootProject.tasks.register("reportMerge", ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
        }
    } else {
        rootProject.tasks.named("reportMerge") as TaskProvider<ReportMergeTask>
    }

    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin> {
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt> detekt@{
            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(this@detekt.xmlReportFile) // or .sarifReportFile
            }
        }
    }
}
