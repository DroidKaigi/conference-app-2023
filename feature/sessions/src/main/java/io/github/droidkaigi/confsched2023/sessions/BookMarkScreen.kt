package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.sessions.BookMarkScreenUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.BookMarkScreenUiState.ListBookMark

const val bookMarkScreenRoute = "bookMark"

fun NavController.navigateToBookMarkScreen() {
    navigate(bookMarkScreenRoute)
}

sealed interface BookMarkScreenUiState {
    object Empty : BookMarkScreenUiState

    data class ListBookMark(
        val bookMarkedTimeline: Timetable,
    ) : BookMarkScreenUiState
}

@Composable
fun BookMarkScreen(
    viewModel: BookMarkScreenViewModel = hiltViewModel<BookMarkScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    BookMarkScreen(
        uiState = uiState,
    )
}

@Composable
private fun BookMarkScreen(
    uiState: BookMarkScreenUiState,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is Empty -> {
                Text("Empty")
            }

            is ListBookMark -> {
                Text("ListBookMark")
            }
        }
    }
}
