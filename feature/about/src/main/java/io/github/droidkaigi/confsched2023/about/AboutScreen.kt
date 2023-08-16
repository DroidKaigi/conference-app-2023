package io.github.droidkaigi.confsched2023.about

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
) {
    composable(aboutScreenRoute) {
        AboutScreen(
            onAboutItemClick = onAboutItemClick,
            onLinkClick = onLinkClick,
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
    onLinkClick: (url: String) -> Unit,
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
        onLinkClick = onLinkClick,
    )
}

class AboutScreenUiState()

@Composable
private fun AboutScreen(
    uiState: AboutScreenUiState,
    snackbarHostState: SnackbarHostState,
    onAboutItemClick: (AboutItem) -> Unit,
    onLinkClick: (url: String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(AboutScreenTestTag),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            LazyColumn(
                Modifier
                    .padding(padding),
            ) {
                item {
                    Text(
                        text = "Please implement AboutScreen!!!",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
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
