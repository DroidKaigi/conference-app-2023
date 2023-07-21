package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.model.fake
import io.github.droidkaigi.confsched2023.ui.KmpHiltViewModel
import io.github.droidkaigi.confsched2023.ui.KmpInject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@KmpHiltViewModel
class ContributorsViewModel @KmpInject constructor() :
    KmpViewModel() {
    // FIXME
    val sessions = MutableStateFlow(Timetable.fake())

    fun greet(): String {
        return "Hello, ${Contributors().greet()}"
    }
}