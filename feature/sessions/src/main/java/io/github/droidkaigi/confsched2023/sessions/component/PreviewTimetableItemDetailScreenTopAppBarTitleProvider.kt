package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.droidkaigi.confsched2023.model.MultiLangText
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake

class PreviewTimetableItemDetailScreenTopAppBarTitleProvider : PreviewParameterProvider<MultiLangText> {
    override val values: Sequence<MultiLangText> = sequenceOf(
        Session.fake().title,
        Session.fake().title.copy(jaTitle = Session.fake().title.jaTitle.repeat(5)),
    )
}
