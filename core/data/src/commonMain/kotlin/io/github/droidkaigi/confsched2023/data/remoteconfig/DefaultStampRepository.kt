package io.github.droidkaigi.confsched2023.data.remoteconfig

import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.data.stamps.StampDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultStampRepository(
    private val remoteConfigApi: RemoteConfigApi,
    private val stampDataStore: StampDataStore,
) : StampRepository {
    private val isStampsEnabledStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val stampDetailDescriptionStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    private suspend fun fetchStampsEnabled() {
        isStampsEnabledStateFlow.value = remoteConfigApi.getBoolean(IS_STAMPS_ENABLED_KEY)
    }

    private suspend fun fetchStampDetailDescription() {
        stampDetailDescriptionStateFlow.value =
            remoteConfigApi.getString(STAMP_DETAIL_DESCRIPTION_KEY)
    }

    override fun getStampEnabledStream(): Flow<Boolean> {
        return isStampsEnabledStateFlow.onStart { fetchStampsEnabled() }
    }

    override fun getIsDisplayedDialogStream(): Flow<Boolean> {
        return stampDataStore.isDisplayedStream()
    }

    override suspend fun displayedDialog() {
        stampDataStore.save(true)
    }

    override fun getStampDetailDescriptionStream(): Flow<String> {
        return stampDetailDescriptionStateFlow.onStart { fetchStampDetailDescription() }
    }

    companion object {
        const val IS_STAMPS_ENABLED_KEY = "is_stamps_enable"
        const val STAMP_DETAIL_DESCRIPTION_KEY = "achievements_detail_description"
    }
}
