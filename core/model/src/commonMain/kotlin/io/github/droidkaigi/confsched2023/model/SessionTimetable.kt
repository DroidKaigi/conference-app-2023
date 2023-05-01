package io.github.droidkaigi.confsched2023.model

class SessionTimetable(
    val sessions: List<Session> = listOf(),
) {
    fun filtered(filters: Filter): SessionTimetable {
        return SessionTimetable(
            sessions = sessions.filter { session ->
                if (filters.filterFavorites) {
                    session.isFavorited
                } else {
                    true
                }
            },
        )
    }
}

data class Filter(val filterFavorites: Boolean)

data class Session(
    val id: String,
    val isFavorited: Boolean = false,
)