package io.github.droidkaigi.confsched2023.model

data class AppVersion(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    val isDebug: Boolean,
)
