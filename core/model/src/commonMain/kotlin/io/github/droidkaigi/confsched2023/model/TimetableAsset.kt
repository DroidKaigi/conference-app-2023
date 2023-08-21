package io.github.droidkaigi.confsched2023.model

public data class TimetableAsset(
    val videoUrl: String?,
    val slideUrl: String?,
) {
    val isAvailable: Boolean
        get() = !videoUrl.isNullOrBlank() && !slideUrl.isNullOrBlank()
}
