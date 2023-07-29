package io.github.droidkaigi.confsched2023.about

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
import io.github.droidkaigi.confsched2023.model.AboutItem
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val aboutScreenRoute = "about"
fun NavGraphBuilder.nestedAboutScreen(
    onAboutItemClick: (AboutItem) -> Unit,
) {
    composable(aboutScreenRoute) {
        AboutScreen(
            onAboutItemClick = onAboutItemClick,
        )
    }
}

fun NavController.navigateAboutScreen() {
    navigate(aboutScreenRoute)
}

const val AboutScreenTestTag = "AboutScreen"

@Composable
fun AboutScreen(
    onAboutItemClick: (AboutItem) -> Unit,
    viewModel: AboutScreenViewModel = hiltViewModel<AboutScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    AboutScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onAboutItemClick = onAboutItemClick,
    )
}

data class AboutScreenUiState(
    val items: List<AboutItem>,
)

@Composable
private fun AboutScreen(
    uiState: AboutScreenUiState,
    snackbarHostState: SnackbarHostState,
    onAboutItemClick: (AboutItem) -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(AboutScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                Modifier
                    .padding(padding),
            ) {
                Text(
                    text = "Please implement this page!!!",
                    style = MaterialTheme.typography.titleLarge,
                )
                uiState.items.forEach {
                    Text(it.toString())
                }
            }
        },
    )
}
