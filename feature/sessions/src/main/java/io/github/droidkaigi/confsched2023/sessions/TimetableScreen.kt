package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.rememberTimetableScreenScrollState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheet
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState
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
    val contentUiState: TimetableSheetUiState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onContributorsClick: () -> Unit,
    onFavoriteClick: (TimetableItem.Session) -> Unit,
) {
    val state = rememberTimetableScreenScrollState()

    Scaffold(
        modifier = Modifier
            .testTag(TimetableScreenTestTag)
            .nestedScroll(state.screenNestedScrollConnection),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        topBar = {
            Column {
                // TODO: Implement TopAppBar design
                TopAppBar(
                    title = {
                        Text(text = "KaigiApp")
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            state.onHeaderPositioned(coordinates.size.height.toFloat())
                        },
                ) {
                    // TODO: Implement header desing(title and image etc..)
                    Spacer(modifier = Modifier.height(130.dp))
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        TimetableSheet(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(
                        constraints.copy(maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt()),
                    )
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0 + (state.sheetScrollOffset / 2).roundToInt())
                    }
                },
            onContributorsClick = onContributorsClick,
            uiState = uiState.contentUiState,
            timetableScreenScrollState = state,
            onFavoriteClick = onFavoriteClick,
        )
    }
}
