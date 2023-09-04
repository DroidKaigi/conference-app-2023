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
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/StampsScreen.kt
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
=======
import io.github.droidkaigi.confsched2023.achievements.section.AchievementList
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val achievementsScreenRoute = "achievements"
fun NavGraphBuilder.nestedAchievementsScreen(
    onAchievementsClick: () -> Unit,
    contentPadding: PaddingValues,
) {
    composable(achievementsScreenRoute) {
        AchievementsScreen(
            onAchievementsClick = onAchievementsClick,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/AchievementsScreen.kt
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
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/StampsScreen.kt
fun StampsScreen(
=======
fun AchievementsScreen(
    onAchievementsClick: () -> Unit,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/AchievementsScreen.kt
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
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/StampsScreen.kt
        onReset = viewModel::onReset,
    )
}

data class StampsScreenUiState(
    val stampListUiState: StampListUiState,
=======
        achievementLottieRawId = uiState.lottieRawRes,
        onAchievementsClick = { achievement ->
            onAchievementsClick()
            viewModel.onAchievementClick(achievement)
        },
        onReachAnimationEnd = viewModel::onReachAnimationEnd,
    )
}

data class AchievementsScreenUiState(
    val lottieRawRes: Int?,
    val achievementListUiState: AchievementListUiState,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/AchievementsScreen.kt
)

@Composable
private fun AchievementsScreen(
    uiState: AchievementsScreenUiState,
    snackbarHostState: SnackbarHostState,
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/StampsScreen.kt
=======
    @RawRes
    achievementLottieRawId: Int?,
    onAchievementsClick: (Achievement) -> Unit,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/AchievementsScreen.kt
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
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/StampsScreen.kt
            StampList(
                uiState = uiState.stampListUiState,
                contentPadding = innerPadding,
                onReset = onReset,
=======
            AchievementList(
                uiState = uiState.achievementListUiState,
                onAchievementsClick = onAchievementsClick,
                contentPadding = innerPadding,
                onReachAnimationEnd = onReachAnimationEnd,
                achievementLottieRawId = achievementLottieRawId,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/AchievementsScreen.kt
                modifier = Modifier.padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
            )
        },
    )
}
