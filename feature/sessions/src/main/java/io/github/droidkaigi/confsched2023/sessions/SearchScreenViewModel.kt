package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.designsystem.strings.AppStrings
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Filters
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState
import io.github.droidkaigi.confsched2023.sessions.section.SearchListUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.handleErrorAndRetry
import kotlinx.collections.immutable.toImmutableList
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

    private val filterStateFlow: MutableStateFlow<Filters> = MutableStateFlow(
        Filters(),
    )

    val uiState: StateFlow<SearchScreenUiState> = buildUiState(
        searchQuery,
        filterStateFlow,
        sessionsStateFlow,
    ) { searchQuery, filters, sessions ->
        val searchedSessions = sessions.filtered(
            Filters(
                searchWord = searchQuery,
                days = filters.days,
                categories = filters.categories,
                sessionTypes = filters.sessionTypes,
                languages = filters.languages,
            ),
        )
        if (searchedSessions.isEmpty()) {
            SearchScreenUiState.Empty(
                searchQuery = searchQuery,
                searchFilterDayUiState = searchFilterDayUiState(filters.days),
                searchFilterCategoryUiState = searchFilterCategoryUiState(filters.categories),
                searchFilterSessionTypeUiState = searchFilterSessionTypeUiState(filters.sessionTypes),
                searchFilterLanguageUiState = searchFilterLanguageUiState(filters.languages),
            )
        } else {
            SearchScreenUiState.SearchList(
                searchQuery = searchQuery,
                searchFilterDayUiState = searchFilterDayUiState(filters.days),
                searchFilterCategoryUiState =
                searchFilterCategoryUiState(filters.categories, sessions.categories),
                searchFilterSessionTypeUiState =
                searchFilterSessionTypeUiState(filters.sessionTypes, sessions.sessionTypes),
                searchFilterLanguageUiState = searchFilterLanguageUiState(filters.languages),
                searchListUiState = SearchListUiState(
                    bookmarkedTimetableItemIds = sessions.bookmarks,
                    timetableItems = searchedSessions.timetableItems,
                ),
            )
        }
    }

    private fun searchFilterDayUiState(
        selectedDays: List<DroidKaigi2023Day>,
    ): SearchFilterUiState<DroidKaigi2023Day> {
        return SearchFilterUiState(
            selectedItems = selectedDays.toImmutableList(),
            items = DroidKaigi2023Day.entries.toImmutableList(),
            isSelected = selectedDays.isNotEmpty(),
            selectedValues = selectedDays.joinToString { it.name },
        )
    }

    private fun searchFilterCategoryUiState(
        selectedCategories: List<TimetableCategory>,
        categories: List<TimetableCategory>? = null,
    ): SearchFilterUiState<TimetableCategory> {
        return SearchFilterUiState(
            selectedItems = selectedCategories.toImmutableList(),
            items = categories.orEmpty().toImmutableList(),
            isSelected = selectedCategories.isNotEmpty(),
            selectedValues = selectedCategories.joinToString { it.title.currentLangTitle },
        )
    }

    private fun searchFilterSessionTypeUiState(
        selectedSessionTypes: List<TimetableSessionType>,
        sessionTypes: List<TimetableSessionType>? = null,
    ): SearchFilterUiState<TimetableSessionType> {
        return SearchFilterUiState(
            selectedItems = selectedSessionTypes.toImmutableList(),
            items = sessionTypes.orEmpty().toImmutableList(),
            isSelected = selectedSessionTypes.isNotEmpty(),
            selectedValues = selectedSessionTypes.joinToString { it.label.currentLangTitle },
        )
    }

    private fun searchFilterLanguageUiState(
        selectedLanguages: List<Lang>,
    ): SearchFilterUiState<Lang> {
        return SearchFilterUiState(
            selectedItems = selectedLanguages.toImmutableList(),
            items = listOf(Lang.JAPANESE, Lang.ENGLISH).toImmutableList(),
            isSelected = selectedLanguages.isNotEmpty(),
            selectedValues = selectedLanguages.joinToString { it.tagName },
        )
    }

    fun onSearchQueryChanged(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY] = searchQuery
    }

    fun onDaySelected(day: DroidKaigi2023Day, isSelected: Boolean) {
        val selectedDays = filterStateFlow.value.days.toMutableList()
        filterStateFlow.value = filterStateFlow.value.copy(
            days = selectedDays.apply {
                if (isSelected) {
                    add(day)
                } else {
                    remove(day)
                }
            }.sortedBy(DroidKaigi2023Day::start),
        )
    }

    fun onCategoriesSelected(category: TimetableCategory, isSelected: Boolean) {
        val selectedCategories = filterStateFlow.value.categories.toMutableList()
        filterStateFlow.value = filterStateFlow.value.copy(
            categories = selectedCategories.apply {
                if (isSelected) {
                    add(category)
                } else {
                    remove(category)
                }
            },
        )
    }

    fun onSessionTypesSelected(sessionType: TimetableSessionType, isSelected: Boolean) {
        val selectedSessionTypes = filterStateFlow.value.sessionTypes.toMutableList()
        filterStateFlow.value = filterStateFlow.value.copy(
            sessionTypes = selectedSessionTypes.apply {
                if (isSelected) {
                    add(sessionType)
                } else {
                    remove(sessionType)
                }
            },
        )
    }

    fun onLanguagesSelected(language: Lang, isSelected: Boolean) {
        val selectedLanguages = filterStateFlow.value.languages.toMutableList()
        filterStateFlow.value = filterStateFlow.value.copy(
            languages = selectedLanguages.apply {
                if (isSelected) {
                    add(language)
                } else {
                    remove(language)
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
