package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import io.github.droidkaigi.confsched2023.data.contributors.DefaultContributorsRepository
import io.github.droidkaigi.confsched2023.data.contributors.FakeContributorsApiClient
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolderImpl
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun contributorViewController(
    contributorsRepository: ContributorsRepository,
    onNavigationIconClick: () -> Unit,
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

    ContributorsScreen(
        viewModel = viewModel,
        onNavigationIconClick = onNavigationIconClick,
        onContributorItemClick = onContributorItemClick,
    )
}
