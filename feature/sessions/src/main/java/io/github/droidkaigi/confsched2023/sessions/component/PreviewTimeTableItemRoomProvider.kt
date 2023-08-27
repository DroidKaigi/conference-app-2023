package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.droidkaigi.confsched2023.model.RoomType.RoomA
import io.github.droidkaigi.confsched2023.model.RoomType.RoomB
import io.github.droidkaigi.confsched2023.model.RoomType.RoomC
import io.github.droidkaigi.confsched2023.model.RoomType.RoomD
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake
import kotlinx.collections.immutable.persistentListOf

class PreviewTimeTableItemRoomProvider : PreviewParameterProvider<TimetableItem> {
    override val values: Sequence<TimetableItem>
        get() = sequenceOf(
            Session.fake(),
            Session.fake().copy(room = Session.fake().room.copy(type = RoomC)),
            Session.fake().copy(room = Session.fake().room.copy(type = RoomA)),
            Session.fake().copy(room = Session.fake().room.copy(type = RoomB)),
            Session.fake().copy(room = Session.fake().room.copy(type = RoomD)),
            Session.fake().copy(speakers = persistentListOf(Session.fake().speakers.first())),
            Session.fake().copy(speakers = persistentListOf()),
        )
}
