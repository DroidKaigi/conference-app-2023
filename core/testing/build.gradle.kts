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
    implementation(project(":feature:sponsors"))
    implementation(project(":feature:floor-map"))
    implementation(project(":feature:stamps"))

    implementation(libs.daggerHiltAndroidTesting)
    implementation(libs.roborazzi)
    implementation(libs.kermit)
    api(libs.roborazziRule)
    api(libs.composeUiTestJunit4)
}