package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

public data class Contributor(
    val id: Int,
    val username: String,
    val profileUrl: String?,
    val iconUrl: String,
) {
    public companion object
}

public fun Contributor.Companion.fakes(): PersistentList<Contributor> = (0..20)
    .map {
        Contributor(
            id = it,
            username = it.toString(),
            profileUrl = "https://developer.android.com/",
            iconUrl = "https://placehold.jp/150x150.png",
        )
    }
    .toPersistentList()