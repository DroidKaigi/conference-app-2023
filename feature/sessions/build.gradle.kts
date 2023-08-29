import io.github.droidkaigi.confsched2023.primitive.android
import io.github.droidkaigi.confsched2023.primitive.kotlinOptions

plugins {
    id("droidkaigi.convention.androidfeature")
}

android.namespace = "io.github.droidkaigi.confsched2023.feature.sessions"

android {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                project.buildDir.absolutePath + "/compose_metrics",
        )
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.model)
    testImplementation(projects.core.testing)

    implementation(libs.composeHiltNavigtation)
    implementation(libs.composeMaterialIcon)
    implementation(libs.composeShimmer)
}
