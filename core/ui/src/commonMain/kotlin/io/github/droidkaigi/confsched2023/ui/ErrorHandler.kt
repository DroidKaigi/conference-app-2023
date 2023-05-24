package io.github.droidkaigi.confsched2023.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry

fun <T> Flow<T>.handleErrorAndRetry(
    actionLabel: String,
    userMessageStateHolder: UserMessageStateHolder,
) = retry { throwable ->
    val messageResult = userMessageStateHolder.showMessage(
        message = throwable.toApplicationErrorMessage(),
        actionLabel = actionLabel,
    )

    val retryPerformed = messageResult == UserMessageResult.ActionPerformed

    retryPerformed
}.catch { /* Do nothing if the user dose not retry. */ }

fun <T> Flow<T>.handleErrorAndRetryAction(
    actionLabel: String,
    userMessageStateHolder: UserMessageStateHolder,
    retryAction: suspend ((Throwable) -> Unit),
) = catch { throwable ->
    val messageResult = userMessageStateHolder.showMessage(
        message = throwable.toApplicationErrorMessage(),
        actionLabel = actionLabel,
    )

    if (messageResult == UserMessageResult.ActionPerformed) {
        retryAction(throwable)
    }
}.catch { /* Do nothing if the user dose not retry. */ }
