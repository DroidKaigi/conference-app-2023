package io.github.droidkaigi.confsched2023.testing.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun runTestWithLogging(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = 30.seconds,
    testBody: suspend TestScope.() -> Unit,
) = runTest(context, timeout) {
    runCatching {
        coroutineScope {
            testBody()
        }
    }.let {
        if (it.isFailure) {
            it.exceptionOrNull()?.let { exception ->
                exception.printStackTrace()
                throw exception
            }
        }
    }
}
