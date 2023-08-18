package io.github.droidkaigi.confsched2023.model

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomIndex
    get() = RoomIndex.entries.getOrNull(sortIndex) ?: RoomIndex.Room1
