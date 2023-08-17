import io.github.droidkaigi.confsched2023.primitive.ksp
import io.github.droidkaigi.confsched2023.primitive.libs

plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.compose")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.spotless")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.designsystem"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                // Fix https://youtrack.jetbrains.com/issue/KT-41821
                implementation(libs.findLibrary("kotlinxAtomicfu").get())
            }
        }
        androidTarget {
            dependencies {
                // FIXME: move this to build-logic
                ksp(libs.findLibrary("showkaseProcessor"))
            }
        }
    }
}

dependencies {
    lintChecks(libs.findLibrary("composeLintCheck").get())
}