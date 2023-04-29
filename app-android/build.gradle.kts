plugins {
    id("com.example.primitive.androidapplication")
    id("com.example.primitive.android.compose")
    id("com.example.primitive.android.hilt")
    id("com.example.primitive.spotless")
}

android.namespace = "io.github.droidkaigi.confsched2023"

dependencies {
    implementation(projects.feature.sessions)
    implementation(projects.core.ui)
}