plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.kmp.compose")
    id("droidkaigi.primitive.kover")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.contributors"
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
                implementation(projects.core.ui)
                implementation(libs.kotlinxCoroutinesCore)
                implementation(projects.core.designsystem)
            }
        }
    }
}

dependencies {
    implementation(projects.core.ui)
}
