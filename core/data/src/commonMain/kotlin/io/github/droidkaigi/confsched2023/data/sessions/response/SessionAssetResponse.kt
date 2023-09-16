package io.github.droidkaigi.confsched2023.data.sessions.response

import kotlinx.serialization.Serializable

@Serializable
public data class SessionAssetResponse(
    val videoUrl: String?,
    val slideUrl: String?,
)
