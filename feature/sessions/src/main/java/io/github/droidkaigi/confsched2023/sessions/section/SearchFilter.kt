package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.sessions.component.FilterCategoryChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterDayChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterLanguageChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterSessionTypeChip

data class SearchFilterUiState<T>(
    val selectedItems: List<T>,
    val items: List<T>,
    val isSelected: Boolean = false,
    val selectedValues: String = "",
)

const val SearchFilterTestTag = "SearchFilter"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFilter(
    searchFilterDayUiState: SearchFilterUiState<DroidKaigi2023Day>,
    searchFilterCategoryUiState: SearchFilterUiState<TimetableCategory>,
    searchFilterSessionTypeUiState: SearchFilterUiState<TimetableSessionType>,
    searchFilterLanguageUiState: SearchFilterUiState<Lang>,
    modifier: Modifier = Modifier,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit = { _, _ -> },
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit = { _, _ -> },
    onSessionTypesSelected: (TimetableSessionType, Boolean) -> Unit = { _, _ -> },
    onLanguagesSelected: (Lang, Boolean) -> Unit = { _, _ -> },
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyRow(
        modifier = modifier.testTag(SearchFilterTestTag),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        item {
            FilterDayChip(
                searchFilterUiState = searchFilterDayUiState,
                onDaySelected = onDaySelected,
            )
        }
        item {
            FilterCategoryChip(
                searchFilterUiState = searchFilterCategoryUiState,
                onCategoriesSelected = onCategoriesSelected,
                onFilterCategoryChipClicked = { keyboardController?.hide() },
            )
        }
        item {
            FilterSessionTypeChip(
                searchFilterUiState = searchFilterSessionTypeUiState,
                onSessionTypeSelected = onSessionTypesSelected,
                onFilterSessionTypeChipClicked = { keyboardController?.hide() },
            )
        }
        item {
            FilterLanguageChip(
                searchFilterUiState = searchFilterLanguageUiState,
                onLanguagesSelected = onLanguagesSelected,
                onFilterLanguageChipClicked = { keyboardController?.hide() },
            )
        }
    }
}
