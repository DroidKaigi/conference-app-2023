package io.github.droidkaigi.confsched2023.data.sessions.response

import kotlinx.serialization.Serializable

@Serializable
data class SpeakerResponse(
    val profilePicture: String? = null,
    val sessions: List<Int> = emptyList(),
    val tagLine: String? = null,
    val isTopSpeaker: Boolean?,
    val bio: String? = null,
    val fullName: String,
    val id: String,
)
