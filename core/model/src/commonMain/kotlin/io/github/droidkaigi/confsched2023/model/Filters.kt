package io.github.droidkaigi.confsched2023.model

public data class Filters(
    val days: List<DroidKaigi2023Day> = emptyList(),
    val categories: List<TimetableCategory> = emptyList(),
    val filterFavorite: Boolean = false,
    val filterSession: Boolean = false,
    val searchWord: String = "",
)
