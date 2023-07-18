package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableCategory
import io.github.droidkaigi.confsched2023.sessions.strings.SessionsStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterCategoryChip(
    selectedCategories: List<TimetableCategory>,
    categories: List<TimetableCategory>,
    onCategoriesSelected: (TimetableCategory, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    selectedCategoriesValues: String = "",
    onFilterCategoryChipClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val onCategoriesSelectedUpdated by rememberUpdatedState(newValue = onCategoriesSelected)

    Box(
        modifier = modifier,
    ) {
        FilterChip(
            selected = isSelected,
            onClick = {
                onFilterCategoryChipClicked()
                expanded = true
            },
            label = { Text(text = selectedCategoriesValues.ifEmpty { SessionsStrings.Category.asString() }) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = category.title.currentLangTitle,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    leadingIcon = {
                        if (selectedCategories.contains(category)) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    onClick = {
                        onCategoriesSelectedUpdated(
                            category,
                            selectedCategories
                                .contains(category)
                                .not(),
                        )
                        expanded = false
                    },
                )
            }
        }
    }
}
