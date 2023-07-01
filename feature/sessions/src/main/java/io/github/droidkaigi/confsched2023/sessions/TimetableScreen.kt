package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableNestedScrollConnection
import io.github.droidkaigi.confsched2023.sessions.component.rememberTimetableScrollState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContent
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlin.math.roundToInt

const val TimetableScreenTestTag = "TimetableScreen"

@Composable
fun TimetableScreen(
    onContributorsClick: () -> Unit,
) {
    val viewModel: TimetableScreenViewModel = hiltViewModel<TimetableScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    TimetableScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onContributorsClick = onContributorsClick,
        onFavoriteClick = viewModel::onBookmarkClick,
    )
}

data class TimetableScreenUiState(
    val contentUiState: TimetableContentUiState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onContributorsClick: () -> Unit,
    onFavoriteClick: (TimetableItem.Session) -> Unit,
) {
    val state = rememberTimetableScrollState()
    val nestedScrollConnection = remember(state) {
        TimetableNestedScrollConnection(state)
    }

    Scaffold(
        modifier = Modifier
            .testTag(TimetableScreenTestTag)
            .nestedScroll(nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        topBar = {
            // TODO: Implement design
            val insetPadding = TopAppBarDefaults.windowInsets.asPaddingValues()
            val statusBarHeight = with(LocalDensity.current) {
                insetPadding.calculateTopPadding().toPx()
            }
            LargeTopAppBar(
                title = {
                    Text(text = "KaigiApp")
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    state.updateScrollOffsetLimit(coordinates.size.height.toFloat() - statusBarHeight)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        TimetableContent(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f) // display above TopAppBar
                .padding(innerPadding)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(maxHeight = constraints.maxHeight - state.scrollOffset.roundToInt())
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0 + (state.scrollOffset / 2).roundToInt())
                    }
                },
            onContributorsClick = onContributorsClick,
            uiState = uiState.contentUiState,
            onFavoriteClick = onFavoriteClick,
        )
    }
}
