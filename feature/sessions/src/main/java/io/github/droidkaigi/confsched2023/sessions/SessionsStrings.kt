package io.github.droidkaigi.confsched2023.sessions

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class SessionsStrings : Strings<SessionsStrings>(Bindings) {
    data object Search : SessionsStrings()
    data object Timetable : SessionsStrings()
    data object Hoge : SessionsStrings()
    class Time(val hours: Int, val minutes: Int) : SessionsStrings()
    data object ScheduleIcon : SessionsStrings()
    data object UserIcon : SessionsStrings()
    data object EventDay : SessionsStrings()
    data object Category : SessionsStrings()
    class SearchResultNotFound(val missedWord: String) : SessionsStrings()
    data object SearchPlaceHolder : SessionsStrings()
    data object Bookmark : SessionsStrings()
    data object BookmarkFilterAllChip : SessionsStrings()
    data object BookmarkedItemNotFound : SessionsStrings()
    data object BookmarkedItemNotFoundSideNote : SessionsStrings()
    data object Share : SessionsStrings()
    data object AddToCalendar : SessionsStrings()
    data object AddToFavorites : SessionsStrings()
    data object RemoveFromFavorites : SessionsStrings()
    data object Date : SessionsStrings()
    data object Place : SessionsStrings()
    data object SessionType : SessionsStrings()
    data object SupportedLanguages : SessionsStrings()
    data object InterpretationTarget : SessionsStrings()
    data object Archive : SessionsStrings()
    data object ViewDocument : SessionsStrings()
    data object WatchVideo : SessionsStrings()
    data object Speaker : SessionsStrings()
    data object TargetAudience : SessionsStrings()
    data object ReadMore : SessionsStrings()
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
                is SearchResultNotFound -> "「${item.missedWord}」と一致する検索結果がありません"
                SearchPlaceHolder -> "気になる技術を入力"
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
                ReadMore -> "続きを読む"
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
                is SearchResultNotFound -> "Nothing matched your search criteria \"${item.missedWord}\""
                SearchPlaceHolder -> "Enter some technology"
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
                ReadMore -> "Read More"
            }
        },
        default = Lang.Japanese,
    )
}
