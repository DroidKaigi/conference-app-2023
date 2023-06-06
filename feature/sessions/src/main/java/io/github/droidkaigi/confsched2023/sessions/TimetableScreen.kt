package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.sessions.component.TimetableFilterUiState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContent
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentUiState
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
    val timetableSessionListUiState: TimetableContentUiState,
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
    TimetableContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onContributorsClick = onContributorsClick,
        onFilterClick = onFilterClick,
        onFavoriteClick = onFavoriteClick,
    )
}
