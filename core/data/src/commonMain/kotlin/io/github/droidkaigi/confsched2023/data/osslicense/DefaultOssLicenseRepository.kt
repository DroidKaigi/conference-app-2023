package io.github.droidkaigi.confsched2023.data.osslicense

import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import io.github.droidkaigi.confsched2023.ui.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultOssLicenseRepository @Inject constructor(
    private val ossLicenseDataSource: OssLicenseDataSource,
) : OssLicenseRepository {

    private val contributorsStateFlow =
        MutableStateFlow<PersistentList<License>>(persistentListOf())

    override fun licenseData(): Flow<PersistentList<License>> {
        return contributorsStateFlow.onStart {
            if (contributorsStateFlow.value.isEmpty()) {
                ossLicenseDataSource.licenseFlow()
            }
        }
    }
}
