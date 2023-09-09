plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kmp.ios")
    id("droidkaigi.primitive.detekt")
    id("droidkaigi.primitive.kmp.ktorfit")
    id("droidkaigi.primitive.kmp.serialization")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.data"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
                implementation(projects.core.common)
                implementation(libs.kotlinxCoroutinesCore)
                // We use api for test
                api(libs.androidxDatastoreDatastorePreferences)
                implementation(libs.okIo)
                implementation(libs.ktorClientCore)
                implementation(libs.ktorKotlinxSerialization)
                implementation(libs.ktorContentNegotiation)
                implementation(libs.kermit)
            }
        }

        androidMain {
            dependsOn(getByName("commonMain"))
            dependencies {
                implementation(libs.ktorClientOkHttp)
                implementation(libs.multiplatformFirebaseAuth)
                implementation(libs.okHttpLoggingInterceptor)
                implementation(libs.okHttpLoggingInterceptor)
                implementation(libs.firebaseRemoteConfig)
            }
        }

        iosMain {
            dependsOn(getByName("commonMain"))
            dependencies {
                implementation(libs.ktorClientDarwin)
                api(libs.koin)
            }
        }
    }
}
