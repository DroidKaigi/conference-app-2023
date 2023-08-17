plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.spotless")
    id("droidkaigi.primitive.kmp.ktorfit")
    id("droidkaigi.primitive.kmp.serialization")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.data"
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.model)
                implementation(projects.core.common)
                implementation(libs.kotlinxCoroutinesCore)
                // We use api for test
                api(libs.androidxDatastoreDatastorePreferences)
                implementation(libs.multiplatformFirebaseAuth)
                implementation(libs.okIo)
                implementation(libs.ktorClientCore)
                implementation(libs.ktorKotlinxSerialization)
                implementation(libs.ktorContentNegotiation)
                implementation(libs.kermit)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktorClientOkHttp)
                implementation(libs.okHttpLoggingInterceptor)
                implementation(libs.okHttpLoggingInterceptor)
                implementation(libs.firebaseRemoteConfig)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktorClientDarwin)
            }
        }
    }
}
dependencies {
}
