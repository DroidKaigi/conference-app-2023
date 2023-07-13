package io.github.droidkaigi.confsched2023.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

public enum class DroidKaigi2023Day(
    public val start: Instant,
    public val end: Instant,
) {
    Day1(
        start = LocalDateTime
            .parse("2022-10-05T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-06T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    Day2(
        start = LocalDateTime
            .parse("2022-10-06T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-07T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    Day3(
        start = LocalDateTime
            .parse("2022-10-07T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2022-10-08T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    ;

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

    fun getDropDownText(language: String): String {
        val japanese = "ja"

        val date = this.start.toLocalDateTime(TimeZone.currentSystemDefault())

        val year = if (language == japanese) {
            "${date.year}年"
        } else {
            "${date.year}"
        }

        val month = if (language == japanese) {
            "${date.monthNumber}月"
        } else {
            date.month.name.lowercase().replaceFirstChar { it.uppercase() }
        }

        val day = if (language == japanese) {
            "${date.dayOfMonth}日"
        } else {
            "${date.dayOfMonth}th"
        }

        return "${this.name} ($year $month $day)"
    }
}
