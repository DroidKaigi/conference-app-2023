package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@Suppress("UNUSED")
// TODO: Pass DefaultContributorRepository from iOS
fun viewController(): UIViewController = ComposeUIViewController {
    val viewModel = ContributorsViewModel()
    val uiViewController = LocalUIViewController.current
    LaunchedEffect(uiViewController) {
//        uiViewController
        // TODO: How to know the destroy event of the ViewController?
//        viewModel.viewModelScope.cancel()
    }

    ContributorsScreen(viewModel, onNavigationIconClick = {})
}
