package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomIndex
    get() = when (name.enTitle.lowercase()) {
        "arctic fox" -> Room2
        "bumblebee" -> Room3
        "chipmunk" -> Room1
        "dolphin" -> Room4
        "electric eel" -> Room5
        else -> Room1
    }
