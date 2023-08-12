package io.github.droidkaigi.confsched2023.data.sponsors.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SponsorResponse(
    val sponsorName: String,
    val sponsorLogo: String,
    val plan: String,
    val link: String,
)
