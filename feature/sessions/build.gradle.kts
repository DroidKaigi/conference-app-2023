plugins {
    id("droidkaigi.convention.androidfeature")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.sessions"

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(libs.animation.graphics.android)
    testImplementation(projects.core.testing)

    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialIcon)
    implementation(libs.composeShimmer)
}
