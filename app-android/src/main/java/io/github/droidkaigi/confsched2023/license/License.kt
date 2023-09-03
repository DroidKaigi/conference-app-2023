package io.github.droidkaigi.confsched2023.license

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class License(
    val name: String,
    val ossSet: Int,
    val length: Int,
    val detail: String = "",
)

data class OssLicenseGroup(
    val title: String,
    val licenses: List<License>,
    val expand: Boolean = false,
)

data class OssLicense(
    val groupList: PersistentList<OssLicenseGroup> = persistentListOf(),
)
