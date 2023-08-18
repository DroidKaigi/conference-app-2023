package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.droidkaigi.confsched2023.model.MultiLangText
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.model.fake

class PreviewTimetableItemDetailScreenTopAppBarTitleAndFontScaleProvider :
    PreviewParameterProvider<Pair<MultiLangText, Float>> {
    override val values: Sequence<Pair<MultiLangText, Float>> = sequenceOf(
        Session.fake().title,
        Session.fake().title.copy(jaTitle = Session.fake().title.jaTitle.repeat(5)),
    ).flatMap { title -> listOf(0.5F, 1.0F, 1.5F, 2.0F).map { title to it } }
}
