package io.github.droidkaigi.confsched2023.model

import kotlinx.serialization.Serializable

@Serializable
public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomIndex
    get() = RoomIndex.values().getOrNull(sortIndex) ?: RoomIndex.Room1
