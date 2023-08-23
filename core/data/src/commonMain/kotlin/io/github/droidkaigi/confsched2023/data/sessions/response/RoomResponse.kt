package io.github.droidkaigi.confsched2023.data.sessions.response

import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    val name: LocaledResponse,
    val id: Int,
)
