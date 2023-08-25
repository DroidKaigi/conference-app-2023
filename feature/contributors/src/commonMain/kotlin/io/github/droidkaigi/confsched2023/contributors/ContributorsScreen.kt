package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import io.github.droidkaigi.confsched2023.contributors.component.ContributorListItem
import io.github.droidkaigi.confsched2023.model.Contributor
import kotlinx.collections.immutable.PersistentList

const val contributorsScreenRoute = "contributors"
const val ContributorsScreenTestTag = "ContributorsScreenTestTag"

data class ContributorsUiState(val contributors: PersistentList<Contributor>)

@Composable
fun ContributorsScreen(
    viewModel: ContributorsViewModel,
    onNavigationIconClick: () -> Unit,
    onContributorItemClick: (url: String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    val uiState by viewModel.uiState.collectAsState()
    ContributorsScreen(
        uiState = uiState,
        onBackClick = onNavigationIconClick,
        onContributorItemClick = onContributorItemClick,
        contentPadding = contentPadding,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContributorsScreen(
    uiState: ContributorsUiState,
    onBackClick: () -> Unit,
    onContributorItemClick: (url: String) -> Unit,
    contentPadding: PaddingValues,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val localLayoutDirection = LocalLayoutDirection.current
    Scaffold(
        modifier = Modifier.testTag(ContributorsScreenTestTag),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Contributor")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back", // TODO: transparent
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(
            left = contentPadding.calculateLeftPadding(localLayoutDirection),
            top = contentPadding.calculateTopPadding(),
            right = contentPadding.calculateRightPadding(localLayoutDirection),
            bottom = contentPadding.calculateBottomPadding(),
        ),
    ) { innerContentPadding ->
        Contributors(
            contributors = uiState.contributors,
            onContributorItemClick = onContributorItemClick,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = innerContentPadding,
        )
    }
}

@Composable
private fun Contributors(
    contributors: PersistentList<Contributor>,
    onContributorItemClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
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
