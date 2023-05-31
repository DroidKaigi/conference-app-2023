plugins {
    id("droidkaigi.primitive.kmp")
    id("droidkaigi.primitive.kmp.android")
    id("droidkaigi.primitive.kmp.android.hilt")
    id("droidkaigi.primitive.kmp.ios")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.data"
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.model)
                implementation(libs.kotlinxCoroutinesCore)
                implementation(libs.androidxDatastoreDatastorePreferences)
            }
        }
    }
}
dependencies {
}
