package io.github.droidkaigi.confsched2023.achievements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.achievements.section.AchievementList
import io.github.droidkaigi.confsched2023.achievements.section.AchievementListUiState
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
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
        onDisplayedInitialDialog = viewModel::onDisplayedInitialDialog,
    )
}

data class AchievementsScreenUiState(
    val achievementListUiState: AchievementListUiState,
    val isShowInitialDialog: Boolean,
)

@Composable
private fun AchievementsScreen(
    uiState: AchievementsScreenUiState,
    snackbarHostState: SnackbarHostState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
    onDisplayedInitialDialog: () -> Unit,
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
            if (uiState.isShowInitialDialog) {
                AchievementScreenDialog(
                    onDismissRequest = onDisplayedInitialDialog,
                )
            }
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

@Composable
fun AchievementScreenDialog(
    onDismissRequest: () -> Unit,
) {
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
            Column {
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

@MultiLanguagePreviews
@Composable
fun AchievementScreenDialogPreview() {
    KaigiTheme {
        Surface {
            AchievementScreenDialog(
                onDismissRequest = {},
            )
        }
    }
}
