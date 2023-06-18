package io.github.droidkaigi.confsched2023.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

/**
 * SnackbarMessageEffect shows a snackbar message when a [UserMessage] is emitted by [userMessageStateHolder].
 */
@Composable
fun SnackbarMessageEffect(
    snackbarHostState: SnackbarHostState,
    userMessageStateHolder: UserMessageStateHolder,
) {
    userMessageStateHolder.messageUiState.userMessages.firstOrNull()?.let { userMessage ->
        LaunchedEffect(userMessage) {
            val snackbarResult: SnackbarResult = snackbarHostState.showSnackbar(
                message = userMessage.message,
                actionLabel = userMessage.actionLabel,
            )
            userMessageStateHolder.messageShown(
                messageId = userMessage.id,
                userMessageResult = when (snackbarResult) {
                    Dismissed -> UserMessageResult.Dismissed
                    ActionPerformed -> UserMessageResult.ActionPerformed
                },
            )
        }
    }
}

data class UserMessage(
    val message: String,
    val actionLabel: String? = null,
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val userMessageResult: UserMessageResult? = null,
)

data class MessageUiState(
    val userMessages: List<UserMessage> = emptyList(),
)
class UserMessageStateHolderImpl : UserMessageStateHolder {
    private var _messageUiState by mutableStateOf(MessageUiState())
    override val messageUiState get() = _messageUiState
    override fun messageShown(messageId: Long, userMessageResult: UserMessageResult) {
        val messages = _messageUiState.userMessages.toMutableList()
        messages.indexOfFirst { it.id == messageId }.let { userMessageIndex ->
            if (userMessageIndex == -1) return@let
            messages.set(
                userMessageIndex,
                messages[userMessageIndex].copy(userMessageResult = userMessageResult),
            )
        }
        _messageUiState = _messageUiState.copy(userMessages = messages)
    }

    override suspend fun showMessage(message: String, actionLabel: String?): UserMessageResult {
        val messages = _messageUiState.userMessages.toMutableList()
        val newMessage = UserMessage(message, actionLabel = actionLabel)
        messages.add(newMessage)
        _messageUiState = _messageUiState.copy(userMessages = messages)
        val messageResult = snapshotFlow {
            _messageUiState
        }.filter { messageState ->
            messageState.userMessages.find { it.id == newMessage.id }?.let { userMessage ->
                val messageResult = userMessage.userMessageResult
                messageResult != null
            } ?: false
        }
            .map { messageState ->
                val userMessage =
                    checkNotNull(messageState.userMessages.find { it.id == newMessage.id })
                checkNotNull(
                    userMessage
                        .userMessageResult,
                )
            }
            .first()
        val newMessages = _messageUiState.userMessages.toMutableList()
        newMessages.find { it.id == newMessage.id }?.let { userMessage ->
            newMessages.remove(userMessage)
        }
        _messageUiState = _messageUiState.copy(userMessages = newMessages)
        return messageResult
    }
}

interface UserMessageStateHolder {
    val messageUiState: MessageUiState
    fun messageShown(messageId: Long, userMessageResult: UserMessageResult)
    suspend fun showMessage(message: String, actionLabel: String? = null): UserMessageResult
}

enum class UserMessageResult {
    Dismissed,
    ActionPerformed,
}
