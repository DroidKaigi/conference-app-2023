package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

public data class Staff(
    val id: Long,
    val username: String,
    val profileUrl: String,
    val iconUrl: String,
) {
    companion object
}

// create fakes
fun Staff.Companion.fakes(): PersistentList<Staff> {
    return (1..20).map {
        Staff(
            id = it.toLong(),
            username = "username $it",
            profileUrl = "https://developer.android.com/",
            iconUrl = "https://placehold.jp/150x150.png",
        )
    }.toPersistentList()
}
