package io.github.droidkaigi.confsched2023.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.OssLicenseGroup
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OssLicenseViewModel @Inject constructor(
    ossLicenseRepository: OssLicenseRepository,
) : ViewModel() {

    private val licenseStateFlow: StateFlow<PersistentList<OssLicenseGroup>> =
        ossLicenseRepository.licenseData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentListOf(),
            )

    internal val uiState: StateFlow<OssLicenseScreenUiState> =
        buildUiState(licenseStateFlow) { licenses ->
            OssLicenseScreenUiState(ossLicense = licenses)
        }
}
