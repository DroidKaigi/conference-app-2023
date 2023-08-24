plugins {
    id("droidkaigi.convention.androidfeature")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.sponsors"

dependencies {
    testImplementation(projects.core.testing)

    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialIcon)
}
