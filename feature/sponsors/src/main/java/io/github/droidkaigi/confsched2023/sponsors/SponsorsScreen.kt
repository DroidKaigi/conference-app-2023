package io.github.droidkaigi.confsched2023.sponsors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.sponsors.section.SponsorList
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.ImmutableList

const val sponsorsScreenRoute = "sponsors"
fun NavGraphBuilder.sponsorsScreen(
    onNavigationIconClick: () -> Unit,
    onSponsorClick: (Sponsor) -> Unit,
) {
    composable(sponsorsScreenRoute) {
        SponsorsScreen(
            onNavigationIconClick = onNavigationIconClick,
            onSponsorClick = onSponsorClick,
        )
    }
}

fun NavController.navigateSponsorsScreen() {
    navigate(sponsorsScreenRoute)
}

const val SponsorsScreenTestTag = "SponsorsScreen"

@Composable
fun SponsorsScreen(
    onNavigationIconClick: () -> Unit,
    onSponsorClick: (Sponsor) -> Unit,
    viewModel: SponsorsScreenViewModel = hiltViewModel<SponsorsScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    SponsorsScreen(
        uiState = uiState,
        onBackClick = onNavigationIconClick,
        snackbarHostState = snackbarHostState,
        onSponsorClick = onSponsorClick,
    )
}

data class SponsorsScreenUiState(
    val platinumSponsors: ImmutableList<Sponsor>,
    val goldSponsors: ImmutableList<Sponsor>,
    val supporters: ImmutableList<Sponsor>,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SponsorsScreen(
    uiState: SponsorsScreenUiState,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onSponsorClick: (Sponsor) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.testTag(SponsorsScreenTestTag),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Sponsor")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            SponsorList(
                platinumSponsors = uiState.platinumSponsors,
                goldSponsors = uiState.goldSponsors,
                supporters = uiState.supporters,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                onSponsorClick = onSponsorClick,
            )
        },
    )
}
