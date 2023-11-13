package io.github.droidkaigi.confsched2023.model

@Immutable
public data class TimetableRooms(val rooms: List<TimetableRoom>)

public data class TimetableRoom(
    val id: Int,
    val name: MultiLangText,
    val type: RoomType,
    val sort: Int,
) : Comparable<TimetableRoom> {
    override fun compareTo(other: TimetableRoom): Int {
        if (sort < 900 && other.sort < 900) {
            return name.currentLangTitle.compareTo(other.name.currentLangTitle)
        }
        return sort.compareTo(other.sort)
    }
}

public val TimetableRoom.nameAndFloor: String
    get() {
        val basementFloorString = MultiLangText(jaTitle = "地下1階", enTitle = "B1F")
        val floor = when (type) {
            RoomType.RoomA -> basementFloorString.currentLangTitle
            RoomType.RoomB -> basementFloorString.currentLangTitle
            RoomType.RoomC -> basementFloorString.currentLangTitle
            RoomType.RoomD -> "1F"
            RoomType.RoomE -> "1F"
            // Assume the room on the third day.
            RoomType.RoomDE -> "1F"
        }
        return "${name.currentLangTitle} ($floor)"
    }
