package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

public interface OssLicenseRepository {
    public fun licenseMetaData(): Flow<PersistentList<License>>

    public fun licenseDetailData(): Flow<List<String>>
}
