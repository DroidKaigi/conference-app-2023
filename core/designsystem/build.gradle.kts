plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.spotless")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.designsystem"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
            }
        }
    }
}
