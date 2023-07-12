package io.github.droidkaigi.confsched2023.sessions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterUiState
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    private val searchFilterUiState: MutableStateFlow<SearchFilterUiState> = MutableStateFlow(
        SearchFilterUiState(),
    )

    val uiState: StateFlow<SearchScreenUiState> = buildUiState(
        searchQuery,
        searchFilterUiState,
    ) { searchQuery, searchFilterUiState ->
        SearchScreenUiState(
            searchQuery = searchQuery,
            searchFilterUiState = searchFilterUiState,
        )
    }

    fun onSearchQueryChanged(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY] = searchQuery
    }

    fun onDaySelected(day: DroidKaigi2023Day, isSelected: Boolean) {
        val selectedDays = searchFilterUiState.value.selectedDays.toMutableList()
        searchFilterUiState.value = searchFilterUiState.value.copy(
            selectedDays = selectedDays.apply {
                if (isSelected)
                    add(day)
                else
                    remove(day)
            }.sortedBy(DroidKaigi2023Day::start)
        )
    }

    fun onFilterCategoryChipClicked() {
        viewModelScope.launch {
            // TODO: Implement SessionsRepository.getCategories()
            val categories = emptyList<TimetableCategory>()
            if (categories.isEmpty())
                return@launch

            searchFilterUiState.value = SearchFilterUiState(
                categories = categories
            )
        }
    }

    fun onCategoriesSelected(category: TimetableCategory, isSelected: Boolean) {
        val selectedCategories = searchFilterUiState.value.selectedCategories.toMutableList()
        searchFilterUiState.value = searchFilterUiState.value.copy(
            selectedCategories = selectedCategories.apply {
                if (isSelected)
                    add(category)
                else
                    remove(category)
            }
        )
    }
}