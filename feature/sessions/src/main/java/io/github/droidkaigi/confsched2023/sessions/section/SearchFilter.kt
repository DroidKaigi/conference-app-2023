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
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.sessions.component.FilterCategoryChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterDayChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterLanguageChip
import io.github.droidkaigi.confsched2023.sessions.component.FilterSessionTypeChip
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SearchFilterUiState<T>(
    val selectedItems: ImmutableList<T>,
    val items: ImmutableList<T>,
    val isSelected: Boolean = false,
    val selectedValues: String = "",
)

const val SearchFilterDayFilterChipTestTag = "SearchFilterDayFilterChip"
const val SearchFilterCategoryChipTestTag = "SearchFilterCategoryChipTest"
const val SearchFilterSessionTypeChipTestTag = "SearchFilterSessionTypeChipTest"
const val SearchFilterLanguageChipTestTag = "SearchFilterLanguageChipTest"

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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        item {
            FilterDayChip(
                searchFilterUiState = searchFilterDayUiState,
                onDaySelected = onDaySelected,
                modifier = Modifier
                    .testTag(SearchFilterDayFilterChipTestTag),
            )
        }
        item {
            FilterCategoryChip(
                searchFilterUiState = searchFilterCategoryUiState,
                onCategoriesSelected = onCategoriesSelected,
                onFilterCategoryChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterCategoryChipTestTag),
            )
        }
        item {
            FilterSessionTypeChip(
                searchFilterUiState = searchFilterSessionTypeUiState,
                onSessionTypeSelected = onSessionTypesSelected,
                onFilterSessionTypeChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterSessionTypeChipTestTag),
            )
        }
        item {
            FilterLanguageChip(
                searchFilterUiState = searchFilterLanguageUiState,
                onLanguagesSelected = onLanguagesSelected,
                onFilterLanguageChipClicked = { keyboardController?.hide() },
                modifier = Modifier
                    .testTag(SearchFilterLanguageChipTestTag),
            )
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun SearchFilterPreview() {
    SearchFilter(
        searchFilterDayUiState = SearchFilterUiState(
            selectedItems = emptyList<DroidKaigi2023Day>().toImmutableList(),
            items = DroidKaigi2023Day.entries.toImmutableList(),
        ),
        searchFilterCategoryUiState = SearchFilterUiState(
            selectedItems = emptyList<TimetableCategory>().toImmutableList(),
            items = emptyList<TimetableCategory>().toImmutableList(),
        ),
        searchFilterSessionTypeUiState = SearchFilterUiState(
            selectedItems = emptyList<TimetableSessionType>().toImmutableList(),
            items = TimetableSessionType.entries.toImmutableList(),
        ),
        searchFilterLanguageUiState = SearchFilterUiState(
            selectedItems = emptyList<Lang>().toImmutableList(),
            items = Lang.entries.toImmutableList(),
        ),
    )
}
