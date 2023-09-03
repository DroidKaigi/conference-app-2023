package io.github.droidkaigi.confsched2023.license

import android.content.Context
import com.google.android.gms.oss.licenses.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okio.buffer
import okio.source
import javax.inject.Inject

public interface OssLicenseRepository {
    public fun sponsors(): Flow<PersistentList<OssLicense>>

    public fun refresh(): Unit
}

internal class OssLicenseRepositoryImpl @Inject constructor(
    private val context:Context
) : OssLicenseRepository {

    private val ossLicenseStateFlow = MutableStateFlow<PersistentList<OssLicense>>(persistentListOf())
    override fun sponsors(): Flow<PersistentList<OssLicense>> {
        refresh()
        return ossLicenseStateFlow
    }

    override fun refresh() {
        ossLicenseStateFlow.value = context.resources.openRawResource(R.raw.keep_third_party_licenses)
            .source()
            .buffer()
            .use {
                val libraries = mutableListOf<OssLicense>()
                while (true) {
                    val line = it.readUtf8Line() ?: break
                    val (position, name) = line.split(' ', limit = 2)
                    val (offset, length) = position.split(':').map { it.toInt() }
                    libraries.add(OssLicense(name, offset, length))
                }
                libraries.toList()
            }.toPersistentList()
    }
}
