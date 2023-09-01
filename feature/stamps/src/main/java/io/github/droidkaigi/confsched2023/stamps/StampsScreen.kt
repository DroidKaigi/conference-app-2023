package io.github.droidkaigi.confsched2023.stamps

import androidx.annotation.RawRes
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
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.section.StampList
import io.github.droidkaigi.confsched2023.stamps.section.StampListUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val stampsScreenRoute = "stamps?id={id}"
const val uri = "https://github.com/DroidKaigi/conference-app-2023"
fun NavGraphBuilder.nestedStampsScreen(
    onStampsClick: () -> Unit,
    contentPadding: PaddingValues,
) {
    composable(
        stampsScreenRoute,
        deepLinks = listOf(
            androidx.navigation.navDeepLink {
                uriPattern = "$uri/$stampsScreenRoute"
            },
        ),
    ) {
        StampsScreen(
            onStampsClick = onStampsClick,
            contentPadding = contentPadding,
            id = it.arguments?.getString("id"),
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
    onStampsClick: () -> Unit,
    id: String? = null,
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
        stampLottieRawId = uiState.lottieRawRes,
        onStampsClick = { stamp ->
            onStampsClick()
            viewModel.onStampClick(stamp)
        },
        onReachAnimationEnd = viewModel::onReachAnimationEnd,
        id = id,
    )
}

data class StampsScreenUiState(
    val lottieRawRes: Int?,
    val stampListUiState: StampListUiState,
)

@Composable
private fun StampsScreen(
    uiState: StampsScreenUiState,
    snackbarHostState: SnackbarHostState,
    @RawRes
    stampLottieRawId: Int?,
    onStampsClick: (Stamp) -> Unit,
    contentPadding: PaddingValues,
    onReachAnimationEnd: () -> Unit,
    id: String? = null,
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
                onStampsClick = onStampsClick,
                contentPadding = innerPadding,
                onReachAnimationEnd = onReachAnimationEnd,
                stampLottieRawId = stampLottieRawId,
                id = id,
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
            )
        },
    )
}
