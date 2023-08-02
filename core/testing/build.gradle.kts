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
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":feature:main"))
    implementation(project(":feature:sessions"))
    implementation(project(":feature:about"))

    implementation(libs.daggerHiltAndroidTesting)
    implementation(libs.roborazzi)
    api(libs.roborazziRule)
    api(libs.composeUiTestJunit4)
}