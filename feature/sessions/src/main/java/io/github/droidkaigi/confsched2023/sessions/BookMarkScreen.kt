package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.droidkaigi.confsched2023.model.Timetable

const val bookMarkScreenRoute = "bookMark"

fun NavController.navigateToBookMarkScreen() {
    navigate(bookMarkScreenRoute)
}

data class BookMarkScreenUiState(
    val bookMarkedTimeTable: Timetable
)

@Composable
fun BookMarkScreen(
    viewModel: BookMarkScreenViewModel = hiltViewModel<BookMarkScreenViewModel>(),
) {
    val uiState by viewModel.uiState.collectAsState()

    BookMarkScreen(
        uiState = uiState
    )
}

@Composable
private fun BookMarkScreen(
    uiState: BookMarkScreenUiState,
) {
    Text("BookMark画面")
}
