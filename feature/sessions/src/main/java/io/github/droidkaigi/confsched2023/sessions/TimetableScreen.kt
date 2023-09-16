package io.github.droidkaigi.confsched2023.sessions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.DroidKaigi2023Day
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableUiType
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
    contentPadding: PaddingValues,
) {
    composable(timetableScreenRoute) {
        TimetableScreen(
            onSearchClick = onSearchClick,
            onTimetableItemClick = onTimetableItemClick,
            onBookmarkIconClick = onBookmarkIconClick,
            contentPadding = contentPadding,
            modifier = modifier,
        )
    }
}

fun NavController.navigateTimetableScreen() {
    navigate(timetableScreenRoute) {
        popUpTo(id = graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

const val TimetableScreenTestTag = "TimetableScreen"

@Composable
fun TimetableScreen(
    onSearchClick: () -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    onBookmarkIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: TimetableScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
        onReachAnimationEnd = viewModel::onReachAnimationEnd,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

data class TimetableScreenUiState(
    val contentUiState: TimetableSheetUiState,
    val timetableUiType: TimetableUiType,
    val onBookmarkIconClickStatus: Boolean,
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
    onBookmarkClick: (TimetableItem, Boolean) -> Unit,
    onBookmarkIconClick: () -> Unit,
    onSearchClick: () -> Unit,
    onTimetableUiChangeClick: () -> Unit,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val density = LocalDensity.current
    val state = rememberTimetableScreenScrollState()
    val layoutDirection = LocalLayoutDirection.current
    val gradientEndRatio =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            0.2f
        } else {
            0.5f
        }
    val timetableTopGradient = timetableTopGradient()
    val bottomPaddingPx = with(density) { contentPadding.calculateBottomPadding().toPx() }
    Scaffold(
        modifier = modifier
            .testTag(TimetableScreenTestTag)
            .nestedScroll(state.screenNestedScrollConnection)
            .background(timetableTopBackground())
            .drawWithCache {
                onDrawBehind {
                    drawRect(
                        brush = Brush.verticalGradient(
                            0f to timetableTopGradient,
                            gradientEndRatio to Color.Transparent,
                        ),
                        size = Size(
                            size.width,
                            size.height - bottomPaddingPx,
                        ),
                    )
                }
            },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TimetableTopArea(
                timetableUiType = uiState.timetableUiType,
                onTimetableUiChangeClick = onTimetableUiChangeClick,
                onSearchClick = onSearchClick,
                onTopAreaBookmarkIconClick = onBookmarkIconClick,
                onBookmarkClickStatus = uiState.onBookmarkIconClickStatus,
                onReachAnimationEnd = onReachAnimationEnd,
            )
        },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(layoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
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
                    .padding(top = 131.dp)
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
                contentPadding = PaddingValues(
                    bottom = innerPadding.calculateBottomPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
            )
        }
    }
}

@MultiThemePreviews
@Composable
fun PreviewTimetableScreenDark() {
    KaigiTheme {
        TimetableScreen(
            uiState = TimetableScreenUiState(
                contentUiState = TimetableSheetUiState.ListTimetable(
                    mapOf(
                        DroidKaigi2023Day.Day1 to TimetableListUiState(
                            mapOf<String, List<TimetableItem>>().toPersistentMap(),
                            Timetable(),
                        ),
                        DroidKaigi2023Day.Day2 to TimetableListUiState(
                            mapOf<String, List<TimetableItem>>().toPersistentMap(),
                            Timetable(),
                        ),
                    ),
                ),
                timetableUiType = TimetableUiType.Grid,
                onBookmarkIconClickStatus = false,
            ),
            snackbarHostState = SnackbarHostState(),
            onTimetableItemClick = {},
            onBookmarkClick = { _, _ -> },
            onBookmarkIconClick = {},
            onSearchClick = {},
            onTimetableUiChangeClick = {},
            onReachAnimationEnd = {},
            modifier = Modifier.statusBarsPadding(),
        )
    }
}
