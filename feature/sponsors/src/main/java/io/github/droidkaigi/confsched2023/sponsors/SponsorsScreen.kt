package io.github.droidkaigi.confsched2023.sponsors

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.droidkaigi.confsched2023.model.Plan
import io.github.droidkaigi.confsched2023.model.Plan.GOLD
import io.github.droidkaigi.confsched2023.model.Plan.PLATINUM
import io.github.droidkaigi.confsched2023.model.Plan.SUPPORTER
import io.github.droidkaigi.confsched2023.model.Sponsor
import io.github.droidkaigi.confsched2023.sponsors.section.SponsorListUiState
import io.github.droidkaigi.confsched2023.sponsors.section.sponsorList
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.PersistentMap

const val sponsorsScreenRoute = "sponsors"
fun NavGraphBuilder.sponsorsScreen(
    onSponsorClick: (Sponsor) -> Unit,
    onNavigationIconClick: () -> Unit
) {
    composable(sponsorsScreenRoute) {
        SponsorsScreen(
            onSponsorClick = onSponsorClick,
            onNavigationIconClick = onNavigationIconClick
        )
    }
}

fun NavController.navigateSponsorsScreen() {
    navigate(sponsorsScreenRoute)
}

const val SponsorsScreenTestTag = "SponsorsScreen"

@Composable
fun SponsorsScreen(
    onSponsorClick: (Sponsor) -> Unit,
    viewModel: SponsorsScreenViewModel = hiltViewModel<SponsorsScreenViewModel>(),
    onNavigationIconClick: () ->Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    SponsorsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSponsorClick = onSponsorClick,
        onBackClick = onNavigationIconClick
    )
}

data class SponsorsScreenUiState(
    val sponsorListUiStates: PersistentMap<Plan, SponsorListUiState>,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SponsorsScreen(
    uiState: SponsorsScreenUiState,
    snackbarHostState: SnackbarHostState,
    onSponsorClick: (Sponsor) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = SponsorsStrings.Sponsor.asString())
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
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        modifier = Modifier
            .testTag(SponsorsScreenTestTag)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ){
                sponsorList(
                    uiState = requireNotNull(uiState.sponsorListUiStates[PLATINUM]),
                    onSponsorClick = onSponsorClick,
                )
                sponsorList(
                    uiState = requireNotNull(uiState.sponsorListUiStates[GOLD]),
                    onSponsorClick = onSponsorClick,
                )
                sponsorList(
                    uiState = requireNotNull(uiState.sponsorListUiStates[SUPPORTER]),
                    onSponsorClick = onSponsorClick,
                )
                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        },
    )
}
