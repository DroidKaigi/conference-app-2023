package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(
    private val sessionsRepository: SessionsRepository,
    userMessageStateHolder: UserMessageStateHolder,
) : ViewModel() {

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

    private val currentDayFilter = MutableStateFlow(
        DroidKaigi2023Day.values().map { it },
    )

    val uiState: StateFlow<BookmarkScreenUiState> =
        buildUiState(
            sessionsStateFlow,
            currentDayFilter,
        ) { sessionsStateFlow, currentDayFilter ->
            val sortAndGroupedBookmarkedTimetableItems = sessionsStateFlow.filtered(
                Filters(
                    days = currentDayFilter,
                    filterFavorite = true,
                ),
            ).timetableItems.groupBy {
                it.startsTimeString + it.endsTimeString
            }.mapValues { entries ->
                entries.value.sortedWith(
                    compareBy({ it.day?.name.orEmpty() }, { it.startsTimeString }),
                )
            }.toPersistentMap()

            if (sortAndGroupedBookmarkedTimetableItems.isEmpty()) {
                BookmarkScreenUiState.Empty(
                    currentDayFilter.toPersistentList(),
                )
            } else {
                BookmarkScreenUiState.ListBookmark(
                    sessionsStateFlow.bookmarks,
                    sortAndGroupedBookmarkedTimetableItems,
                    currentDayFilter.toPersistentList(),
                )
            }
        }

    fun onClickAllFilterChip() {
        currentDayFilter.update {
            DroidKaigi2023Day.values().toList()
        }
    }

    fun onClickDayFirstChip() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day1)
        }
    }

    fun onClickDaySecondChip() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day2)
        }
    }

    fun onClickDayThirdChip() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day3)
        }
    }

    fun updateBookmark(timetableItem: TimetableItemId) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(timetableItem)
        }
    }
}
