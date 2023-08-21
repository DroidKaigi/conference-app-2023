package io.github.droidkaigi.confsched2023.model

data class AppVersion(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
) {
    companion object {
        fun fake(): AppVersion {
            return AppVersion(
                packageName = "io.github.droidkaigi.confsched2023",
                versionName = "0.0.1",
                versionCode = 1,
            )
        }
    }
}
