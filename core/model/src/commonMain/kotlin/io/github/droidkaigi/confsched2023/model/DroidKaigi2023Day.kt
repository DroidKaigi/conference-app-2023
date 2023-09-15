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
        day = 2,
        dayOfMonth = 15,
        start = LocalDateTime
            .parse("2023-09-15T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2023-09-16T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    Day3(
        day = 3,
        dayOfMonth = 16,
        start = LocalDateTime
            .parse("2023-09-16T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2023-09-17T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
    ),
    ;

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

    public companion object {
        public fun ofOrNull(time: Instant): DroidKaigi2023Day? {
            return entries.firstOrNull {
                time in it.start..it.end
            }
        }

        /**
         * @return appropriate initial day for now
         */
        fun initialSelectedDay(isTest: Boolean = false): DroidKaigi2023Day {
            // Timetable tab set initial tab with current date.
            // To get the consistent test result, fix selected timetable tab to Day1 here.
            if (isTest) return Day1
            val reversedEntries = entries.sortedByDescending { it.day }
            var selectedDay = reversedEntries.last()
            for (entry in reversedEntries) {
                if (Clock.System.now() <= entry.end) selectedDay = entry
            }
            return selectedDay
        }
    }
}
