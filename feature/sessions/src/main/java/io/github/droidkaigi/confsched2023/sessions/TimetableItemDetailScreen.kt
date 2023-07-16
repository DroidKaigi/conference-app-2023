package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailContent
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailFooter
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailHeader
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailSummary
import io.github.droidkaigi.confsched2023.sessions.strings.TimetableItemDetailViewModel

const val timetableItemDetailScreenRouteItemIdParameterName = "timetableItemId"
const val timetableItemDetailScreenRoute =
    "timetableItemDetail/{$timetableItemDetailScreenRouteItemIdParameterName}"

fun NavGraphBuilder.sessionScreens(onNavigationIconClick: () -> Unit) {
    composable(timetableItemDetailScreenRoute) {
        TimetableItemDetailScreen(
            onNavigationIconClick = onNavigationIconClick,
        )
    }
    composable(bookMarkScreenRoute) {
        BookMarkScreen(
            onClickBackPress = onNavigationIconClick
        )
    }
}

fun NavController.navigateToTimetableItemDetailScreen(
    timetableItemId: TimetableItemId,
) {
    navigate(
        timetableItemDetailScreenRoute.replace(
            "{$timetableItemDetailScreenRouteItemIdParameterName}",
            timetableItemId.value,
        ),
    )
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

sealed class TimetableItemDetailScreenUiState() {
    object Loading : TimetableItemDetailScreenUiState()
    data class Loaded(
        val timetableItem: TimetableItem,
        val isBookmarked: Boolean,
    ) : TimetableItemDetailScreenUiState()
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
