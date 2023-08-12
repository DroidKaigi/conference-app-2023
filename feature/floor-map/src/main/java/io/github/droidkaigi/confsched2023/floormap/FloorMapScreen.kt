package io.github.droidkaigi.confsched2023.floormap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    )
}

data class FloorMapScreenUiState(
    val sideEvents: List<SideEvent>,
)

@Composable
private fun FloorMapScreen(
    uiState: FloorMapScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSideEventClick: (SideEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(FloorMapScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                Modifier
                    .padding(padding),
            ) {
                Text(
                    text = "Please implement FloorMapScreen!!!",
                    style = MaterialTheme.typography.titleLarge,
                )
                uiState.sideEvents.forEach {
                    Text(it.toString())
                }
            }
        },
    )
}
