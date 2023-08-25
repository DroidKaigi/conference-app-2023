plugins {
    id("droidkaigi.primitive.android")
    id("droidkaigi.primitive.android.kotlin")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.android.hilt")
    id("droidkaigi.primitive.kover")
    id("droidkaigi.primitive.detekt")
}

android.namespace = "io.github.droidkaigi.confsched2023.core.testing"

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.feature.main)
    implementation(projects.feature.sessions)
    implementation(projects.feature.about)
    implementation(projects.feature.sponsors)
    implementation(projects.feature.floorMap)
    implementation(projects.feature.stamps)
    implementation(projects.feature.staff)
    implementation(libs.daggerHiltAndroidTesting)
    implementation(libs.roborazzi)
    implementation(libs.kermit)
    api(libs.roborazziRule)
    api(libs.composeUiTestJunit4)
    implementation(libs.composeMaterialWindowSize)
}
