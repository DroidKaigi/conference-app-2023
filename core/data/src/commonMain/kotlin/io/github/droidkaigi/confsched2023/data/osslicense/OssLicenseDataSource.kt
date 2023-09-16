package io.github.droidkaigi.confsched2023.data.osslicense

import io.github.droidkaigi.confsched2023.model.OssLicenseGroup

public interface OssLicenseDataSource {
    public suspend fun license(): List<OssLicenseGroup>
}
