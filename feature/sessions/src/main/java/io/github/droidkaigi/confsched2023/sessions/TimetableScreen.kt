package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.sessions.section.TimetableItemListSection
import io.github.droidkaigi.confsched2023.ui.SnackbarMessageEffect

@Composable
// TODO: Name screen level Composable function
fun TimetableScreen(
    onContributorsClick: () -> Unit
) {
    val sessionScreenViewModel: SessionScreenViewModel = hiltViewModel<SessionScreenViewModel>()
    val uiState by sessionScreenViewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()
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
        Column(Modifier.padding(innerPadding)) {
            Text(
                text = "Go to ContributorsScreen",
                modifier = Modifier.clickable {
                    onContributorsClick()
                }
            )

            TimetableItemListSection(
                uiState = uiState,
                onFavoriteClick = { session ->
                    sessionScreenViewModel.onFavoriteClick(session)
                }
            )
        }
    }
}
