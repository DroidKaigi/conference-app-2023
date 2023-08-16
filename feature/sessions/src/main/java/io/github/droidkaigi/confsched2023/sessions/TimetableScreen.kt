package io.github.droidkaigi.confsched2023.sessions

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableTopArea
import io.github.droidkaigi.confsched2023.sessions.component.rememberTimetableScreenScrollState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableHeader
import io.github.droidkaigi.confsched2023.sessions.section.TimetableListUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheet
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.toPersistentMap
import kotlin.math.roundToInt

const val timetableScreenRoute = "timetable"
fun NavGraphBuilder.nestedSessionScreens(
    onSearchClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(timetableScreenRoute) {
        TimetableScreen(
            onSearchClick = onSearchClick,
            onTimetableItemClick = onTimetableItemClick,
            onBookmarkIconClick = onBookmarkIconClick,
        )
    }
}

fun NavController.navigateTimetableScreen() {
    navigate(timetableScreenRoute)
}

const val TimetableScreenTestTag = "TimetableScreen"

@Composable
fun TimetableScreen(
    onSearchClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
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
        onBookmarkIconClick = onBookmarkIconClick,
        onSearchClick = onSearchClick,
        onTimetableUiChangeClick = viewModel::onUiTypeChange,
    )
}

data class TimetableScreenUiState(
    val contentUiState: TimetableSheetUiState,
)

private val timetableTopBackgroundLight = Color(0xFFF6FFD3)
private val timetableTopBackgroundDark = Color(0xFF2D4625)

@Composable
@ReadOnlyComposable
private fun timetableTopBackground() = if (!isSystemInDarkTheme()) {
    timetableTopBackgroundLight
} else {
    timetableTopBackgroundDark
}

private val timetableTopGradientLight = Color(0xFFA9E5FF)
private val timetableTopGradientDark = Color(0xFF050D10)

@Composable
@ReadOnlyComposable
private fun timetableTopGradient() = if (!isSystemInDarkTheme()) {
    timetableTopGradientLight
} else {
    timetableTopGradientDark
}

@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTimetableUiChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTimetableScreenScrollState()
    val gradientEndRatio =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 0.2f
        else 0.5f
    Scaffold(
        modifier = modifier
            .testTag(TimetableScreenTestTag)
            .nestedScroll(state.screenNestedScrollConnection)
            .background(timetableTopBackground())
            .background(
                Brush.verticalGradient(
                    0f to timetableTopGradient(),
                    gradientEndRatio to Color.Transparent,
                )
            ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(
                    WindowInsets.safeContent.asPaddingValues(),
                ),
            )
        },
        topBar = {
            TimetableTopArea(
                onTimetableUiChangeClick,
                onSearchClick,
                onBookmarkIconClick,
            )
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            TimetableHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        state.onHeaderPositioned(
                            coordinates.size.height.toFloat() - innerPadding.calculateTopPadding().value,
                        )
                    },
            )
            TimetableSheet(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 130.dp)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            constraints.copy(maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt()),
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(
                                0,
                                0 + (state.sheetScrollOffset / 2).roundToInt(),
                            )
                        }
                    },
                onTimetableItemClick = onTimetableItemClick,
                uiState = uiState.contentUiState,
                timetableScreenScrollState = state,
                onFavoriteClick = onBookmarkClick,
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Composable
fun PreviewTimetableScreenDark() {
    PreviewTimetableScreen()
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun PreviewTimetableScreenLight() {
    PreviewTimetableScreen()
}

@Composable
private fun PreviewTimetableScreen() {
    KaigiTheme {
        TimetableScreen(
            TimetableScreenUiState(
                TimetableSheetUiState.ListTimetable(
                    mapOf(
                        DroidKaigi2023Day.Day1 to TimetableListUiState(
                            mapOf<String, List<TimetableItem>>().toPersistentMap(),
                            Timetable()
                        )
                    )
                )
            ),
            SnackbarHostState(),
            {},
            {},
            {},
            {},
            {},
            Modifier.statusBarsPadding()
        )
    }
}
