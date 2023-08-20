package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.SupportedLanguages

@Composable
fun FilterLanguageChip(
    searchFilterItemUiState: SearchFilterItemUiState<Lang>,
    onLanguagesSelected: (Lang, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onFilterLanguageChipClicked: () -> Unit,
) {
    FilterChipWithDropdownMenu(
        searchFilterItemUiState = searchFilterItemUiState,
        onSelected = onLanguagesSelected,
        filterChipLabelDefaultText = SupportedLanguages.asString(),
        onFilterChipClick = onFilterLanguageChipClicked,
        dropdownMenuItemText = { language ->
            language.tagName
        },
        modifier = modifier,
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterLanguageChip() {
    var uiState by remember {
        mutableStateOf(
            SearchFilterItemUiState(
                selectedItems = emptyList(),
                items = listOf(Lang.JAPANESE, Lang.ENGLISH),
            ),
        )
    }

    KaigiTheme {
        FilterLanguageChip(
            searchFilterItemUiState = uiState,
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
