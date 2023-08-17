package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterUiState
import io.github.droidkaigi.confsched2023.sessions.section.SearchListUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sessionsRepository: SessionsRepository,
    userMessageStateHolder: UserMessageStateHolder,
) : ViewModel() {
    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val searchFilterUiState: MutableStateFlow<SearchFilterUiState> = MutableStateFlow(
        SearchFilterUiState(),
    )

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

    val uiState: StateFlow<SearchScreenUiState> = buildUiState(
        searchQuery,
        searchFilterUiState,
        sessionsStateFlow,
    ) { searchQuery, searchFilterUiState, sessions ->
        val searchedSessions = sessions.filtered(
            Filters(
                days = searchFilterUiState.selectedDays,
                categories = searchFilterUiState.selectedCategories,
                searchWord = searchQuery,
            ),
        )
        if (searchedSessions.isEmpty()) {
            SearchScreenUiState.Empty(
                searchQuery = searchQuery,
                searchFilterUiState = searchFilterUiState,
            )
        } else {
            SearchScreenUiState.SearchList(
                searchQuery = searchQuery,
                searchFilterUiState = searchFilterUiState,
                searchListUiState = SearchListUiState(
                    bookmarkedTimetableItemIds = sessions.bookmarks,
                    timetableItems = searchedSessions.timetableItems,
                ),
            )
        }
    }

    fun onSearchQueryChanged(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY] = searchQuery
    }

    fun onDaySelected(day: DroidKaigi2023Day, isSelected: Boolean) {
        val selectedDays = searchFilterUiState.value.selectedDays.toMutableList()
        searchFilterUiState.value = searchFilterUiState.value.copy(
            selectedDays = selectedDays.apply {
                if (isSelected) {
                    add(day)
                } else {
                    remove(day)
                }
            }.sortedBy(DroidKaigi2023Day::start),
        )
    }

    fun onFilterCategoryChipClicked() {
        viewModelScope.launch {
            // TODO: Implement SessionsRepository.getCategories()
            val categories = emptyList<TimetableCategory>()
            if (categories.isEmpty()) {
                return@launch
            }

            searchFilterUiState.value = SearchFilterUiState(
                categories = categories,
            )
        }
    }

    fun onCategoriesSelected(category: TimetableCategory, isSelected: Boolean) {
        val selectedCategories = searchFilterUiState.value.selectedCategories.toMutableList()
        searchFilterUiState.value = searchFilterUiState.value.copy(
            selectedCategories = selectedCategories.apply {
                if (isSelected) {
                    add(category)
                } else {
                    remove(category)
                }
            },
        )
    }

    fun onClickTimetableItemBookmark(timetableItem: TimetableItem) {
        viewModelScope.launch {
            sessionsRepository.toggleBookmark(timetableItem.id)
        }
    }
}
