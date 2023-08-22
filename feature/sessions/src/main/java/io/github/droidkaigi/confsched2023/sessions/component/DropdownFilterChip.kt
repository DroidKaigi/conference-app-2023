package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.sessions.section.SearchFilterUiState

const val DropdownFilterChipItemTestTag = "DropdownFilterChipItem"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownFilterChip(
    searchFilterUiState: SearchFilterUiState<T>,
    onSelected: (T, Boolean) -> Unit,
    filterChipLabel: @Composable () -> Unit,
    dropdownMenuItemText: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    filterChipLeadingIcon: @Composable (() -> Unit)? = null,
    filterChipTrailingIcon: @Composable (() -> Unit)? = null,
    onFilterChipClick: (() -> Unit)? = null,
    dropdownMenuItemLeadingIcon: @Composable ((T) -> Unit)? = null,
    dropdownMenuItemTrailingIcon: @Composable ((T) -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val onSelectedUpdated by rememberUpdatedState(newValue = onSelected)

    val expandMenu = { expanded = true }
    val shrinkMenu = { expanded = false }

    val selectedItems = searchFilterUiState.selectedItems

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart),
    ) {
        FilterChip(
            selected = searchFilterUiState.isSelected,
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
            searchFilterUiState.items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        dropdownMenuItemText(item)
                    },
                    leadingIcon = dropdownMenuItemLeadingIcon?.let { icon ->
                        {
                            icon(item)
                        }
                    },
                    trailingIcon = dropdownMenuItemTrailingIcon?.let { icon ->
                        {
                            icon(item)
                        }
                    },
                    onClick = {
                        onSelectedUpdated(
                            item,
                            selectedItems.contains(item).not(),
                        )
                        shrinkMenu()
                    },
                    modifier = Modifier.testTag(DropdownFilterChipItemTestTag),
                )
            }
        }
    }
}

@Composable
fun <T> DropdownFilterChip(
    searchFilterUiState: SearchFilterUiState<T>,
    onSelected: (T, Boolean) -> Unit,
    filterChipLabelDefaultText: String,
    dropdownMenuItemText: (T) -> String,
    modifier: Modifier = Modifier,
    onFilterChipClick: (() -> Unit)? = null,
) {
    DropdownFilterChip(
        modifier = modifier,
        searchFilterUiState = searchFilterUiState,
        onSelected = onSelected,
        filterChipLabel = {
            Text(
                text = searchFilterUiState.selectedValues.ifEmpty {
                    filterChipLabelDefaultText
                },
            )
        },
        filterChipTrailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        },
        onFilterChipClick = onFilterChipClick,
        dropdownMenuItemText = { item ->
            Text(
                text = dropdownMenuItemText(item),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        dropdownMenuItemLeadingIcon = { item ->
            if (searchFilterUiState.selectedItems.contains(item)) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
    )
}
