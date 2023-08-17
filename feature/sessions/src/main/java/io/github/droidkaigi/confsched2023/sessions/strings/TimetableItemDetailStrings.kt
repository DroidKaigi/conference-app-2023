package io.github.droidkaigi.confsched2023.sessions.strings

import io.github.droidkaigi.confsched2023.designsystem.strings.Lang
import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import io.github.droidkaigi.confsched2023.designsystem.strings.StringsBindings

sealed class TimetableItemDetailStrings : Strings<TimetableItemDetailStrings>(Bindings) {
    data object BookmarkedSuccessfully : TimetableItemDetailStrings()
    data object ViewBookmarkList : TimetableItemDetailStrings()

    private object Bindings : StringsBindings<TimetableItemDetailStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                BookmarkedSuccessfully -> "ブックマークに追加されました"
                ViewBookmarkList -> "一覧を見る"
            }
        },
        Lang.English to { item, _ ->
            when (item) {
                BookmarkedSuccessfully -> "Bookmarked successfully."
                ViewBookmarkList -> "View"
            }
        },
        default = Lang.English,
    )
}
