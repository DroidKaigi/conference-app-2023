package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolderImpl
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun contributorViewController(
    contributorsRepository: ContributorsRepository,
    onContributorItemClick: (url: String) -> Unit,
): UIViewController = ComposeUIViewController {
    val viewModel = ContributorsViewModel(
        contributorsRepository = contributorsRepository,
        userMessageStateHolder = UserMessageStateHolderImpl()
    )
    val uiViewController = LocalUIViewController.current
    LaunchedEffect(uiViewController) {
//        uiViewController
        // TODO: How to know the destroy event of the ViewController?
//        viewModel.viewModelScope.cancel()
    }

    KaigiTheme {
        ContributorsScreen(
            viewModel = viewModel,
            isTopAppBarHidden = true,
            onNavigationIconClick = { /** no action for iOS side **/ },
            onContributorItemClick = onContributorItemClick,
        )
    }
}
