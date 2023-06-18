package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import io.github.droidkaigi.confsched2023.data.DefaultSessionsRepository
import io.github.droidkaigi.confsched2023.data.FakeSessionsApi
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun viewController(): UIViewController = ComposeUIViewController {
    val viewModel = ContributorsViewModel(DefaultSessionsRepository(FakeSessionsApi()))
    val uiViewController = LocalUIViewController.current
    LaunchedEffect(uiViewController) {
//        uiViewController
        // TODO: How to know the destroy event of the ViewController?
//        viewModel.viewModelScope.cancel()
    }

    ContributorsScreen(viewModel)
}
