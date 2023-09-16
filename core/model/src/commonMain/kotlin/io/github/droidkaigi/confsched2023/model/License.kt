package io.github.droidkaigi.confsched2023.model

data class License(
    val id: String,
    val name: String,
    val licensesText: String,
)

data class OssLicenseGroup(
    val title: String,
    val licenses: List<License>,
)
