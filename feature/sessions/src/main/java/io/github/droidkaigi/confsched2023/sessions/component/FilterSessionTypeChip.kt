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
import io.github.droidkaigi.confsched2023.model.TimetableSessionType
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.SessionType

const val FilterSessionTypeChipTestTag = "FilterSessionTypeChip"

@Composable
fun FilterSessionTypeChip(
    dropdownFilterChipUiState: DropdownFilterChipUiState<TimetableSessionType>,
    onSessionTypeSelected: (TimetableSessionType, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onFilterSessionTypeChipClicked: () -> Unit,
) {
    DropdownFilterChip(
        dropdownFilterChipUiState = dropdownFilterChipUiState,
        onSelected = onSessionTypeSelected,
        filterChipLabelDefaultText = SessionType.asString(),
        onFilterChipClick = onFilterSessionTypeChipClicked,
        dropdownMenuItemText = { sessionType ->
            sessionType.label.currentLangTitle
        },
        modifier = modifier.testTag(FilterSessionTypeChipTestTag),
    )
}

@MultiThemePreviews
@Composable
fun PreviewFilterSessionTypeChip() {
    var uiState by remember {
        mutableStateOf(
            DropdownFilterChipUiState(
                selectedItems = emptyList(),
                items = TimetableSessionType.entries.toList(),
            ),
        )
    }

    KaigiTheme {
        FilterSessionTypeChip(
            dropdownFilterChipUiState = uiState,
            onSessionTypeSelected = { sessionType, isSelected ->
                val selectedSessionTypes = uiState.selectedItems.toMutableList()
                val newSelectedSessionTypes = selectedSessionTypes.apply {
                    if (isSelected) {
                        add(sessionType)
                    } else {
                        remove(sessionType)
                    }
                }
                uiState = uiState.copy(
                    selectedItems = newSelectedSessionTypes,
                    isSelected = newSelectedSessionTypes.isNotEmpty(),
                    selectedValues = newSelectedSessionTypes.joinToString { it.label.currentLangTitle },
                )
            },
            onFilterSessionTypeChipClicked = {},
        )
    }
}
