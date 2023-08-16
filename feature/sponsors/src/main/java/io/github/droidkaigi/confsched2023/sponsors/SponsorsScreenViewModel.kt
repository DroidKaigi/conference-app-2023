package io.github.droidkaigi.confsched2023.sponsors

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.model.Plan
import io.github.droidkaigi.confsched2023.model.Plan.GOLD
import io.github.droidkaigi.confsched2023.model.Plan.PLATINUM
import io.github.droidkaigi.confsched2023.model.Plan.SUPPORTER
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import io.github.droidkaigi.confsched2023.sponsors.section.SponsorListUiState
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import io.github.droidkaigi.confsched2023.ui.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SponsorsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
    sponsorsRepository: SponsorsRepository,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val sponsorsStateFlow = sponsorsRepository.sponsors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = persistentListOf(),
        )

    val uiState: StateFlow<SponsorsScreenUiState> = buildUiState(sponsorsStateFlow) { sponsors ->

        val sponsorListUiStates = Plan.values().associateWith { plan ->
            SponsorListUiState(
                title = when(plan){
                    PLATINUM -> SponsorsStrings.PlatinumSponsors
                    GOLD ->  SponsorsStrings.GoldSponsors
                    SUPPORTER -> SponsorsStrings.Supporters
                },
                gridSize = when(plan){
                    PLATINUM -> 1
                    GOLD ->  2
                    SUPPORTER -> 3
                },
                sponsorList = sponsors.filter { it.plan == plan }.toPersistentList()
            )
        }.toPersistentMap()

        SponsorsScreenUiState(
            sponsorListUiStates = sponsorListUiStates
        )
    }
}
