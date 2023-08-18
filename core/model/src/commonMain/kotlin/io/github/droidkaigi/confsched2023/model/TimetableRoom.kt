package io.github.droidkaigi.confsched2023.model

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
    val type: RoomType,
)
