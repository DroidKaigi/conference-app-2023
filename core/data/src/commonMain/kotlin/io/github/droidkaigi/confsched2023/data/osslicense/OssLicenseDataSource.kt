package io.github.droidkaigi.confsched2023.data.osslicense

import io.github.droidkaigi.confsched2023.model.OssLicense

interface OssLicenseDataSource {
    suspend fun licenseFlow(): OssLicense
}
