package io.github.droidkaigi.confsched2023.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

public enum class DroidKaigi2023Day(
    public val day: Int,
    public val dayOfMonth: Int,
    public val start: Instant,
    public val end: Instant,
) {
    Day1(
        day = 1,
        dayOfMonth = 14,
        start = LocalDateTime
            .parse("2023-09-14T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2023-09-15T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    Day2(
        day = 1,
        dayOfMonth = 15,
        start = LocalDateTime
            .parse("2023-09-15T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2023-09-16T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    Day3(
        day = 1,
        dayOfMonth = 16,
        start = LocalDateTime
            .parse("2023-09-16T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2023-09-17T00:00:00")
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
