package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HoursItem(
    hour: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = hour,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun TimetableGridHours() {}