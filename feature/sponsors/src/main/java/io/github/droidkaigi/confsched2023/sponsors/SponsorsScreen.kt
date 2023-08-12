package io.github.droidkaigi.confsched2023.sponsors

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
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList

const val sponsorsScreenRoute = "sponsors"
fun NavGraphBuilder.sponsorsScreen(
    onSponsorClick: (Sponsor) -> Unit,
) {
    composable(sponsorsScreenRoute) {
        SponsorsScreen(
            onSponsorClick = onSponsorClick,
        )
    }
}

fun NavController.navigateSponsorsScreen() {
    navigate(sponsorsScreenRoute)
}

const val SponsorsScreenTestTag = "SponsorsScreen"

@Composable
fun SponsorsScreen(
    onSponsorClick: (Sponsor) -> Unit,
    viewModel: SponsorsScreenViewModel = hiltViewModel<SponsorsScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    println("mylog:" + uiState)
    SponsorsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSponsorClick = onSponsorClick,
    )
}

data class SponsorsScreenUiState(
    val sponsors: ImmutableList<Sponsor>,
)

@Composable
private fun SponsorsScreen(
    uiState: SponsorsScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSponsorClick: (Sponsor) -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(SponsorsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                Modifier
                    .padding(padding),
            ) {
                Text(
                    text = "Please implement SponsorsScreen!!!",
                    style = MaterialTheme.typography.titleLarge,
                )
                uiState.sponsors.forEach {
                    Text(it.toString())
                }
            }
        },
    )
}
