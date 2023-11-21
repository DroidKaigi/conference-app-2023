package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import io.github.droidkaigi.confsched2023.contributors.component.ContributorListItem
import io.github.droidkaigi.confsched2023.model.Contributor
import io.github.droidkaigi.confsched2023.ui.AutoSizableText
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect
import kotlinx.collections.immutable.PersistentList

const val contributorsScreenRoute = "contributors"
const val ContributorsScreenTestTag = "ContributorsScreenTestTag"

data class ContributorsUiState(val contributors: PersistentList<Contributor>)

@Composable
fun ContributorsScreen(
    viewModel: ContributorsViewModel,
    isTopAppBarHidden: Boolean = false,
    onNavigationIconClick: () -> Unit,
    onContributorItemClick: (url: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    ContributorsScreen(
        uiState = uiState,
        isTopAppBarHidden = isTopAppBarHidden,
        snackbarHostState = snackbarHostState,
        onBackClick = onNavigationIconClick,
        onContributorItemClick = onContributorItemClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContributorsScreen(
    uiState: ContributorsUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onContributorItemClick: (url: String) -> Unit,
    isTopAppBarHidden: Boolean,
) {
    val scrollBehavior =
        if (!isTopAppBarHidden) {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        } else {
            null
        }
    Scaffold(
        modifier = Modifier.testTag(ContributorsScreenTestTag),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (scrollBehavior != null) {
                LargeTopAppBar(
                    title = {
                        AutoSizableText(
                            text = "Contributor",
                            minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                        )
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
            }
        },
    ) { padding ->
        Contributors(
            contributors = uiState.contributors,
            onContributorItemClick = onContributorItemClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .let {
                    if (scrollBehavior != null) {
                        it.nestedScroll(scrollBehavior.nestedScrollConnection)
                    } else {
                        it
                    }
                },
        )
    }
}

@Composable
private fun Contributors(
    contributors: PersistentList<Contributor>,
    onContributorItemClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(contributors) {
            ContributorListItem(
                contributor = it,
                onClick = onContributorItemClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
