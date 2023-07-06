package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailContent
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailFooter
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailHeader
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailSummary
import io.github.droidkaigi.confsched2023.sessions.strings.TimetableItemDetailViewModel

sealed class TimetableItemDetailScreenUiState() {
    object Loading : TimetableItemDetailScreenUiState()
    data class Loaded(
        val timetableItem: TimetableItem,
        val isBookmarked: Boolean,
    ) : TimetableItemDetailScreenUiState()
}

@Composable
fun TimetableItemDetailScreen(
    onNavigationIconClick: () -> Unit,
    viewModel: TimetableItemDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    TimetableItemDetailScreen(
        uiState = uiState,
        onNavigationIconClick = onNavigationIconClick,
        onBookmarkClick = viewModel::onBookmarkClick,
    )
}

@Composable
private fun TimetableItemDetailScreen(
    uiState: TimetableItemDetailScreenUiState,
    onNavigationIconClick: () -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
) {
    when (uiState) {
        TimetableItemDetailScreenUiState.Loading -> {
            Text(text = "Loading")
        }

        is TimetableItemDetailScreenUiState.Loaded -> {
            Column {
                TimetableItemDetailHeader()
                TimetableItemDetailSummary()
                TimetableItemDetailContent()
                TimetableItemDetailFooter()
            }
        }
    }
}
