package io.github.droidkaigi.confsched2023.sessions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

const val bookMarkScreenRoute = "bookMark"

fun NavController.navigateToBookMarkScreen() {
    navigate(bookMarkScreenRoute)
}

@Composable
fun BookMarkScreen(
    modifier: Modifier = Modifier,
) {
    Text("ブックマーク画面")
}
