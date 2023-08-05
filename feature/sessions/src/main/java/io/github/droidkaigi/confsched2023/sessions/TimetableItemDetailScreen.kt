package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailContent
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailFooter
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailHeader
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailScreenTopAppBar
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailSummaryCard

const val timetableItemDetailScreenRouteItemIdParameterName = "timetableItemId"
const val timetableItemDetailScreenRoute =
    "timetableItemDetail/{$timetableItemDetailScreenRouteItemIdParameterName}"

fun NavGraphBuilder.sessionScreens(
    onNavigationIconClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
) {
    composable(timetableItemDetailScreenRoute) {
        TimetableItemDetailScreen(
            onNavigationIconClick = onNavigationIconClick,
        )
    }
    composable(bookmarkScreenRoute) {
        BookmarkScreen(
            onBackPressClick = onNavigationIconClick,
            onTimetableItemClick = onTimetableItemClick,
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
    Scaffold(
        topBar = {
            if (uiState is TimetableItemDetailScreenUiState.Loaded) {
                TimetableItemDetailScreenTopAppBar(
                    title = uiState.timetableItem.title,
                    onNavigationIconClick = onNavigationIconClick,
                )
            }
        },
        bottomBar = {
            if (uiState is TimetableItemDetailScreenUiState.Loaded) {
                TimetableItemDetailFooter(
                    timetableItem = uiState.timetableItem,
                    isBookmarked = uiState.isBookmarked,
                    onBookmarkClick = onBookmarkClick,
                )
            }
        },
    ) { innerPadding ->
        when (uiState) {
            TimetableItemDetailScreenUiState.Loading -> {
                Text(text = "Loading")
            }

            is TimetableItemDetailScreenUiState.Loaded -> {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(scrollState),
                ) {
                    TimetableItemDetailHeader(title = uiState.timetableItem.title)
                    TimetableItemDetailSummaryCard(
                        timetableItem = uiState.timetableItem,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                    )
                    TimetableItemDetailContent(uiState = uiState.timetableItem)
                }
            }
        }
    }
}
