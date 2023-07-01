package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableUiType
import io.github.droidkaigi.confsched2023.sessions.section.TimetableGridUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableListUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val sessionsStateFlow: StateFlow<Timetable> = sessionsRepository
        .getSessionsStream()
        .handleErrorAndRetry(
            AppStrings.Retry,
            userMessageStateHolder,
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Timetable(),
        )
    private val timetableUiTypeStateFlow: MutableStateFlow<TimetableUiType> =
        MutableStateFlow(TimetableUiType.List)

    private val timetableSheetUiState: StateFlow<TimetableSheetUiState> = buildUiState(
        sessionsStateFlow,
        timetableUiTypeStateFlow,
    ) { sessionTimetable, uiType ->
        if (sessionTimetable.timetableItems.isEmpty()) {
            return@buildUiState TimetableSheetUiState.Empty
        }
        if (uiType == TimetableUiType.List) {
            return@buildUiState TimetableSheetUiState.ListTimetable(
                TimetableListUiState(
                    timetable = sessionTimetable,
                ),
            )
        } else {
            return@buildUiState TimetableSheetUiState.GridTimetable(
                TimetableGridUiState(
                    timetable = sessionTimetable,
                ),
            )
        }
    }

    val uiState: StateFlow<TimetableScreenUiState> = buildUiState(
        timetableSheetUiState,
    ) { sessionListUiState ->
        TimetableScreenUiState(
            contentUiState = sessionListUiState,
        )
    }

    fun onUiTypeChange(uiType: TimetableUiType) {
        timetableUiTypeStateFlow.value = uiType
    }

    fun onBookmarkClick(session: TimetableItem.Session) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(session.id)
        }
    }
}
