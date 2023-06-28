plugins {
    id("droidkaigi.primitive.androidapplication")
    id("droidkaigi.primitive.android.kotlin")
    id("droidkaigi.primitive.android.compose")
    id("droidkaigi.primitive.android.hilt")
    id("droidkaigi.primitive.spotless")
    id("droidkaigi.primitive.android.roborazzi")
}

android.namespace = "io.github.droidkaigi.confsched2023"

dependencies {
    implementation(projects.feature.main)
    implementation(projects.feature.contributors)
    implementation(projects.feature.sessions)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(libs.composeNavigation)
    implementation(libs.composeHiltNavigtation)
    testImplementation(projects.core.testing)
}