package io.github.droidkaigi.confsched2023.contributors

import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.Timetable
import io.github.droidkaigi.confsched2023.ui.KmpHiltViewModel
import io.github.droidkaigi.confsched2023.ui.KmpInject
import io.github.droidkaigi.confsched2023.ui.KmpViewModel
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@KmpHiltViewModel
class ContributorsViewModel @KmpInject constructor(val sessionRepository: SessionsRepository) :
    KmpViewModel() {
    // FIXME
    val sessions = sessionRepository.getTimetableStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Timetable())

    fun greet(): String {
        return "Hello, ${Contributors().greet()}"
    }
}