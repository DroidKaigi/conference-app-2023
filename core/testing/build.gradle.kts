plugins {
    id("droidkaigi.primitive.android")
    id("droidkaigi.primitive.android.kotlin")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.android.hilt")
    id("droidkaigi.primitive.spotless")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.testing"

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.daggerHiltAndroidTesting)
    api(libs.composeUiTestJunit4)
}