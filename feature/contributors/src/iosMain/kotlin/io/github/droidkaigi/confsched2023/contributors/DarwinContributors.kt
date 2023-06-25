package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import io.github.droidkaigi.confsched2023.data.createDataStore
import io.github.droidkaigi.confsched2023.data.session.DefaultSessionsRepository
import io.github.droidkaigi.confsched2023.data.session.FakeSessionsApi
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import kotlinx.coroutines.MainScope
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun viewController(): UIViewController = ComposeUIViewController {
    val viewModel = ContributorsViewModel(
        DefaultSessionsRepository(
            FakeSessionsApi(),
            UserDataStore(
                createDataStore(MainScope(), { "user_preference.preferences_pb" })
            )
        )
    )
    val uiViewController = LocalUIViewController.current
    LaunchedEffect(uiViewController) {
//        uiViewController
        // TODO: How to know the destroy event of the ViewController?
//        viewModel.viewModelScope.cancel()
    }

    ContributorsScreen(viewModel)
}
