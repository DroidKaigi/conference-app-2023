package io.github.droidkaigi.confsched2023.data.osslicense

import io.github.droidkaigi.confsched2023.model.OssLicenseGroup

interface OssLicenseDataSource {
    suspend fun license(): List<OssLicenseGroup>
}
