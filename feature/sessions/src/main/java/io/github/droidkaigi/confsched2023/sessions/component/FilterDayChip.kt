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
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.sessions.strings.SessionsStrings
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDayChip(
    selectedDays: List<DroidKaigi2023Day>,
    kaigiDays: List<DroidKaigi2023Day>,
    onDaySelected: (DroidKaigi2023Day, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    selectedDaysValues: String = "",
) {
    var expanded by remember { mutableStateOf(false) }
    val onDaySelectedUpdated by rememberUpdatedState(newValue = onDaySelected)

    Box(
        modifier = modifier,
    ) {
        FilterChip(
            selected = isSelected,
            onClick = { expanded = true },
            label = { Text(text = selectedDaysValues.ifEmpty { SessionsStrings.EventDay.asString() }) },
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
            kaigiDays.forEach { kaigiDay ->
                val japanese = "ja"

                val date = kaigiDay.start.toLocalDateTime(TimeZone.currentSystemDefault())

                val year = if (Locale.getDefault().language == japanese) {
                    "${date.year}年"
                } else {
                    "${date.year}"
                }

                val month = if (Locale.getDefault().language == japanese) {
                    "${date.monthNumber}月"
                } else {
                    date.month.name.lowercase().replaceFirstChar { it.uppercase() }
                }

                val day = if (Locale.getDefault().language == japanese) {
                    "${date.dayOfMonth}日"
                } else {
                    "${date.dayOfMonth}th"
                }

                DropdownMenuItem(
                    text = {
                        Text(
                            text = "${kaigiDay.name} ($year $month $day)",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    leadingIcon = {
                        if (selectedDays.contains(kaigiDay)) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                    onClick = {
                        onDaySelectedUpdated(
                            kaigiDay,
                            selectedDays
                                .contains(kaigiDay)
                                .not(),
                        )
                        expanded = false
                    },
                )
            }
        }
    }
}
