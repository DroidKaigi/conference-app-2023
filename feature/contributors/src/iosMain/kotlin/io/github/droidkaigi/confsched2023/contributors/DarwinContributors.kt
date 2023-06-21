package io.github.droidkaigi.confsched2023.contributors

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.droidkaigi.confsched2023.data.DefaultSessionsRepository
import io.github.droidkaigi.confsched2023.data.FakeSessionsApi
import io.github.droidkaigi.confsched2023.ui.KmpInject
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import platform.UIKit.UIViewController

@Suppress("UNUSED")
fun viewController(): UIViewController = ComposeUIViewController {
    @KmpInject lateinit var dataStore: DataStore<Preferences>
    @KmpInject lateinit var coroutineScope: CoroutineScope

    val viewModel = ContributorsViewModel(
        DefaultSessionsRepository(
            FakeSessionsApi(),
            dataStore,
            coroutineScope
        )
    )
    val uiViewController = LocalUIViewController.current
    LaunchedEffect(uiViewController) {
        uiViewController
        // TODO: How to know the destroy event of the ViewController?
        viewModel.viewModelScope.cancel()
    }

    ContributorsScreen(viewModel)
}
