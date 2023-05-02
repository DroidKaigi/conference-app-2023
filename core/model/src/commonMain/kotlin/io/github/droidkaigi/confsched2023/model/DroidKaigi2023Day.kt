package io.github.droidkaigi.confsched2023.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

public enum class DroidKaigi2023Day(
    public val start: Instant,
    public val end: Instant
) {
    Day1(
        start = LocalDateTime
            .parse("2022-10-05T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-06T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    ),
    Day2(
        start = LocalDateTime
            .parse("2022-10-06T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-07T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    ),
    Day3(
        start = LocalDateTime
            .parse("2022-10-07T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-08T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    );

    public val kaigiPlace: KaigiPlace
        get() = if (this != Day3) {
            KaigiPlace.Prism
        } else {
            KaigiPlace.Bellesalle
        }

    public companion object {
        public fun ofOrNull(time: Instant): DroidKaigi2023Day? {
            return values().firstOrNull {
                time in it.start..it.end
            }
        }

        public fun defaultDyamicThemeDate(): Boolean {
            return Day1.start < Clock.System.now()
        }
    }
}

public enum class KaigiPlace {
    Prism,
    Bellesalle
}
