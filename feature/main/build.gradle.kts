plugins {
    id("droidkaigi.convention.androidfeature")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.main"

dependencies {
    testImplementation(projects.core.testing)

    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialWindowSize)
    implementation(libs.composeMaterialIcon)
    implementation(libs.composeNavigation)
    implementation(libs.androidxWindow)
}
