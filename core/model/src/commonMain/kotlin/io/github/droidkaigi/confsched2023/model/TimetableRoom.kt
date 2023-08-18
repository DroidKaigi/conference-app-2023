package io.github.droidkaigi.confsched2023.model

import io.github.droidkaigi.confsched2023.model.RoomType.RoomA
import io.github.droidkaigi.confsched2023.model.RoomType.RoomB
import io.github.droidkaigi.confsched2023.model.RoomType.RoomC
import io.github.droidkaigi.confsched2023.model.RoomType.RoomD
import io.github.droidkaigi.confsched2023.model.RoomType.RoomE

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomType
    get() = when (name.enTitle.lowercase()) {
        "arctic fox" -> RoomA
        "bumblebee" -> RoomB
        "chipmunk" -> RoomC
        "dolphin" -> RoomD
        "electric eel" -> RoomE
        else -> RoomA
    }
