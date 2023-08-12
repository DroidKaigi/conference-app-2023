package io.github.droidkaigi.confsched2023.main

import androidx.lifecycle.ViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {
    private val _uiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState(),
    )
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        fetchAndApplyRemoteConfig()
    }

    private fun fetchAndApplyRemoteConfig() {
        val remoteConfig = firebaseRemoteConfig
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            _uiState.value = uiState.value.copy(
                isEnableStamps = remoteConfig.getBoolean("is_enable_stamps"),
            )
        }
    }
}
