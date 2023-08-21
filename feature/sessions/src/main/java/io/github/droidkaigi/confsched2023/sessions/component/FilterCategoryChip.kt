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
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.fakes
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState

const val FilterCategoryChipTestTag = "FilterCategoryChip"

@Composable
fun FilterCategoryChip(
    searchFilterUiState: SearchFilterUiState<TimetableCategory>,
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onFilterCategoryChipClicked: () -> Unit,
) {
    DropdownFilterChip(
        searchFilterUiState = searchFilterUiState,
        onSelected = onCategoriesSelected,
        filterChipLabelDefaultText = SessionsStrings.Category.asString(),
        onFilterChipClick = onFilterCategoryChipClicked,
        dropdownMenuItemText = { category ->
            category.title.currentLangTitle
        },
        modifier = modifier.testTag(FilterCategoryChipTestTag),
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterCategoryChip() {
    var uiState by remember {
        mutableStateOf(
            SearchFilterUiState(
                selectedItems = emptyList(),
                items = TimetableCategory.fakes(),
            ),
        )
    }

    KaigiTheme {
        FilterCategoryChip(
            searchFilterUiState = uiState,
            onCategoriesSelected = { category, isSelected ->
                val selectedCategories = uiState.selectedItems.toMutableList()
                val newSelectedCategories = selectedCategories.apply {
                    if (isSelected) {
                        add(category)
                    } else {
                        remove(category)
                    }
                }
                uiState = uiState.copy(
                    selectedItems = newSelectedCategories,
                    isSelected = newSelectedCategories.isNotEmpty(),
                    selectedValues = newSelectedCategories.joinToString { it.title.currentLangTitle },
                )
            },
            onFilterCategoryChipClicked = {},
        )
    }
}
