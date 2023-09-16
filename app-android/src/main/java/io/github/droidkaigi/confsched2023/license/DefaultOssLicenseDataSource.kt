package io.github.droidkaigi.confsched2023.license

import android.content.Context
import io.github.droidkaigi.confsched2023.R
import io.github.droidkaigi.confsched2023.data.osslicense.OssLicenseDataSource
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicense
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

    override suspend fun licenseFlow(): OssLicense {
        return withContext(context = Dispatchers.IO) {
            val details = readLicensesFile().toRowList()
            val metadata = readLicensesMetaFile().toRowList().parseToLibraryItem(
                details = details,
            )
            val groupList = metadata.distinctBy { it.name }.groupByCategory()
                .map {
                    OssLicenseGroup(
                        title = it.key,
                        licenses = it.value,
                    )
                }.toPersistentList()
            OssLicense(groupList)
        }
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

    private fun List<String>.parseToLibraryItem(details: List<String>): List<License> {
        return mapIndexed { index, value ->
            val (position, name) = value.split(' ', limit = 2)
            val (offset, length) = position.split(':').map { it.toInt() }
            val id = name.replace(' ', '-')
            License(
                name = name,
                id = id,
                offset = offset,
                length = length,
                detail = details[index],
            )
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
