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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Lang
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings.SupportedLanguages
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterLanguageChip(
    selectedLanguages: ImmutableList<Lang>,
    languages: ImmutableList<Lang>,
    onLanguagesSelected: (Lang, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    selectedLanguagesValues: String = "",
    onFilterLanguageChipClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val onLanguagesSelectedUpdated by rememberUpdatedState(newValue = onLanguagesSelected)

    Box(
        modifier = modifier,
    ) {
        FilterChip(
            selected = isSelected,
            onClick = {
                onFilterLanguageChipClicked()
                expanded = true
            },
            label = { Text(text = selectedLanguagesValues.ifEmpty { SupportedLanguages.asString() }) },
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
            languages.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = language.tagName,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    leadingIcon = {
                        if (selectedLanguages.contains(language)) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    onClick = {
                        onLanguagesSelectedUpdated(
                            language,
                            selectedLanguages
                                .contains(language)
                                .not(),
                        )
                        expanded = false
                    },
                )
            }
        }
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun FilterLanguageChipPreview() {
    KaigiTheme {
        Surface {
            FilterLanguageChip(
                selectedLanguages = emptyList(),
                languages = emptyList(),
                onLanguagesSelected = { _, _ -> },
                onFilterLanguageChipClicked = {},
            )
        }
    }
}
