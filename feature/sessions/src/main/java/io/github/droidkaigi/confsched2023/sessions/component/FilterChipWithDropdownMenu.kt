package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

data class SearchFilterItemUiState<T>(
    val selectedItems: List<T>,
    val items: List<T>,
    val isSelected: Boolean = false,
    val selectedValues: String = "",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterChipWithDropdownMenu(
    searchFilterItemUiState: SearchFilterItemUiState<T>,
    onSelected: (T, Boolean) -> Unit,
    filterChipLabel: @Composable () -> Unit,
    dropdownMenuItemText: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onFilterChipClick: (() -> Unit)? = null,
    filterChipLeadingIcon: @Composable (() -> Unit)? = null,
    filterChipTrailingIcon: @Composable (() -> Unit)? = null,
    dropdownMenuItemLeadingIcon: @Composable ((List<T>, T) -> Unit)? = null,
    dropdownMenuItemTrailingIcon: @Composable ((List<T>, T) -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val onSelectedUpdated by rememberUpdatedState(newValue = onSelected)

    val expandMenu = { expanded = true }
    val shrinkMenu = { expanded = false }

    val selectedItems = searchFilterItemUiState.selectedItems

    Box(
        modifier = modifier,
    ) {
        FilterChip(
            selected = searchFilterItemUiState.isSelected,
            onClick = {
                onFilterChipClick?.invoke()
                expandMenu()
            },
            label = filterChipLabel,
            leadingIcon = filterChipLeadingIcon,
            trailingIcon = filterChipTrailingIcon,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = shrinkMenu,
        ) {
            searchFilterItemUiState.items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        dropdownMenuItemText(item)
                    },
                    leadingIcon = dropdownMenuItemLeadingIcon?.let { icon ->
                        { icon(selectedItems, item) }
                    },
                    trailingIcon = dropdownMenuItemTrailingIcon?.let { icon ->
                        { icon(selectedItems, item) }
                    },
                    onClick = {
                        onSelectedUpdated(
                            item,
                            selectedItems.contains(item).not(),
                        )
                        shrinkMenu()
                    },
                )
            }
        }
    }
}
