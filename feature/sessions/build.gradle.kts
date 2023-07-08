plugins {
    id("droidkaigi.convention.androidfeature")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.sessions"

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.model)
    testImplementation(projects.core.testing)

    implementation(libs.androidxCoreKtx)
    implementation(libs.composeUi)
    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterial)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.androidxLifecycleLifecycleRuntimeKtx)
    implementation(libs.androidxActivityActivityCompose)
    implementation(libs.composeCoil)
    androidTestImplementation(libs.composeUiTestJunit4)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
}