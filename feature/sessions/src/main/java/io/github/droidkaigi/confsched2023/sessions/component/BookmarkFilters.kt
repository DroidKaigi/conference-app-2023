package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.md_theme_light_outline
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun BookmarkFilters(
    currentDayFilter: PersistentList<DroidKaigi2023Day>,
    onClickAllFilterChip: () -> Unit,
    onClickDayFirstChip: () -> Unit,
    onClickDaySecondChip: () -> Unit,
    onClickDayThirdChip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedChipColor = AssistChipDefaults.assistChipColors(
        containerColor = Color(0xFFCEE9DB),
    )
    val selectedChipBoarderColor = AssistChipDefaults.assistChipBorder(
        borderColor = md_theme_light_outline,
        borderWidth = 0.dp,
    )
    val isAll = currentDayFilter.size == DroidKaigi2023Day.values().size
    val isDayFirst =
        currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day1
    val isDaySecond =
        currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day2
    val isDayThird =
        currentDayFilter.size == 1 && currentDayFilter.first() == DroidKaigi2023Day.Day3
    Row(modifier) {
        AssistChip(
            onClick = onClickAllFilterChip,
            label = {
                ChipInnerText(SessionsStrings.BookmarkFilterAllChip.asString())
            },
            colors = if (isAll) {
                selectedChipColor
            } else {
                AssistChipDefaults.assistChipColors()
            },
            border = if (isAll) {
                selectedChipBoarderColor
            } else {
                AssistChipDefaults.assistChipBorder()
            },
        )
        Spacer(modifier = Modifier.size(8.dp))
        AssistChip(
            onClick = onClickDayFirstChip,
            label = {
                ChipInnerText(DroidKaigi2023Day.Day1.name)
            },
            colors = if (isDayFirst) {
                selectedChipColor
            } else {
                AssistChipDefaults.assistChipColors()
            },
            border = if (isDayFirst) {
                selectedChipBoarderColor
            } else {
                AssistChipDefaults.assistChipBorder()
            },
        )
        Spacer(modifier = Modifier.size(8.dp))
        AssistChip(
            onClick = onClickDaySecondChip,
            label = {
                ChipInnerText(DroidKaigi2023Day.Day2.name)
            },
            colors = if (isDaySecond) {
                selectedChipColor
            } else {
                AssistChipDefaults.assistChipColors()
            },
            border = if (isDaySecond) {
                selectedChipBoarderColor
            } else {
                AssistChipDefaults.assistChipBorder()
            },
        )
        Spacer(modifier = Modifier.size(8.dp))
        AssistChip(
            onClick = onClickDayThirdChip,
            label = {
                ChipInnerText(DroidKaigi2023Day.Day3.name)
            },
            colors = if (isDayThird) {
                selectedChipColor
            } else {
                AssistChipDefaults.assistChipColors()
            },
            border = if (isDayThird) {
                selectedChipBoarderColor
            } else {
                AssistChipDefaults.assistChipBorder()
            },
        )
    }
}

@Composable
private fun ChipInnerText(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
}

@Preview
@Composable
fun BookmarkFiltersPreview() {
    KaigiTheme {
        Surface {
            BookmarkFilters(
                currentDayFilter = DroidKaigi2023Day.values().toList().toPersistentList(),
                onClickAllFilterChip = {},
                onClickDayFirstChip = {},
                onClickDaySecondChip = {},
                onClickDayThirdChip = {},
            )
        }
    }
}
