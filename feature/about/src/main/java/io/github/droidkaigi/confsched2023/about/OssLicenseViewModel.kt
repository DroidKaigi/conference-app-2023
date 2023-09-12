package io.github.droidkaigi.confsched2023.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicense
import io.github.droidkaigi.confsched2023.model.OssLicenseGroup
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OssLicenseViewModel @Inject constructor(
    private val ossLicenseRepository: OssLicenseRepository,
) : ViewModel() {

    private val licenseStateFlow: StateFlow<PersistentList<License>> =
        ossLicenseRepository.licenseMetaData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = persistentListOf(),
            )

    internal val uiState: StateFlow<OssLicenseScreenUiState> =
        buildUiState(licenseStateFlow) { metadata ->
            val groupList = metadata.distinctBy { it.name }.groupByCategory()
                .map {
                    OssLicenseGroup(
                        title = it.key,
                        licenses = it.value,
                    )
                }.toPersistentList()
            OssLicenseScreenUiState(ossLicense = OssLicense(groupList))
        }

    private fun List<License>.groupByCategory(): Map<String, List<License>> {
        val categoryList = listOf(
            "Android Support",
            "Android Datastore",
            "Android ",
            "Compose UI",
            "Compose Material3",
            "Compose ",
            "kotlinx",
            "Kotlin",
            "ktor",
            "AndroidX lifecycle",
            "AndroidX ",
        )
        return groupBy { license ->
            categoryList.firstOrNull {
                license.name.startsWith(it)
            } ?: "etc"
        }
    }

    fun loadLicenseList() {
        ossLicenseRepository.refresh()
    }
}
