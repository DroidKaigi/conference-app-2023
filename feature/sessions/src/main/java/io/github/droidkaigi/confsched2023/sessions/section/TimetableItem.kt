package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailContent
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailSummaryCard

data class TimetableItemUiState(
    val timetableItem: TimetableItem,
)

@Composable
internal fun TimetableItem(
    uiState: TimetableItemUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        item {
            TimetableItemDetailSummaryCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                timetableItem = uiState.timetableItem,
            )
        }

        item {
            TimetableItemDetailContent(uiState = uiState.timetableItem)
        }
    }
}
