package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.sessions.TimetableItemDetailScreenUiState.Loaded
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailBottomAppBar
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailContent
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailScreenTopAppBar
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailSummaryCard
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

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
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    TimetableItemDetailScreen(
        uiState = uiState,
        onNavigationIconClick = onNavigationIconClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        snackbarHostState = snackbarHostState,
    )
}

sealed class TimetableItemDetailScreenUiState() {
    data object Loading : TimetableItemDetailScreenUiState()
    data class Loaded(
        val timetableItem: TimetableItem,
        val isBookmarked: Boolean,
    ) : TimetableItemDetailScreenUiState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableItemDetailScreen(
    uiState: TimetableItemDetailScreenUiState,
    onNavigationIconClick: () -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (uiState is TimetableItemDetailScreenUiState.Loaded) {
                TimetableItemDetailScreenTopAppBar(
                    title = uiState.timetableItem.title,
                    onNavigationIconClick = onNavigationIconClick,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        bottomBar = {
            if (uiState is TimetableItemDetailScreenUiState.Loaded) {
                TimetableItemDetailBottomAppBar(
                    timetableItem = uiState.timetableItem,
                    isBookmarked = uiState.isBookmarked,
                    onBookmarkClick = onBookmarkClick,
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        when (uiState) {
            TimetableItemDetailScreenUiState.Loading -> {
                Text(text = "Loading")
            }

            is TimetableItemDetailScreenUiState.Loaded -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                ) {
                    item {
                        TimetableItemDetailSummaryCard(
                            timetableItem = uiState.timetableItem,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                        )
                    }
                    item {
                        TimetableItemDetailContent(uiState = uiState.timetableItem)
                    }
                }
            }
        }
    }
}

@Composable
@MultiThemePreviews
@MultiLanguagePreviews
fun TimetableItemDetailScreenPreview() {
    var isBookMarked by remember { mutableStateOf(false) }

    KaigiTheme {
        Surface {
            TimetableItemDetailScreen(
                uiState = Loaded(
                    timetableItem = Session.fake(),
                    isBookmarked = isBookMarked,
                ),
                onNavigationIconClick = {},
                onBookmarkClick = {
                    isBookMarked = !isBookMarked
                },
                snackbarHostState = SnackbarHostState(),
            )
        }
    }
}
