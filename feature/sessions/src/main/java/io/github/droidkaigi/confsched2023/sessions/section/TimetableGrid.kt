package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.runtime.Composable
import io.github.droidkaigi.confsched2023.model.Timetable

data class TimetableGridUiState(val timetable: Timetable)

@Composable
fun TimetableGrid(
    uiState: TimetableGridUiState,
) {
}
