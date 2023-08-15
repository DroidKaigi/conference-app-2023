package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableLanguage
import io.github.droidkaigi.confsched2023.model.TimetableSessionType

data class SearchFilterUiState(
    val categories: List<TimetableCategory> = emptyList(),
    val sessionTypes: List<TimetableSessionType> = emptyList(),
    val languages: List<TimetableLanguage> = emptyList(),
    val selectedDays: List<DroidKaigi2023Day> = emptyList(),
    val selectedCategories: List<TimetableCategory> = emptyList(),
    val selectedSessionTypes: List<TimetableSessionType> = emptyList(),
    val selectedLanguages: List<TimetableLanguage> = emptyList(),
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
        get() = selectedLanguages.joinToString { it.langOfSpeaker }

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
    onLanguagesSelected: (TimetableLanguage, Boolean) -> Unit = { _, _ -> },
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilterDayChip(
            isSelected = searchFilterUiState.isDaySelected,
            selectedDays = searchFilterUiState.selectedDays,
            selectedDaysValues = searchFilterUiState.selectedDaysValues,
            kaigiDays = DroidKaigi2023Day.values().toList(),
            onDaySelected = onDaySelected,
        )
        FilterCategoryChip(
            isSelected = searchFilterUiState.isCategoriesSelected,
            selectedCategories = searchFilterUiState.selectedCategories,
            selectedCategoriesValues = searchFilterUiState.selectedCategoriesValue,
            categories = searchFilterUiState.categories,
            onCategoriesSelected = onCategoriesSelected,
            onFilterCategoryChipClicked = { keyboardController?.hide() },
        )
        FilterSessionTypeChip(
            isSelected = searchFilterUiState.isSessionTypeSelected,
            selectedSessionTypes = searchFilterUiState.selectedSessionTypes,
            selectedSessionTypesValues = searchFilterUiState.selectedSessionTypesValue,
            sessionTypes = searchFilterUiState.sessionTypes,
            onSessionTypeSelected = onSessionTypesSelected,
            onFilterSessionTypeChipClicked = { keyboardController?.hide() },
        )
        FilterLanguageChip(
            isSelected = searchFilterUiState.isLanguagesSelected,
            selectedLanguages = searchFilterUiState.selectedLanguages,
            selectedLanguagesValues = searchFilterUiState.selectedLanguagesValue,
            languages = searchFilterUiState.languages,
            onLanguagesSelected = onLanguagesSelected,
            onFilterLanguageChipClicked = { keyboardController?.hide() },
        )
    }
}
