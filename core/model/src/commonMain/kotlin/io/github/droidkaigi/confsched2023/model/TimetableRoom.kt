package io.github.droidkaigi.confsched2023.model

import kotlinx.serialization.Serializable

@Serializable
public data class TimetableRoom(
    val type: RoomType,
    val name: MultiLangText,
    val sort: Int,
)
