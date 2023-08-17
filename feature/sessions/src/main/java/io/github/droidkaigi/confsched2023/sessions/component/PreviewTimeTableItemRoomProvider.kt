package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import kotlinx.collections.immutable.persistentListOf

class PreviewTimeTableItemRoomProvider : PreviewParameterProvider<TimetableItem> {
    override val values: Sequence<TimetableItem>
        get() = sequenceOf(
            Session.fake(),
            Session.fake().copy(room = Session.fake().room.copy(sortIndex = 1)),
            Session.fake().copy(room = Session.fake().room.copy(sortIndex = 2)),
            Session.fake().copy(room = Session.fake().room.copy(sortIndex = 3)),
            Session.fake().copy(room = Session.fake().room.copy(sortIndex = 4)),
            Session.fake().copy(speakers = persistentListOf()),
        )
}
