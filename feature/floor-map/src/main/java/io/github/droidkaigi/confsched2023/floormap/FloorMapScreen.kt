package io.github.droidkaigi.confsched2023.floormap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.floormap.component.FloorLevelSwitcher
import io.github.droidkaigi.confsched2023.floormap.component.FloorMap
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventList
import io.github.droidkaigi.confsched2023.floormap.section.FloorMapSideEventListUiState
import io.github.droidkaigi.confsched2023.model.FloorLevel
import io.github.droidkaigi.confsched2023.model.SideEvent
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val floorMapScreenRoute = "floorMap"
fun NavGraphBuilder.nestedFloorMapScreen(
    onSideEventClick: (SideEvent) -> Unit,
) {
    composable(floorMapScreenRoute) {
        FloorMapScreen(
            onSideEventClick = onSideEventClick,
        )
    }
}

fun NavController.navigateFloorMapScreen() {
    navigate(floorMapScreenRoute)
}

const val FloorMapScreenTestTag = "FloorMapScreen"

@Composable
fun FloorMapScreen(
    onSideEventClick: (SideEvent) -> Unit,
    viewModel: FloorMapScreenViewModel = hiltViewModel<FloorMapScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )

    FloorMapScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSideEventClick = onSideEventClick,
        updateFloorLevelState = viewModel::updateFloorLevelState
    )
}

data class FloorMapScreenUiState(
    val floorLevel: FloorLevel,
    val floorMapSideEventListUiState: FloorMapSideEventListUiState,
)

@Composable
private fun FloorMapScreen(
    uiState: FloorMapScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSideEventClick: (SideEvent) -> Unit,
    updateFloorLevelState: (FloorLevel) -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(FloorMapScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Box(
                Modifier
                    .fillMaxSize()
//                    .padding(padding) FIXME コメントを外すとSnackbarの分paddingが合わなくなる,
            ) {
                Column(
                    Modifier
                        .matchParentSize()
                ) {
                    Text(
                        text = "Please implement FloorMapScreen!!!",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    FloorMap(floorLevel = uiState.floorLevel)
                    FloorMapSideEventList(
                        uiState = uiState.floorMapSideEventListUiState,
                        onSideEventClick = onSideEventClick,
                        modifier = Modifier.weight(1f),
                    )
                }
                FloorLevelSwitcher(
                    selectingFloorLevel = uiState.floorLevel,
                    updateFloorLevelState = updateFloorLevelState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                )
            }
        },
    )
}
