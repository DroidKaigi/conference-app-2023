plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.model"

kotlin {
    sourceSets {
        commonMain{
            dependencies {
                implementation(libs.kotlinxCoroutinesCore)
            }
        }
    }
}

dependencies {
}