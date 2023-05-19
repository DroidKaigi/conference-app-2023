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
import io.github.droidkaigi.confsched2023.sessions.section.timetableItemListSection
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

@Composable
// TODO: Name screen level Composable function
fun TimetableScreen(
    onContributorsClick: () -> Unit,
) {
    val sessionScreenViewModel: SessionScreenViewModel = hiltViewModel<SessionScreenViewModel>()
    val uiState by sessionScreenViewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()
    TimetableScreen(
        snackbarHostState,
        sessionScreenViewModel,
        uiState,
        onContributorsClick,
        onFilterClick = sessionScreenViewModel::onFavoriteFilterClicked
    )
}

@Composable
private fun TimetableScreen(
    snackbarHostState: SnackbarHostState,
    sessionScreenViewModel: SessionScreenViewModel,
    uiState: SessionScreenUiState,
    onContributorsClick: () -> Unit,
    onFilterClick: () -> Unit,
) {
    SnackbarMessageEffect(
        snackbarHostState = snackbarHostState,
        userMessageStateHolder = sessionScreenViewModel.userMessageStateHolder
    )
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
                Text(
                    text = "Filter " + if (uiState.filterUiState.isChecked) "ON" else "OFF",
                    modifier = Modifier.clickable {
                        onFilterClick()
                    }
                )
            }

            timetableItemListSection(
                uiState = uiState,
                onFavoriteClick = { session ->
                    sessionScreenViewModel.onFavoriteClick(session)
                }
            )
        }
    }
}
