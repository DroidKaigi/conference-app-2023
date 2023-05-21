package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableFilter
import io.github.droidkaigi.confsched2023.sessions.component.TimetableFilterUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSessionListUiState
import io.github.droidkaigi.confsched2023.sessions.section.timetableItemListSection
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

@Composable
fun TimetableScreen(
    onContributorsClick: () -> Unit,
) {
    val viewModel: TimetableScreenViewModel = hiltViewModel<TimetableScreenViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = viewModel.userMessageStateHolder
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
    val timetableSessionListUiState: TimetableSessionListUiState,
    val timetableFilterUiState: TimetableFilterUiState,
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
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            item {
                Text(
                    text = "Go to ContributorsScreen",
                    modifier = Modifier.clickable {
                        onContributorsClick()
                    }
                )
            }
            item {
                TimetableFilter(
                    timetableFilterUiState = uiState.timetableFilterUiState,
                    onFilterClick = onFilterClick
                )
            }

            timetableItemListSection(
                uiState = uiState,
                onFavoriteClick = {
                    onFavoriteClick(it)
                }
            )
        }
    }
}
