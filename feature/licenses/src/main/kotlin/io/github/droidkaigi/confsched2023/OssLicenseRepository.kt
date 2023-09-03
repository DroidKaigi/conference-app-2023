package io.github.droidkaigi.confsched2023

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

public interface OssLicenseRepository {
    public fun sponsors(): Flow<PersistentList<OssLicense>>

    public fun refresh(): Unit
}

internal class OssLicenseRepositoryImpl @Inject constructor() : OssLicenseRepository {

    private val ossLicenseStateFlow = MutableStateFlow<PersistentList<OssLicense>>(persistentListOf())
    override fun sponsors(): Flow<PersistentList<OssLicense>> {
        return ossLicenseStateFlow
    }

    override fun refresh() {
        // TODO implement later
    }
}
