package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import java.util.Locale

@Composable
fun FilterDayChip(
    searchFilterItemUiState: SearchFilterItemUiState<DroidKaigi2023Day>,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChipWithDropdownMenu(
        searchFilterItemUiState = searchFilterItemUiState,
        onSelected = onDaySelected,
        filterChipLabelDefaultText = SessionsStrings.EventDay.asString(),
        dropdownMenuItemText = { kaigiDay ->
            kaigiDay.getDropDownText(Locale.getDefault().language)
        },
        modifier = modifier,
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterDayChip() {
    var uiState by remember {
        mutableStateOf(
            SearchFilterItemUiState(
                selectedItems = emptyList(),
                items = DroidKaigi2023Day.entries.toList(),
            ),
        )
    }

    KaigiTheme {
        FilterDayChip(
            searchFilterItemUiState = uiState,
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
