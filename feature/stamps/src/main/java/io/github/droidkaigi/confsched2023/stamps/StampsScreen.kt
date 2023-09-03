package io.github.droidkaigi.confsched2023.stamps

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.stamps.section.StampList
import io.github.droidkaigi.confsched2023.stamps.section.StampListUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val stampsScreenRoute = "stamps"
const val uri = "https://droidkaigi.jp/apps/achievements"
fun NavGraphBuilder.nestedStampsScreen(
    contentPadding: PaddingValues,
) {
    composable(
        stampsScreenRoute,
        deepLinks = listOf(
            androidx.navigation.navDeepLink {
                uriPattern = "$uri/*"
            },
        ),
    ) {
        StampsScreen(
            contentPadding = contentPadding,
        )
    }
}

fun NavController.navigateStampsScreen() {
    navigate(stampsScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val StampsScreenTestTag = "StampsScreen"

@Composable
fun StampsScreen(
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: StampsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    StampsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        contentPadding = contentPadding,
        onReset = viewModel::onReset,
    )
}

data class StampsScreenUiState(
    val stampListUiState: StampListUiState,
)

@Composable
private fun StampsScreen(
    uiState: StampsScreenUiState,
    snackbarHostState: SnackbarHostState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        modifier = Modifier.testTag(StampsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(layoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
        content = { innerPadding ->
            StampList(
                uiState = uiState.stampListUiState,
                contentPadding = innerPadding,
                onReset = onReset,
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
            )
        },
    )
}
