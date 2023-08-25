package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Filters
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
import kotlinx.collections.immutable.toPersistentMap
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
        .getTimetableStream()
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

    private val onBookmarkClickStateFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    private val timetableSheetUiState: StateFlow<TimetableSheetUiState> = buildUiState(
        sessionsStateFlow,
        timetableUiTypeStateFlow,
    ) { sessionTimetable, uiType ->
        if (sessionTimetable.timetableItems.isEmpty()) {
            return@buildUiState TimetableSheetUiState.Empty
        }
        if (uiType == TimetableUiType.List) {
            TimetableSheetUiState.ListTimetable(
                DroidKaigi2023Day.entries.associateWith { day ->
                    val sortAndGroupedTimetableItems = sessionTimetable.filtered(
                        Filters(
                            days = listOf(day),
                        ),
                    ).timetableItems.groupBy {
                        it.startsTimeString + it.endsTimeString
                    }.mapValues { entries ->
                        entries.value.sortedWith(
                            compareBy({ it.day?.name.orEmpty() }, { it.startsTimeString }),
                        )
                    }.toPersistentMap()
                    TimetableListUiState(
                        timetableItemMap = sortAndGroupedTimetableItems,
                        timetable = sessionTimetable.dayTimetable(day),
                    )
                },
            )
        } else {
            TimetableSheetUiState.GridTimetable(
                DroidKaigi2023Day.entries.associateWith { day ->
                    TimetableGridUiState(
                        timetable = sessionTimetable.dayTimetable(day),
                    )
                },
            )
        }
    }

    val uiState: StateFlow<TimetableScreenUiState> = buildUiState(
        timetableSheetUiState,
        onBookmarkClickStateFlow,
    ) { sessionListUiState, _ ->
        TimetableScreenUiState(
            contentUiState = sessionListUiState,
            timetableUiType = timetableUiTypeStateFlow.value,
            onBookmarkIconClickStatus = onBookmarkClickStateFlow.value,
        )
    }

    fun onUiTypeChange() {
        timetableUiTypeStateFlow.value =
            if (timetableUiTypeStateFlow.value == TimetableUiType.List) {
                TimetableUiType.Grid
            } else {
                TimetableUiType.List
            }
    }

    fun onBookmarkClick(
        session: TimetableItem,
        isBookmarked: Boolean,
    ) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(session.id)
        }
        if (isBookmarked) {
            onBookmarkClickStateFlow.value = true
        }
    }

    fun onReachAnimationEnd() {
        onBookmarkClickStateFlow.value = false
    }
}
