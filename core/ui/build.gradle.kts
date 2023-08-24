plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.compose")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kover")
    id("droidkaigi.primitive.detekt")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.ui"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.designsystem)
                implementation(projects.core.data)
                implementation(libs.kermit)
                api(projects.core.common)
                api(libs.composeImageLoader)
            }
        }
    }
}
dependencies {
}
