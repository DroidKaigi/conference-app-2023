package io.github.droidkaigi.confsched2023.stamps

import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.stamps.section.StampList
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList

const val stampsScreenRoute = "stamps"
fun NavGraphBuilder.nestedStampsScreen(
    onStampsClick: () -> Unit,
    contentPadding: PaddingValues,
) {
    composable(stampsScreenRoute) {
        StampsScreen(
            onStampsClick = onStampsClick,
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
    onStampsClick: () -> Unit,
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
    )
}

data class StampsScreenUiState(
    val lottieRawRes: Int?,
    val stamps: ImmutableList<Stamp>,
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
            var showDialog by rememberSaveable { mutableStateOf(true) }
            val onDismiss = { showDialog = false }

            if (showDialog) {
                StampsScreenDialog(
                    onDismissRequest = onDismiss,
                )
            }
            StampList(
                stamps = uiState.stamps,
                onStampsClick = onStampsClick,
                contentPadding = innerPadding,
                onReachAnimationEnd = onReachAnimationEnd,
                stampLottieRawId = stampLottieRawId,
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
fun StampsScreenDialog(
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = StampsStrings.DialogTitle.asString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column {
                OrderedListText(
                    order = 1,
                    text = StampsStrings.DialogDescription1.asString(),
                )
                OrderedListText(
                    order = 2,
                    text = StampsStrings.DialogDescription2.asString(),
                )
                OrderedListText(
                    order = 3,
                    text = StampsStrings.DialogDescription3.asString(),
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Text(
                modifier = Modifier
                    .clickable { onDismissRequest() },
                text = StampsStrings.DialogConfirmButton.asString(),
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
            text = "$order. ",
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
fun StampsScreenDialogPreview() {
    KaigiTheme {
        Surface {
            StampsScreenDialog(
                onDismissRequest = {},
            )
        }
    }
}
