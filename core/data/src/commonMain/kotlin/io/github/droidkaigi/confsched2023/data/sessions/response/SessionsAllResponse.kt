package io.github.droidkaigi.confsched2023.data.sessions.response

import kotlinx.serialization.Serializable

@Serializable
public data class SessionsAllResponse(
    val sessions: List<SessionResponse> = emptyList(),
    val rooms: List<RoomResponse> = emptyList(),
    val speakers: List<SpeakerResponse> = emptyList(),
    val categories: List<CategoryResponse> = emptyList(),
)
