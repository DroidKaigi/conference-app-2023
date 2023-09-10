package io.github.droidkaigi.confsched2023.floormap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.floormap.FloorMapContentUiState.LargeFloorMapContentUiState
import io.github.droidkaigi.confsched2023.floormap.FloorMapContentUiState.SmallFloorMapContentUiState
import io.github.droidkaigi.confsched2023.floormap.component.FloorLevelSwitcher
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapAndSideEventList
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapUiState
import io.github.droidkaigi.confsched2023.floormap.section.fadingEdge
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.FloorLevel.Basement
import io.github.droidkaigi.confsched2023.model.FloorLevel.Ground
import io.github.droidkaigi.confsched2023.model.SideEvents
import io.github.droidkaigi.confsched2023.ui.AutoSizableText
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.toImmutableList

const val floorMapScreenRoute = "floorMap"
fun NavGraphBuilder.nestedFloorMapScreen(
    windowSize: WindowSizeClass,
    onSideEventClick: (url: String) -> Unit,
    contentPadding: PaddingValues,
) {
    composable(floorMapScreenRoute) {
        FloorMapScreen(
            windowSize = windowSize,
            onSideEventClick = onSideEventClick,
            contentPadding = contentPadding,
        )
    }
}

fun NavController.navigateFloorMapScreen() {
    navigate(floorMapScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val FloorMapScreenTestTag = "FloorMapScreen"

@Composable
fun FloorMapScreen(
    windowSize: WindowSizeClass,
    onSideEventClick: (url: String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: FloorMapScreenViewModel = hiltViewModel<FloorMapScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    FloorMapScreen(
        widthSizeClass = windowSize.widthSizeClass,
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSideEventClick = onSideEventClick,
        onClickFloorLevelSwitcher = viewModel::onClickFloorLevelSwitcher,
        contentPadding = contentPadding,
    )
}

data class FloorMapScreenUiState(
    val floorLevel: FloorLevel,
    val largeFloorMapContentUiState: LargeFloorMapContentUiState,
    val smallFloorMapContentUiState: SmallFloorMapContentUiState,
)

sealed interface FloorMapContentUiState {
    data class LargeFloorMapContentUiState(
        val baseSideEventListUiState: FloorMapSideEventListUiState,
        val groundSideEventListUiState: FloorMapSideEventListUiState,
    ) : FloorMapContentUiState

    data class SmallFloorMapContentUiState(
        val floorMapUiState: FloorMapUiState,
        val sideEventListUiState: FloorMapSideEventListUiState,
    ) : FloorMapContentUiState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FloorMapScreen(
    widthSizeClass: WindowWidthSizeClass,
    uiState: FloorMapScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSideEventClick: (url: String) -> Unit,
    onClickFloorLevelSwitcher: (FloorLevel) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.testTag(FloorMapScreenTestTag),
        topBar = {
            TopAppBar(
                title = {
                    if (scrollBehavior.state.overlappedFraction == 0f) {
                        AutoSizableText(
                            text = FloorMapStrings.Title.asString(),
                            minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 1,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    } else {
                        AutoSizableText(
                            text = FloorMapStrings.Title.asString(),
                            modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
                            minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(layoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
        content = { innerPadding ->
            FloorMapContent(
                contentPadding = innerPadding,
                widthSizeClass = widthSizeClass,
                nestedScrollConnection = scrollBehavior.nestedScrollConnection,
                uiState = uiState,
                onSideEventClick = onSideEventClick,
                onClickFloorLevelSwitcher = onClickFloorLevelSwitcher,
            )
        },
    )
}

@Composable
private fun FloorMapContent(
    contentPadding: PaddingValues,
    widthSizeClass: WindowWidthSizeClass,
    nestedScrollConnection: NestedScrollConnection,
    uiState: FloorMapScreenUiState,
    onSideEventClick: (url: String) -> Unit,
    onClickFloorLevelSwitcher: (FloorLevel) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val childContentPadding = PaddingValues(
        start = contentPadding.calculateStartPadding(layoutDirection) + 16.dp,
        top = contentPadding.calculateTopPadding(),
        end = contentPadding.calculateEndPadding(layoutDirection) + 16.dp,
        bottom = contentPadding.calculateBottomPadding(),
    )
    BoxWithConstraints(
        Modifier,
    ) {
        if (widthSizeClass != WindowWidthSizeClass.Compact) {
            LargeScreenContent(
                uiState = uiState.largeFloorMapContentUiState,
                onSideEventClick = onSideEventClick,
                contentPadding = childContentPadding,
            )
        } else {
            MobileContent(
                nestedScrollConnection = nestedScrollConnection,
                uiState = uiState,
                onSideEventClick = onSideEventClick,
                onClickFloorLevelSwitcher = onClickFloorLevelSwitcher,
                contentPadding = childContentPadding,
            )
        }
    }
}

@Composable
private fun MobileContent(
    nestedScrollConnection: NestedScrollConnection,
    uiState: FloorMapScreenUiState,
    onSideEventClick: (url: String) -> Unit,
    onClickFloorLevelSwitcher: (FloorLevel) -> Unit,
    contentPadding: PaddingValues,
) {
    val density = LocalDensity.current
    var boxHeight by remember { mutableIntStateOf(0) }
    var switcherPosition by remember { mutableFloatStateOf(0f) }
    val switcherBottomPadding = 24.dp
    val gradientStartRatio by remember {
        derivedStateOf {
            if (boxHeight == 0 || switcherPosition == 0f) {
                1f
            } else {
                switcherPosition / (boxHeight - with(density) { switcherBottomPadding.toPx() })
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .onGloballyPositioned {
                boxHeight = it.size.height
            },
    ) {
        FloorMapAndSideEventList(
            floorMapUiState = uiState.smallFloorMapContentUiState.floorMapUiState,
            sideEventListUiState = uiState.smallFloorMapContentUiState.sideEventListUiState,
            onSideEventClick = onSideEventClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
                .nestedScroll(nestedScrollConnection)
                .fadingEdge(
                    Brush.verticalGradient(
                        gradientStartRatio to Color.Black,
                        1f to Color.Transparent,
                    ),
                ),
            contentPadding = PaddingValues(),
        )
        FloorLevelSwitcher(
            selectingFloorLevel = uiState.floorLevel,
            onClickFloorLevelSwitcher = onClickFloorLevelSwitcher,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = switcherBottomPadding)
                .onGloballyPositioned {
                    switcherPosition = it.positionInParent().y
                },
        )
    }
}

@Composable
private fun LargeScreenContent(
    uiState: LargeFloorMapContentUiState,
    contentPadding: PaddingValues,
    onSideEventClick: (url: String) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    Row(
        Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.width(contentPadding.calculateStartPadding(layoutDirection)))
        Column(
            modifier = Modifier.weight(0.8f),
        ) {
            FloorMapAndSideEventList(
                floorMapUiState = FloorMapUiState.of(Basement),
                sideEventListUiState = uiState.baseSideEventListUiState,
                onSideEventClick = onSideEventClick,
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding(),
                ),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(0.8f),
        ) {
            FloorMapAndSideEventList(
                floorMapUiState = FloorMapUiState.of(Ground),
                sideEventListUiState = uiState.groundSideEventListUiState,
                onSideEventClick = onSideEventClick,
                contentPadding = PaddingValues(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding(),
                ),
            )
        }
        Spacer(modifier = Modifier.width(contentPadding.calculateEndPadding(layoutDirection)))
    }
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun PreviewFloorMapScreen() {
    KaigiTheme {
        Surface {
            FloorMapScreen(
                widthSizeClass = WindowWidthSizeClass.Compact,
                uiState = FloorMapScreenUiState(
                    floorLevel = Basement,
                    largeFloorMapContentUiState = LargeFloorMapContentUiState(
                        baseSideEventListUiState = FloorMapSideEventListUiState(
                            sideEvents = SideEvents.filter { it.floorLevel == Basement }
                                .toImmutableList(),
                        ),
                        groundSideEventListUiState = FloorMapSideEventListUiState(
                            sideEvents = SideEvents.filter { it.floorLevel == Ground }
                                .toImmutableList(),
                        ),
                    ),
                    smallFloorMapContentUiState = SmallFloorMapContentUiState(
                        floorMapUiState = FloorMapUiState.of(Basement),
                        sideEventListUiState = FloorMapSideEventListUiState(
                            sideEvents = SideEvents.filter { it.floorLevel == Basement }
                                .toImmutableList(),
                        ),
                    ),
                ),
                snackbarHostState = SnackbarHostState(),
                onSideEventClick = {},
                onClickFloorLevelSwitcher = {},
            )
        }
    }
}
