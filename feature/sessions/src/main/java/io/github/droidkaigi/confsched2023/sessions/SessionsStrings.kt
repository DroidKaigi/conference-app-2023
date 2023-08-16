package io.github.droidkaigi.confsched2023.sessions

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SessionsStrings : Strings<SessionsStrings>(Bindings) {
    data object Search : SessionsStrings()
    data object Timetable : SessionsStrings()
    data object Hoge : SessionsStrings()
    class Time(val hours: Int, val minutes: Int) : SessionsStrings()
    object ScheduleIcon : SessionsStrings()
    object UserIcon : SessionsStrings()
    object EventDay : SessionsStrings()
    object Category : SessionsStrings()
    object SearchResultNotFound : SessionsStrings()
    object Bookmark : SessionsStrings()
    object BookmarkFilterAllChip : SessionsStrings()
    object BookmarkedItemNotFound : SessionsStrings()
    object BookmarkedItemNotFoundSideNote : SessionsStrings()
    object Share : SessionsStrings()
    object AddToCalendar : SessionsStrings()
    object AddToFavorites : SessionsStrings()
    object RemoveFromFavorites : SessionsStrings()
    object Date : SessionsStrings()
    object Place : SessionsStrings()
    object SessionType : SessionsStrings()
    object SupportedLanguages : SessionsStrings()
    object InterpretationTarget : SessionsStrings()
    object Archive : SessionsStrings()
    object ViewDocument : SessionsStrings()
    object WatchVideo : SessionsStrings()
    object Speaker : SessionsStrings()
    object TargetAudience : SessionsStrings()
    private object Bindings : StringsBindings<SessionsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Search -> "検索"
                Timetable -> "タイムテーブル"
                Hoge -> "ホゲ"
                is Time -> "${item.hours}時${item.minutes}分"
                ScheduleIcon -> "スケジュールアイコン"
                UserIcon -> "ユーザーアイコン"
                EventDay -> "開催日"
                Category -> "カテゴリー"
                Bookmark -> "Bookmark"
                BookmarkFilterAllChip -> "全て"
                SearchResultNotFound -> "この検索条件に一致する結果はありません"
                BookmarkedItemNotFound -> "登録されたセッションがありません"
                BookmarkedItemNotFoundSideNote -> "気になるセッションをブックマークに追加して\n集めてみましょう！"
                Share -> "共有"
                AddToCalendar -> "カレンダーに追加"
                AddToFavorites -> "お気に入りに追加"
                RemoveFromFavorites -> "お気に入りから削除"
                Date -> "日付"
                Place -> "場所"
                SessionType -> "セッション種別"
                SupportedLanguages -> "対応言語"
                InterpretationTarget -> "同時通訳対象"
                Archive -> "アーカイブ"
                ViewDocument -> "資料を見る"
                TargetAudience -> "対象者"
                WatchVideo -> "動画を見る"
                Speaker -> "スピーカー"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Search -> "Search"
                Timetable -> "Timetable"
                Hoge -> bindings.defaultBinding(item, bindings)
                is Time -> "${item.hours}:${item.minutes}"
                ScheduleIcon -> "Schedule icon"
                UserIcon -> "User icon"
                EventDay -> "Day"
                Category -> "Category"
                SearchResultNotFound -> "Nothing matched your search criteria"
                Bookmark -> "Bookmark"
                BookmarkFilterAllChip -> "All"
                BookmarkedItemNotFound -> "No sessions registered"
                BookmarkedItemNotFoundSideNote -> "Add the sessions you are interested in to your bookmarks \n and collect them!"
                Share -> "Share"
                AddToCalendar -> "Add to calendar"
                AddToFavorites -> "Add to favorites"
                RemoveFromFavorites -> "Remove from favorites"
                Date -> "Date"
                Place -> "Place"
                SessionType -> "Session type"
                SupportedLanguages -> "Supported languages"
                InterpretationTarget -> "Interpretation target"
                Archive -> "Archive"
                ViewDocument -> "View Document"
                WatchVideo -> "Watch Video"
                Speaker -> "Speaker"
                TargetAudience -> "Target Audience"
            }
        },
        default = Lang.Japanese,
    )
}
