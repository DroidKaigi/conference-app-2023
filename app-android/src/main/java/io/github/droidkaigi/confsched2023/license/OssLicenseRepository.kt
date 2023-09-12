package io.github.droidkaigi.confsched2023.license

import android.content.Context
import io.github.droidkaigi.confsched2023.R
import io.github.droidkaigi.confsched2023.model.License
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okio.BufferedSource
import okio.buffer
import okio.source
import javax.inject.Inject

public interface OssLicenseRepository {
    public fun licenseMetaData(): Flow<PersistentList<License>>

    public fun licenseDetailData(): Flow<List<String>>

    public fun refresh(): Unit
}

internal class OssLicenseRepositoryImpl @Inject constructor(
    private val context: Context,
) : OssLicenseRepository {

    private val licenseMetaStateFlow =
        MutableStateFlow<PersistentList<License>>(persistentListOf())

    private val licenseDetailStateFlow = MutableStateFlow<List<String>>(emptyList())

    override fun licenseMetaData(): Flow<PersistentList<License>> {
        refresh()
        return licenseMetaStateFlow
    }

    override fun licenseDetailData(): Flow<List<String>> {
        refresh()
        return licenseDetailStateFlow
    }

    override fun refresh() {
        licenseMetaStateFlow.value = readLicensesMetaFile()
            .toRowList()
            .parseToLibraryItem()
            .toPersistentList()

        licenseDetailStateFlow.value = readLicensesFile()
            .toRowList()
    }

    private fun readLicensesMetaFile(): BufferedSource {
        return context.resources.openRawResource(R.raw.third_party_license_metadata)
            .source()
            .buffer()
    }

    private fun readLicensesFile(): BufferedSource {
        return context.resources.openRawResource(R.raw.third_party_licenses)
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
