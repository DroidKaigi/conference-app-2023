package io.github.droidkaigi.confsched2023.achievements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.achievements.ClickedAchievementState.Clicked
import io.github.droidkaigi.confsched2023.achievements.component.AchievementHighlightAnimation
import io.github.droidkaigi.confsched2023.achievements.section.AchievementList
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.model.Achievement
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
        popUpTo(id = graph.findStartDestination().id) {
            saveState = true
        }
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
        showAnimation = { achievement -> viewModel.onClickAchievement(achievement) },
        finishAnimation = viewModel::onFinishAnimation,
        onDisplayedInitialDialog = viewModel::onDisplayedInitialDialog,
    )
}

data class AchievementsScreenUiState(
    val achievementListUiState: AchievementListUiState,
    val isShowInitialDialog: Boolean,
    val clickedAchievement: ClickedAchievementState,
)

sealed interface ClickedAchievementState {
    data class Clicked(
        val achievement: Achievement,
        val animationRawId: Int,
    ) : ClickedAchievementState
    data object NotClicked : ClickedAchievementState
}

@Composable
private fun AchievementsScreen(
    uiState: AchievementsScreenUiState,
    snackbarHostState: SnackbarHostState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
    showAnimation: (Achievement) -> Unit,
    finishAnimation: () -> Unit,
    onDisplayedInitialDialog: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    Box {
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
                if (uiState.isShowInitialDialog) {
                    AchievementScreenDialog(
                        onDismissRequest = onDisplayedInitialDialog,
                    )
                }
                AchievementList(
                    uiState = uiState.achievementListUiState,
                    contentPadding = innerPadding,
                    onReset = onReset,
                    showAnimation = showAnimation,
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection),
                    ),
                )
            },
        )
        if (uiState.clickedAchievement is Clicked) {
            DisposableEffect(uiState.clickedAchievement) {
                onDispose {
                    finishAnimation()
                }
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.6F),
            ) {
                AchievementHighlightAnimation(
                    animationRawId = uiState.clickedAchievement.animationRawId,
                    onFinishAnimation = {
                        finishAnimation()
                    },
                )
            }
        }
    }
}

@Composable
fun AchievementScreenDialog(
    onDismissRequest: () -> Unit,
) {
    val scrollState = rememberScrollState()
    AlertDialog(
        title = {
            Text(
                text = AchievementsStrings.DialogTitle.asString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                OrderedListText(
                    order = 1,
                    text = AchievementsStrings.DialogDescription1.asString(),
                )
                OrderedListText(
                    order = 2,
                    text = AchievementsStrings.DialogDescription2.asString(),
                )
                OrderedListText(
                    order = 3,
                    text = AchievementsStrings.DialogDescription3.asString(),
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Text(
                modifier = Modifier
                    .clickable { onDismissRequest() },
                text = AchievementsStrings.DialogConfirmButton.asString(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        },
    )
}

@Composable
fun OrderedListText(
    order: Int,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = "$order.",
            modifier = Modifier
                .width(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// FIXME The dialog preview test does not pass.
// 　If you know how to fix it, I would appreciate it if you could respond.
// @MultiLanguagePreviews
// @Composable
// fun StampsScreenDialogPreview() {
//    KaigiTheme {
//        Surface {
//            StampsScreenDialog(
//                onDismissRequest = {},
//            )
//        }
//    }
// }
