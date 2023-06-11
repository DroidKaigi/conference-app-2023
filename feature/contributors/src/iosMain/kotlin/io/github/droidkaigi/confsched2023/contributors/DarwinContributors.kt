package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun viewController(): UIViewController = ComposeUIViewController {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Hello")
    }
}
