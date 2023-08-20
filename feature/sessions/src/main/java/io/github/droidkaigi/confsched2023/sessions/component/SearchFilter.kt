package io.github.droidkaigi.confsched2023.sessions.component

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

const val SearchFilterDayFilterChipTestTag = "SearchFilterDayFilterChip"
const val SearchFilterCategoryChipTestTag = "SearchFilterCategoryChipTest"
const val SearchFilterSessionTypeChipTestTag = "SearchFilterSessionTypeChipTest"
const val SearchFilterLanguageChipTestTag = "SearchFilterLanguageChipTest"

data class SearchFilterUiState(
    val categories: List<TimetableCategory> = emptyList(),
    val sessionTypes: List<TimetableSessionType> = emptyList(),
    val selectedDays: List<DroidKaigi2023Day> = emptyList(),
    val selectedCategories: List<TimetableCategory> = emptyList(),
    val selectedSessionTypes: List<TimetableSessionType> = emptyList(),
    val selectedLanguages: List<Lang> = emptyList(),
    val isFavoritesOn: Boolean = false,
) {
    val selectedDaysValues: String
        get() = selectedDays.joinToString { it.name }

    val isDaySelected: Boolean
        get() = selectedDays.isNotEmpty()

    val selectedCategoriesValue: String
        get() = selectedCategories.joinToString { it.title.currentLangTitle }

    val selectedSessionTypesValue: String
        get() = selectedSessionTypes.joinToString { it.label.currentLangTitle }

    val selectedLanguagesValue: String
        get() = selectedLanguages.joinToString { it.tagName }

    val isCategoriesSelected: Boolean
        get() = selectedCategories.isNotEmpty()

    val isLanguagesSelected: Boolean
        get() = selectedLanguages.isNotEmpty()

    val isSessionTypeSelected: Boolean
        get() = selectedSessionTypes.isNotEmpty()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFilter(
    searchFilterUiState: SearchFilterUiState,
    modifier: Modifier = Modifier,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit = { _, _ -> },
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit = { _, _ -> },
    onSessionTypesSelected: (TimetableSessionType, Boolean) -> Unit = { _, _ -> },
    onLanguagesSelected: (Lang, Boolean) -> Unit = { _, _ -> },
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        item {
            FilterDayChip(
                isSelected = searchFilterUiState.isDaySelected,
                selectedDays = searchFilterUiState.selectedDays,
                selectedDaysValues = searchFilterUiState.selectedDaysValues,
                kaigiDays = DroidKaigi2023Day.entries.toList(),
                onDaySelected = onDaySelected,
                modifier = Modifier
                    .testTag(SearchFilterDayFilterChipTestTag),
            )
        }
        item {
            FilterCategoryChip(
                isSelected = searchFilterUiState.isCategoriesSelected,
                selectedCategories = searchFilterUiState.selectedCategories,
                selectedCategoriesValues = searchFilterUiState.selectedCategoriesValue,
                categories = searchFilterUiState.categories,
                onCategoriesSelected = onCategoriesSelected,
                onFilterCategoryChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterCategoryChipTestTag),
            )
        }
        item {
            FilterSessionTypeChip(
                isSelected = searchFilterUiState.isSessionTypeSelected,
                selectedSessionTypes = searchFilterUiState.selectedSessionTypes,
                selectedSessionTypesValues = searchFilterUiState.selectedSessionTypesValue,
                sessionTypes = searchFilterUiState.sessionTypes,
                onSessionTypeSelected = onSessionTypesSelected,
                onFilterSessionTypeChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterSessionTypeChipTestTag),
            )
        }
        item {
            FilterLanguageChip(
                isSelected = searchFilterUiState.isLanguagesSelected,
                selectedLanguages = searchFilterUiState.selectedLanguages,
                selectedLanguagesValues = searchFilterUiState.selectedLanguagesValue,
                languages = listOf(Lang.JAPANESE, Lang.ENGLISH),
                onLanguagesSelected = onLanguagesSelected,
                onFilterLanguageChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterLanguageChipTestTag),
            )
        }
    }
}
