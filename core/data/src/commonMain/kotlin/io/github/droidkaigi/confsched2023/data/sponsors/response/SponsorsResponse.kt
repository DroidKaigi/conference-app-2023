package io.github.droidkaigi.confsched2023.data.sponsors.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SponsorsResponse(
    val sponsor: List<SponsorResponse>,
)
