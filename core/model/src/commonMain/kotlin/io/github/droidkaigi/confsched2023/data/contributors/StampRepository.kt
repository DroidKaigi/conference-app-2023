package io.github.droidkaigi.confsched2023.data.contributors

import kotlinx.coroutines.flow.Flow

interface StampRepository {
    fun getStampEnabledStream(): Flow<Boolean>

    fun getIsDisplayedDialogStream(): Flow<Boolean?>

    suspend fun displayedDialog()

    fun getStampDetailDescriptionStream(): Flow<String>
}
