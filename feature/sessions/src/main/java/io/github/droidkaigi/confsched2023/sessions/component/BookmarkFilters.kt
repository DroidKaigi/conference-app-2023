package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings

const val BookmarkFilterChipAllTestTag = "BookmarkFilterChipAllTestTag"
const val BookmarkFilterChipDay1TestTag = "BookmarkFilterChipDay1TestTag"
const val BookmarkFilterChipDay2TestTag = "BookmarkFilterChipDay2TestTag"
const val BookmarkFilterChipDay3TestTag = "BookmarkFilterChipDay3TestTag"

@Composable
fun BookmarkFilters(
    isAll: Boolean,
    isDayFirst: Boolean,
    isDaySecond: Boolean,
    isDayThird: Boolean,
    onAllFilterChipClick: () -> Unit,
    onDayFirstChipClick: () -> Unit,
    onDaySecondChipClick: () -> Unit,
    onDayThirdChipClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        BookmarkFilterChip(
            labelText = SessionsStrings.BookmarkFilterAllChip.asString(),
            isSelected = isAll,
            onClick = onAllFilterChipClick,
            modifier = Modifier.testTag(BookmarkFilterChipAllTestTag),
        )
        Spacer(modifier = Modifier.size(8.dp))
        BookmarkFilterChip(
            labelText = DroidKaigi2023Day.Day1.name,
            isSelected = isDayFirst,
            onClick = onDayFirstChipClick,
            modifier = Modifier.testTag(BookmarkFilterChipDay1TestTag),
        )
        Spacer(modifier = Modifier.size(8.dp))
        BookmarkFilterChip(
            labelText = DroidKaigi2023Day.Day2.name,
            isSelected = isDaySecond,
            onClick = onDaySecondChipClick,
            modifier = Modifier.testTag(BookmarkFilterChipDay2TestTag),
        )
        Spacer(modifier = Modifier.size(8.dp))
        BookmarkFilterChip(
            labelText = DroidKaigi2023Day.Day3.name,
            isSelected = isDayThird,
            onClick = onDayThirdChipClick,
            modifier = Modifier.testTag(BookmarkFilterChipDay3TestTag),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookmarkFilterChip(
    labelText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedChipColor = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
    )
    val selectedChipBoarderColor = FilterChipDefaults.filterChipBorder(
        borderColor = MaterialTheme.colorScheme.outline,
        borderWidth = 0.dp,
    )
    FilterChip(
        onClick = onClick,
        label = {
            ChipInnerText(labelText)
        },
        modifier = modifier,
        leadingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
            }
        },
        colors = if (isSelected) {
            selectedChipColor
        } else {
            FilterChipDefaults.filterChipColors()
        },
        border = if (isSelected) {
            selectedChipBoarderColor
        } else {
            FilterChipDefaults.filterChipBorder()
        },
        selected = isSelected,
    )
}

@Composable
private fun ChipInnerText(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
}

@MultiThemePreviews
@Composable
fun BookmarkFiltersPreview() {
    KaigiTheme {
        Surface {
            BookmarkFilters(
                isAll = false,
                isDayFirst = true,
                isDaySecond = false,
                isDayThird = false,
                onAllFilterChipClick = {},
                onDayFirstChipClick = {},
                onDaySecondChipClick = {},
                onDayThirdChipClick = {},
            )
        }
    }
}
