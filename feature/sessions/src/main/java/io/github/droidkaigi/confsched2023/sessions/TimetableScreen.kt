package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
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
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableFilter
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState
import io.github.droidkaigi.confsched2023.sessions.section.timetableContent
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

const val TimetableScreenTestTag = "TimetableScreen"

@Composable
fun TimetableScreen(
    onContributorsClick: () -> Unit,
) {
    val viewModel: TimetableScreenViewModel = hiltViewModel<TimetableScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder,
    )
    TimetableScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onContributorsClick = onContributorsClick,
        onFilterClick = viewModel::onFavoriteFilterClick,
        onFavoriteClick = viewModel::onFavoriteClick,
    )
}

data class TimetableScreenUiState(
    val contentUiState: TimetableContentUiState,
    val filterEnabled: Boolean,
    val filterIsChecked: Boolean,
)

@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    snackbarHostState: SnackbarHostState,
    onContributorsClick: () -> Unit,
    onFilterClick: () -> Unit,
    onFavoriteClick: (TimetableItem.Session) -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        contentWindowInsets = WindowInsets.statusBars,
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .testTag(TimetableScreenTestTag),
        ) {
            item {
                Text(
                    text = "Go to ContributorsScreen",
                    modifier = Modifier.clickable {
                        onContributorsClick()
                    },
                )
            }
            item {
                TimetableFilter(
                    enabled = uiState.filterEnabled,
                    isChecked = uiState.filterIsChecked,
                    onFilterClick = onFilterClick,
                )
            }
            timetableContent(
                timetableContentUiState = uiState.contentUiState,
                onFavoriteClick = onFavoriteClick,
            )
        }
    }
}
