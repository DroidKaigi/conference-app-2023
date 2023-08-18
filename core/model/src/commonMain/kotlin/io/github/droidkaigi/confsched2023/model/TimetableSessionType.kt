package io.github.droidkaigi.confsched2023.model

enum class TimetableSessionType(
    val key: String,
    val label: MultiLangText,
) {
    WELCOME_TALK(
        key = "WELCOME_TALK",
        label = MultiLangText(
            jaTitle = "ウェルカムトーク",
            enTitle = "Welcome Talk",
        ),
    ),
    NORMAL(
        key = "NORMAL",
        label = MultiLangText(
            jaTitle = "セッション",
            enTitle = "Session",
        ),
    ),
    LUNCH(
        key = "LUNCH",
        label = MultiLangText(
            jaTitle = "ランチ",
            enTitle = "Lunch",
        ),
    ),
    AFTER_PARTY(
        key = "AFTER_PARTY",
        label = MultiLangText(
            jaTitle = "アフターパーティー",
            enTitle = "After Party",
        ),
    ),
    ;

    companion object {
        fun ofOrNull(key: String): TimetableSessionType? {
            return values().firstOrNull {
                it.key == key
            }
        }
    }
}
