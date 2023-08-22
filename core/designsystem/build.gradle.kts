plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.compose")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.detekt")
    id("droidkaigi.primitive.kmp.android.showkase")
    id("droidkaigi.primitive.kover")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.designsystem"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                // Fix https://youtrack.jetbrains.com/issue/KT-41821
                implementation(libs.kotlinxAtomicfu)
            }
        }
    }
}

dependencies {
    lintChecks(libs.composeLintCheck)
}
