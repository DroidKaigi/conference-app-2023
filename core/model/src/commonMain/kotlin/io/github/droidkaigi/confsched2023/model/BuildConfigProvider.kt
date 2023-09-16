package io.github.droidkaigi.confsched2023.model

interface BuildConfigProvider {
    val versionName: String
    val debugBuild: Boolean
}
