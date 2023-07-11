package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableTopArea
import io.github.droidkaigi.confsched2023.sessions.component.rememberTimetableScreenScrollState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheet
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlin.math.roundToInt

const val timetableScreenRoute = "timetable"
fun NavGraphBuilder.nestedSessionScreens(
    onTimetableItemClick: (TimetableItem) -> Unit,
    onClickBookMarkIcon: () -> Unit,
) {
    composable(timetableScreenRoute) {
        TimetableScreen(
            onTimetableItemClick = onTimetableItemClick,
            onClickBookMarkIcon = onClickBookMarkIcon,
        )
    }
}

fun NavController.navigateTimetableScreen() {
    navigate(timetableScreenRoute)
}

const val TimetableScreenTestTag = "TimetableScreen"

@Composable
fun TimetableScreen(
    onTimetableItemClick: (TimetableItem) -> Unit,
    onClickBookMarkIcon: () -> Unit,
    viewModel: TimetableScreenViewModel = hiltViewModel<TimetableScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    TimetableScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onTimetableItemClick = onTimetableItemClick,
        onBookmarkClick = viewModel::onBookmarkClick,
        onClickBookMarkIcon = onClickBookMarkIcon,
        onTimetableUiChangeClick = viewModel::onUiTypeChange,
    )
}

data class TimetableScreenUiState(
    val contentUiState: TimetableSheetUiState,
)

@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onClickBookMarkIcon: () -> Unit,
    onTimetableUiChangeClick: () -> Unit,
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
            TimetableTopArea(state, onTimetableUiChangeClick, onClickBookMarkIcon)
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
            onTimetableItemClick = onTimetableItemClick,
            uiState = uiState.contentUiState,
            timetableScreenScrollState = state,
            onFavoriteClick = onBookmarkClick,
        )
    }
}
