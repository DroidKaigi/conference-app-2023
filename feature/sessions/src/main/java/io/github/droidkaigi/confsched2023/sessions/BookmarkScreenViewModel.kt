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
import io.github.droidkaigi.confsched2023.sessions.section.BookmarkSheetUiState
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
        ) { sessions, currentDayFilter ->
            val sortAndGroupedBookmarkedTimetableItems = sessions.filtered(
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
                BookmarkScreenUiState(
                    contentUiState = BookmarkSheetUiState.Empty(
                        currentDayFilter.toPersistentList(),
                    ),
                )
            } else {
                BookmarkScreenUiState(
                    contentUiState = BookmarkSheetUiState.ListBookmark(
                        sessions.bookmarks,
                        sortAndGroupedBookmarkedTimetableItems,
                        currentDayFilter.toPersistentList(),
                    ),
                )
            }
        }

    fun onAllFilterChipClick() {
        currentDayFilter.update {
            DroidKaigi2023Day.values().toList()
        }
    }

    fun onDayFirstChipClick() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day1)
        }
    }

    fun onDaySecondChipClick() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day2)
        }
    }

    fun onDayThirdChipClick() {
        currentDayFilter.update {
            listOf(DroidKaigi2023Day.Day3)
        }
    }

    fun updateBookmark(timetableItem: TimetableItem) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(timetableItem.id)
        }
    }
}
