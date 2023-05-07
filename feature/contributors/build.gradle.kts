plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kmp.ios")
    // NOTE: Tentatively using compose for android
    // Supposed to use compose for multiplatform
    id("droidkaigi.primitive.android.compose")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.contributors"
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
                implementation(libs.kotlinxCoroutinesCore)
            }
        }
        androidMain {
            dependencies {
                implementation(projects.core.designsystem)
            }
        }
    }
}
