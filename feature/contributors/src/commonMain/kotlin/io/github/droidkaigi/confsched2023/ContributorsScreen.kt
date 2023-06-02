package io.github.droidkaigi.confsched2023

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ContributorsScreen() {
    val contributors = Contributors()
    Text(text = contributors.greet())
}
