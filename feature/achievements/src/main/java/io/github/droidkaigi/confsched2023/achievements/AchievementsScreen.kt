package io.github.droidkaigi.confsched2023.achievements

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
import io.github.droidkaigi.confsched2023.achievements.section.AchievementList
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val achievementsScreenRoute = "achievements"
const val uri = "https://droidkaigi.jp/apps/achievements"
fun NavGraphBuilder.nestedAchievementsScreen(
    contentPadding: PaddingValues,
) {
    composable(
        achievementsScreenRoute,
        deepLinks = listOf(
            androidx.navigation.navDeepLink {
                uriPattern = "$uri/*"
            },
        ),
    ) {
        AchievementsScreen(
            contentPadding = contentPadding,
        )
    }
}

fun NavController.navigateAchievementsScreen() {
    navigate(achievementsScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val AchievementsScreenTestTag = "AchievementsScreen"

@Composable
fun AchievementsScreen(
    contentPadding: PaddingValues = PaddingValues(),
    viewModel: AchievementsScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    AchievementsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        contentPadding = contentPadding,
        onReset = viewModel::onReset,
    )
}

data class AchievementsScreenUiState(
    val achievementListUiState: AchievementListUiState,
)

@Composable
private fun AchievementsScreen(
    uiState: AchievementsScreenUiState,
    snackbarHostState: SnackbarHostState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        modifier = Modifier.testTag(AchievementsScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(layoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
        content = { innerPadding ->
            AchievementList(
                uiState = uiState.achievementListUiState,
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
