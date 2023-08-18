package io.github.droidkaigi.confsched2023.model

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val sort: Int,
    val sortIndex: Int,
)

val TimetableRoom.type: RoomIndex
    get() = RoomIndex.entries.getOrNull(sortIndex) ?: RoomIndex.Room1

val TimetableRoom.nameAndFloor: String
    get() {
        val basementFloorString = MultiLangText(jaTitle = "地下1階", enTitle = "B1F")
        val floor = when (type) {
            RoomIndex.Room1 -> basementFloorString.currentLangTitle
            RoomIndex.Room2 -> basementFloorString.currentLangTitle
            RoomIndex.Room3 -> basementFloorString.currentLangTitle
            RoomIndex.Room4 -> "1F"
            RoomIndex.Room5 -> "1F"
        }
        return "${name.currentLangTitle} ($floor)"
    }
