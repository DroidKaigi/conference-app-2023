package io.github.droidkaigi.confsched2023.license

import android.content.Context
import io.github.droidkaigi.confsched2023.R.raw
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okio.BufferedSource
import okio.buffer
import okio.source
import javax.inject.Inject

class OssLicenseRepositoryImpl @Inject constructor(
    private val context: Context,
) : OssLicenseRepository {

    private val licenseMetaStateFlow =
        MutableStateFlow<PersistentList<License>>(persistentListOf())

    private val licenseDetailStateFlow = MutableStateFlow<List<String>>(emptyList())

    override fun licenseMetaData(): Flow<PersistentList<License>> {
        licenseMetaStateFlow.value = readLicensesMetaFile()
            .toRowList()
            .parseToLibraryItem()
            .toPersistentList()
        return licenseMetaStateFlow
    }

    override fun licenseDetailData(): Flow<List<String>> {
        licenseDetailStateFlow.value = readLicensesFile()
            .toRowList()
        return licenseDetailStateFlow
    }

    private fun readLicensesMetaFile(): BufferedSource {
        return context.resources.openRawResource(raw.third_party_license_metadata)
            .source()
            .buffer()
    }

    private fun readLicensesFile(): BufferedSource {
        return context.resources.openRawResource(raw.third_party_licenses)
            .source()
            .buffer()
    }

    private fun List<String>.parseToLibraryItem(): List<License> {
        return map {
            val (position, name) = it.split(' ', limit = 2)
            val (offset, length) = position.split(':').map { it.toInt() }
            License(name, offset, length)
        }
    }

    private fun BufferedSource.toRowList(): List<String> {
        val list: MutableList<String> = mutableListOf()
        while (true) {
            val line = readUtf8Line() ?: break
            list.add(line)
        }
        return list
    }
}
