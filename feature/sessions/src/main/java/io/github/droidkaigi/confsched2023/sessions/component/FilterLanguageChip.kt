package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.Lang.ENGLISH
import io.github.droidkaigi.confsched2023.model.Lang.JAPANESE
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.SupportedLanguages
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState

const val FilterLanguageChipTestTag = "FilterLanguageChip"

@Composable
fun FilterLanguageChip(
    searchFilterUiState: SearchFilterUiState<Lang>,
    onLanguagesSelected: (Lang, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onFilterLanguageChipClicked: () -> Unit,
) {
    DropdownFilterChip(
        searchFilterUiState = searchFilterUiState,
        onSelected = onLanguagesSelected,
        filterChipLabelDefaultText = SupportedLanguages.asString(),
        onFilterChipClick = onFilterLanguageChipClicked,
        dropdownMenuItemText = { language ->
            language.tagName
        },
        modifier = modifier.testTag(FilterLanguageChipTestTag),
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterLanguageChip() {
    var uiState by remember {
        mutableStateOf(
            SearchFilterUiState(
                selectedItems = emptyList(),
                items = listOf(JAPANESE, ENGLISH),
            ),
        )
    }

    KaigiTheme {
        FilterLanguageChip(
            searchFilterUiState = uiState,
            onLanguagesSelected = { language, isSelected ->
                val selectedLanguages = uiState.selectedItems.toMutableList()
                val newSelectedLanguages = selectedLanguages.apply {
                    if (isSelected) {
                        add(language)
                    } else {
                        remove(language)
                    }
                }
                uiState = uiState.copy(
                    selectedItems = newSelectedLanguages,
                    isSelected = newSelectedLanguages.isNotEmpty(),
                    selectedValues = newSelectedLanguages.joinToString { it.tagName },
                )
            },
            onFilterLanguageChipClicked = {},
        )
    }
}
