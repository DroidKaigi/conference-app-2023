package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.droidkaigi.confsched2023.contributors.Contributors

@Composable
fun ContributorsScreen() {
    val contributors = Contributors()
    Text(text = contributors.greet())
}
