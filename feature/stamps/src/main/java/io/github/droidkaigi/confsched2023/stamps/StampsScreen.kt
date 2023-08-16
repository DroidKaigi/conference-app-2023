package io.github.droidkaigi.confsched2023.stamps

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
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList

const val stampsScreenRoute = "stamps"
fun NavGraphBuilder.nestedStampsScreen(
    onStampsClick: () -> Unit,
) {
    composable(stampsScreenRoute) {
        StampsScreen(
            onStampsClick = onStampsClick,
        )
    }
}

fun NavController.navigateStampsScreen() {
    navigate(stampsScreenRoute)
}

const val StampsScreenTestTag = "StampsScreen"

@Composable
fun StampsScreen(
    onStampsClick: () -> Unit,
    viewModel: StampsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    StampsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onStampsClick = onStampsClick,
    )
}

data class StampsScreenUiState(
    val stamps: ImmutableList<String>,
)

@Composable
private fun StampsScreen(
    uiState: StampsScreenUiState,
    snackbarHostState: SnackbarHostState,
    onStampsClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(StampsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                Modifier
                    .padding(padding),
            ) {
                Text(
                    text = "Please implement StampsScreen!!!",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
    )
}
