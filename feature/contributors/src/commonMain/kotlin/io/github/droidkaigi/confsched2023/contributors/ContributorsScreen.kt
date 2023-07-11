package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

const val contributorsScreenRoute = "contributors"

@Composable
fun ContributorsScreen(viewModel: ContributorsViewModel) {
    val contributors = Contributors()
    Text(text = contributors.greet())
    val sessions by viewModel.sessions.collectAsState()
    Column {
        sessions.timetableItems.forEach {
            Text(
                text = it.title.currentLangTitle,
            )
        }
    }
}
