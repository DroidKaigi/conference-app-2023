package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.RoomIndex.RoomA
import io.github.droidkaigi.confsched2023.model.RoomIndex.RoomB
import io.github.droidkaigi.confsched2023.model.RoomIndex.RoomC
import io.github.droidkaigi.confsched2023.model.RoomIndex.RoomD
import io.github.droidkaigi.confsched2023.model.RoomIndex.RoomE

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomIndex
    get() = when (name.enTitle.lowercase()) {
        "arctic fox" -> RoomA
        "bumblebee" -> RoomB
        "chipmunk" -> RoomC
        "dolphin" -> RoomD
        "electric eel" -> RoomE
        else -> RoomA
    }
