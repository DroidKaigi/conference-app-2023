package io.github.droidkaigi.confsched2023.about

import android.content.Context
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.about.component.AboutDroidKaigiDetail
import io.github.droidkaigi.confsched2023.about.component.AboutFooterLinks
import io.github.droidkaigi.confsched2023.about.component.aboutCredits
import io.github.droidkaigi.confsched2023.about.component.aboutOthers
import io.github.droidkaigi.confsched2023.model.AboutItem
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val aboutScreenRoute = "about"
fun NavGraphBuilder.nestedAboutScreen(
    onAboutItemClick: (AboutItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
    contentPadding: PaddingValues,
) {
    composable(aboutScreenRoute) {
        AboutScreen(
            onAboutItemClick = onAboutItemClick,
            onLinkClick = onLinkClick,
            contentPadding = contentPadding,
        )
    }
}

fun NavController.navigateAboutScreen() {
    navigate(aboutScreenRoute) {
        launchSingleTop = true
        restoreState = true
    }
}

const val AboutScreenTestTag = "AboutScreen"

@Composable
fun AboutScreen(
    onAboutItemClick: (AboutItem) -> Unit,
    viewModel: AboutScreenViewModel = hiltViewModel<AboutScreenViewModel>(),
    contentPadding: PaddingValues = PaddingValues(),
    onLinkClick: (url: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    AboutScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onAboutItemClick = onAboutItemClick,
        onLinkClick = onLinkClick,
        contentPadding = contentPadding,
    )
}

class AboutScreenUiState(
    val versionName: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreen(
    uiState: AboutScreenUiState,
    snackbarHostState: SnackbarHostState,
    onAboutItemClick: (AboutItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
    contentPadding: PaddingValues,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        modifier = Modifier.testTag(AboutScreenTestTag),
        topBar = {
            TopAppBar(
                title = {
                    if (scrollBehavior.state.overlappedFraction == 0f) {
                        Text(
                            text = AboutStrings.Title.asString(),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Medium,
                        )
                    } else {
                        Text(
                            text = AboutStrings.Title.asString(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(layoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(layoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
        content = { padding ->
            LazyColumn(
                Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = padding,
            ) {
                item {
                    AboutDroidKaigiDetail(
                        onLinkClick = onLinkClick,
                    )
                }
                aboutCredits(
                    onSponsorsItemClick = {
                        onAboutItemClick(AboutItem.Sponsors)
                    },
                    onContributorsItemClick = {
                        onAboutItemClick(AboutItem.Contributors)
                    },
                    onStaffItemClick = {
                        onAboutItemClick(AboutItem.Staff)
                    },
                )
                aboutOthers(
                    onCodeOfConductItemClick = {
                        onAboutItemClick(AboutItem.CodeOfConduct)
                    },
                    onLicenseItemClick = {
                        onAboutItemClick(AboutItem.License)
                    },
                    onPrivacyPolicyItemClick = {
                        onAboutItemClick(AboutItem.PrivacyPolicy)
                    },
                )
                item {
                    AboutFooterLinks(
                        versionName = uiState.versionName,
                        onYouTubeClick = {
                            onAboutItemClick(AboutItem.YouTube)
                        },
                        onXClick = {
                            onAboutItemClick(AboutItem.X)
                        },
                        onMediumClick = {
                            onAboutItemClick(AboutItem.Medium)
                        },
                    )
                }
            }
        },
    )
}
