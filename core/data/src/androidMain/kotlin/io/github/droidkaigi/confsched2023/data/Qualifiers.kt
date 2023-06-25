package io.github.droidkaigi.confsched2023.data

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoCoroutineScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultCoroutineScope
