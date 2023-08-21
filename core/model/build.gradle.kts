plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.spotless")
    id("droidkaigi.primitive.kover")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.model"

kotlin {
    sourceSets {
        commonMain{
            dependencies {
                implementation(libs.kotlinxCoroutinesCore)
                implementation(libs.kotlinSerializationJson)
                api(libs.kotlinxCollectionsImmutable)
                api(libs.kotlinxDatetime)
            }
        }
        val androidMain by getting {
            dependencies{
                implementation(libs.composeRuntime)
                implementation(libs.androidxAppCompat)
            }
        }
    }
}

dependencies {
}