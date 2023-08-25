package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
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
    contentPadding: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsState()
    ContributorsScreen(
        uiState = uiState,
        onBackClick = onNavigationIconClick,
        onContributorItemClick = onContributorItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContributorsScreen(
    uiState: ContributorsUiState,
    onBackClick: () -> Unit,
    onContributorItemClick: (url: String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
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
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        Contributors(
            contributors = uiState.contributors,
            onContributorItemClick = onContributorItemClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        )
    }
}

@Composable
private fun Contributors(
    contributors: PersistentList<Contributor>,
    onContributorItemClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // FIXME: Bottom Inset not implemented
    LazyColumn(
        modifier = modifier
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