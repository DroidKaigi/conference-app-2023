package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.droidkaigi.confsched2023.model.TimetableItem

@Composable
fun TimetableItemDetailSummary(
    titleItem: TimetableItem,
    modifier: Modifier = Modifier,
) {
    TimetableItemDetailSummaryCard(timetableItem = titleItem)
}
