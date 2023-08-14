package io.github.droidkaigi.confsched2023.data.sessions.response

import kotlinx.serialization.Serializable

@Serializable
data class SessionMessageResponse(
    val ja: String,
    val en: String,
)
