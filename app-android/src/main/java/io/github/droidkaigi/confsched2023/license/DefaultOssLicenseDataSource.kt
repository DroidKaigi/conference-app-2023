package io.github.droidkaigi.confsched2023.license

import android.content.Context
import io.github.droidkaigi.confsched2023.R
import io.github.droidkaigi.confsched2023.data.osslicense.OssLicenseDataSource
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicenseGroup
import io.github.droidkaigi.confsched2023.ui.Inject
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.BufferedSource
import okio.buffer
import okio.source

class DefaultOssLicenseDataSource @Inject constructor(
    private val context: Context,
) : OssLicenseDataSource {

    override suspend fun license(): List<OssLicenseGroup> {
        return withContext(context = Dispatchers.IO) {
            readLicenses()
                .groupByCategory()
                .map {
                    OssLicenseGroup(
                        title = it.key,
                        licenses = it.value,
                    )
                }
                .toPersistentList()
        }
    }

    private fun readLicenses(): List<License> {
        val licenseData = readLicensesFile().toRowList()
        return readLicensesMetaFile().toRowList()
            .map {
                val (position, name) = it.split(' ', limit = 2)
                val (offset, length) = position.split(':').map { it.toInt() }
                val licensesText = kotlin.runCatching {
                    licenseData.subList(offset, offset + length).joinToString()
                }.getOrNull() ?: ""
                License(
                    id = name.replace(' ', '-'),
                    name = name,
                    licensesText = licensesText,
                )
            }
            .distinctBy { it.id }
    }

    private fun List<License>.groupByCategory(): Map<String, List<License>> {
        val categoryList = listOf(
            "Android Support",
            "Android Datastore",
            "Android ",
            "Compose UI",
            "Compose Material3",
            "Compose ",
            "AndroidX lifecycle",
            "AndroidX ",
            "Kotlin",
            "Dagger",
            "Firebase",
            "Ktorfit",
            "okhttp",
            "ktor",
        )
        return groupBy { license ->
            categoryList.firstOrNull {
                license.name.startsWith(
                    prefix = it,
                    ignoreCase = true,
                )
            } ?: "etc"
        }
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

    private fun BufferedSource.toRowList(): List<String> {
        val list: MutableList<String> = mutableListOf()
        while (true) {
            val line = readUtf8Line() ?: break
            list.add(line)
        }
        return list
    }
}
