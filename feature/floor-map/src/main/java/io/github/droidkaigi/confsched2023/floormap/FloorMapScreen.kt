package io.github.droidkaigi.confsched2023.floormap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.floormap.component.FloorLevelSwitcher
import io.github.droidkaigi.confsched2023.floormap.section.FloorMap
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventList
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapUiState
import io.github.droidkaigi.confsched2023.floormap.section.fadingEdge
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.FloorLevel.Basement
import io.github.droidkaigi.confsched2023.model.SideEvents
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.toImmutableList

const val floorMapScreenRoute = "floorMap"
fun NavGraphBuilder.nestedFloorMapScreen(
    onSideEventClick: (url: String) -> Unit,
) {
    composable(floorMapScreenRoute) {
        FloorMapScreen(
            onSideEventClick = onSideEventClick,
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
    onSideEventClick: (url: String) -> Unit,
    viewModel: FloorMapScreenViewModel = hiltViewModel<FloorMapScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    FloorMapScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSideEventClick = onSideEventClick,
        onClickFloorLevelSwitcher = viewModel::onClickFloorLevelSwitcher,
    )
}

data class FloorMapScreenUiState(
    val floorLevel: FloorLevel,
    val floorMapUiState: FloorMapUiState,
    val floorMapSideEventListUiState: FloorMapSideEventListUiState,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FloorMapScreen(
    uiState: FloorMapScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSideEventClick: (url: String) -> Unit,
    onClickFloorLevelSwitcher: (FloorLevel) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp), // https://stackoverflow.com/a/75962622
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .testTag(FloorMapScreenTestTag)
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = FloorMapStrings.Title.asString(),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
            )
        },
        content = { innerPadding ->
            Box(
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
            ) {
                Column(
                    Modifier
                        .fillMaxSize(),
                ) {
                    FloorMap(uiState = uiState.floorMapUiState)
                    FloorMapSideEventList(
                        uiState = uiState.floorMapSideEventListUiState,
                        onSideEventClick = onSideEventClick,
                        modifier = Modifier
                            .weight(1f)
                            .fadingEdge(
                                Brush.verticalGradient(
                                    0.85f to Color.Black,
                                    1f to Color.Transparent,
                                ),
                            )
                            .padding(bottom = 56.dp),
                    )
                }
                FloorLevelSwitcher(
                    selectingFloorLevel = uiState.floorLevel,
                    onClickFloorLevelSwitcher = onClickFloorLevelSwitcher,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp),
                )
            }
        },
    )
}

@MultiThemePreviews
@MultiLanguagePreviews
@Composable
fun PreviewFloorMapScreen() {
    KaigiTheme {
        Surface {
            FloorMapScreen(
                uiState = FloorMapScreenUiState(
                    floorLevel = Basement,
                    floorMapUiState = FloorMapUiState.of(Basement),
                    floorMapSideEventListUiState = FloorMapSideEventListUiState(
                        sideEvents = SideEvents.filter { it.floorLevel == Basement }
                            .toImmutableList(),
                    ),
                ),
                snackbarHostState = SnackbarHostState(),
                onSideEventClick = {},
                onClickFloorLevelSwitcher = {},
            )
        }
    }
}
