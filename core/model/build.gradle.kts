plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.detekt")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.model"

@Suppress("UnusedPrivateProperty")
kotlin {
    explicitApi()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinxCoroutinesCore)
                implementation(libs.kotlinSerializationJson)
                api(libs.kotlinxCollectionsImmutable)
                api(libs.kotlinxDatetime)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.composeRuntime)
                implementation(libs.androidxAppCompat)
            }
        }
    }
}

dependencies {
    implementation(platform(libs.composeBom))
}
