package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableCategory

data class SearchFilterUiState(
    val categories: List<TimetableCategory> = emptyList(),
    val selectedCategories: List<TimetableCategory> = emptyList(),
    val selectedDays: List<DroidKaigi2023Day> = emptyList(),
    val isFavoritesOn: Boolean = false,
) {
    val selectedDaysValues: String
        get() = selectedDays.joinToString { it.name }

    val isDaySelected: Boolean
        get() = selectedDays.isNotEmpty()

    val selectedCategoriesValue: String
        get() = selectedCategories.joinToString { it.title.currentLangTitle }

    val isCategoriesSelected: Boolean
        get() = selectedCategories.isNotEmpty()
}

@Composable
fun SearchFilter(
    searchFilterUiState: SearchFilterUiState,
    modifier: Modifier = Modifier,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit = { _, _ -> },
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit = { _, _ -> },
    onFilterCategoryChipClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterDayChip(
            isSelected = searchFilterUiState.isDaySelected,
            selectedDays = searchFilterUiState.selectedDays,
            selectedDaysValues = searchFilterUiState.selectedDaysValues,
            kaigiDays = DroidKaigi2023Day.entries.toList(),
            onDaySelected = onDaySelected,
        )
        FilterCategoryChip(
            isSelected = searchFilterUiState.isCategoriesSelected,
            selectedCategories = searchFilterUiState.selectedCategories,
            selectedCategoriesValues = searchFilterUiState.selectedCategoriesValue,
            categories = searchFilterUiState.categories,
            onCategoriesSelected = onCategoriesSelected,
            onFilterCategoryChipClicked = onFilterCategoryChipClicked,
        )
    }
}
