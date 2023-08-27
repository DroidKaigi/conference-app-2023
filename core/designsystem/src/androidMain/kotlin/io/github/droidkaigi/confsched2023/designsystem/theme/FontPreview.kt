package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun FontPreview() {
    Column(
        Modifier.background(color = Color.White),
    ) {
        Text("Display Large", style = typography().displayLarge)
        Text("Display Medium", style = typography().displayMedium)
        Text("Display Small", style = typography().displaySmall)
        Text("Headline Large", style = typography().headlineLarge)
        Text("Headline Medium", style = typography().headlineMedium)
        Text("Headline Small", style = typography().headlineSmall)
        Text("Title Large", style = typography().titleLarge)
        Text("Title Medium", style = typography().titleMedium)
        Text("Title Small", style = typography().titleSmall)
        Text("Label Large", style = typography().labelLarge)
        Text("Label Medium", style = typography().labelMedium)
        Text("Label Small", style = typography().labelSmall)
        Text("Body Large", style = typography().bodyLarge)
        Text("Body Medium", style = typography().bodyMedium)
        Text("Body Small", style = typography().bodySmall)
    }
}
