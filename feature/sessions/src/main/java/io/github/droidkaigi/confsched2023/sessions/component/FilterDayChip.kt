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
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState
import java.util.Locale

const val FilterDayChipTestTag = "FilterDayChip"

@Composable
fun FilterDayChip(
    searchFilterUiState: SearchFilterUiState<DroidKaigi2023Day>,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownFilterChip(
        searchFilterUiState = searchFilterUiState,
        onSelected = onDaySelected,
        filterChipLabelDefaultText = SessionsStrings.EventDay.asString(),
        dropdownMenuItemText = { kaigiDay ->
            kaigiDay.getDropDownText(Locale.getDefault().language)
        },
        modifier = modifier.testTag(FilterDayChipTestTag),
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterDayChip() {
    var uiState by remember {
        mutableStateOf(
            SearchFilterUiState(
                selectedItems = emptyList(),
                items = DroidKaigi2023Day.entries.toList(),
            ),
        )
    }

    KaigiTheme {
        FilterDayChip(
            searchFilterUiState = uiState,
            onDaySelected = { kaigiDay, isSelected ->
                val selectedDays = uiState.selectedItems.toMutableList()
                val newSelectedDays = selectedDays.apply {
                    if (isSelected) {
                        add(kaigiDay)
                    } else {
                        remove(kaigiDay)
                    }
                }.sortedBy(DroidKaigi2023Day::start)
                uiState = uiState.copy(
                    selectedItems = newSelectedDays,
                    isSelected = newSelectedDays.isNotEmpty(),
                    selectedValues = newSelectedDays.joinToString { it.name },
                )
            },
        )
    }
}
