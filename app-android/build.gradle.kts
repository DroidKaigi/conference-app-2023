plugins {
    id("com.example.primitive.androidapplication")
    id("com.example.primitive.android.compose")
    id("com.example.primitive.android.hilt")
    id("com.example.primitive.spotless")
}

android.namespace = "com.example.project.template"

dependencies {
    implementation(projects.feature.home)
    implementation(projects.core.ui)
}