package io.github.droidkaigi.confsched2023.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

public data class Sponsor(
    val name: String,
    val logo: String,
    val plan: Plan,
    val link: String,
) {
    public companion object
}

public enum class Plan {
    PLATINUM,
    GOLD,
    SUPPORTER,
    ;

    public companion object {
        public fun ofOrNull(plan: String): Plan? {
            return entries.firstOrNull { it.name == plan }
        }
    }

    public val isSupporter: Boolean
        get() = this == SUPPORTER
    public val isPlatinum: Boolean
        get() = this == PLATINUM
    public val isGold: Boolean
        get() = this == GOLD
}

public fun Sponsor.Companion.fakes(): PersistentList<Sponsor> = (
    List(3) {
        Sponsor(
            name = "DroidKaigi PLATINUM Section $it",
            logo = "https://placehold.jp/150x150.png",
            plan = Plan.PLATINUM,
            link = "https://developer.android.com/",
        )
    } + List(5) {
        Sponsor(
            name = "DroidKaigi GOLD Section $it",
            logo = "https://placehold.jp/150x150.png",
            plan = Plan.GOLD,
            link = "https://developer.android.com/",
        )
    } + List(12) {
        Sponsor(
            name = "DroidKaigi Supporter Section $it",
            logo = "https://placehold.jp/150x150.png",
            plan = Plan.SUPPORTER,
            link = "https://developer.android.com/",
        )
    }
    ).toPersistentList()
