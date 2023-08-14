package io.github.droidkaigi.confsched2023.data.remoteconfig

import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart

class DefaultStampRepository(
    private val remoteConfigApi: RemoteConfigApi,
) : StampRepository {
    private val isStampsEnabledStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private suspend fun fetchStampsEnabled() {
        isStampsEnabledStateFlow.value = remoteConfigApi.getBoolean(IS_STAMPS_ENABLED_KEY)
    }

    override fun getStampEnabledStream(): Flow<Boolean> {
        return isStampsEnabledStateFlow.onStart { fetchStampsEnabled() }
    }

    companion object {
        const val IS_STAMPS_ENABLED_KEY = "is_stamps_enable"
    }
}
