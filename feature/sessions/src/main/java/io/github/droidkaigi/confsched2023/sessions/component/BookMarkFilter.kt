package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day

@Composable
fun BookMarkFilter(
    currentDayFilter: List<DroidKaigi2023Day>,
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
        borderColor = Color(0xFF707974),
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
                Text(
                    text = "全て",
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
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
                Text(
                    text = DroidKaigi2023Day.Day1.name,
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
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
                Text(
                    text = DroidKaigi2023Day.Day2.name,
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
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
                Text(
                    text = DroidKaigi2023Day.Day3.name,
                    fontWeight = FontWeight(500),
                    fontSize = 17.sp,
                )
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
