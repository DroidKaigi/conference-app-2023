package io.github.droidkaigi.confsched2023.ui

import io.github.droidkaigi.confsched2023.designsystem.strings.Strings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry

fun <T> Flow<T>.handleErrorAndRetry(
    actionLabel: Strings<*>,
    userMessageStateHolder: UserMessageStateHolder,
) = retry { throwable ->
    val messageResult = userMessageStateHolder.showMessage(
        message = throwable.toApplicationErrorMessage(),
        actionLabel = actionLabel.asString(),
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
