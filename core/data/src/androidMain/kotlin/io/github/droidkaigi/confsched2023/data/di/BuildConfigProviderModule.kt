package io.github.droidkaigi.confsched2023.data.di

import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import java.util.Optional
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
public annotation class AppAndroidBuildConfig

@Module
@InstallIn(SingletonComponent::class)
public class BuildConfigProviderModule {
    @Provides
    @Singleton
    public fun provideBuildConfigProvider(
        @AppAndroidBuildConfig buildConfigOverride: Optional<BuildConfigProvider>,
    ): BuildConfigProvider = if (buildConfigOverride.isPresent) {
        buildConfigOverride.get()
    } else {
        EmptyBuildConfigProvider
    }
}

@InstallIn(SingletonComponent::class)
@Module
public abstract class AppAndroidBuildConfigModule {
    @BindsOptionalOf
    @AppAndroidBuildConfig
    public abstract fun bindBuildConfigProvider(): BuildConfigProvider
}

private object EmptyBuildConfigProvider : BuildConfigProvider {
    override val versionName: String = ""
}
