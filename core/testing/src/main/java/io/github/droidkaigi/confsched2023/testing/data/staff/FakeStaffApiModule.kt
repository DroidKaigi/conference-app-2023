package io.github.droidkaigi.confsched2023.testing.data.staff

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.staff.FakeStaffApiClient
import io.github.droidkaigi.confsched2023.data.staff.StaffApiClient
import io.github.droidkaigi.confsched2023.data.staff.StaffApiModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StaffApiModule::class],
)
class FakeStaffApiModule {
    @Provides
    @Singleton
    fun provideStaffApi(): StaffApiClient {
        return FakeStaffApiClient()
    }
}
