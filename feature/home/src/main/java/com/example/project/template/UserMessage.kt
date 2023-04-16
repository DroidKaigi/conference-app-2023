package com.example.project.template

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.SnackbarResult.Dismissed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.example.project.template.UserMessageStateHolder.UserMessageResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.UUID
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * SnackbarMessageEffect shows a snackbar message when a [UserMessage] is emitted by [userMessageStateHolder].
 */
@Composable
fun SnackbarMessageEffect(
    snackbarHostState: SnackbarHostState,
    userMessageStateHolder: UserMessageStateHolder
) {
    userMessageStateHolder.messageUiState.userMessages.firstOrNull()?.let { userMessage ->
        LaunchedEffect(userMessage) {
            val snackbarResult: SnackbarResult = snackbarHostState.showSnackbar(
                message = userMessage.message,
                actionLabel = userMessage.actionLabel
            )
            userMessageStateHolder.messageShown(
                messageId = userMessage.id,
                userMessageResult = when (snackbarResult) {
                    Dismissed -> UserMessageResult.Dismissed
                    ActionPerformed -> UserMessageResult.ActionPerformed
                }
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
    val userMessages: List<UserMessage> = emptyList()
)

@Module
@InstallIn(ViewModelComponent::class)
class MessageStateHolderModule {
    @Provides
    fun provideMessageStateHolder(): UserMessageStateHolder {
        return UserMessageStateHolderImpl()
    }
}

class UserMessageStateHolderImpl : UserMessageStateHolder {
    private var _messageUiState by mutableStateOf(MessageUiState())
    override val messageUiState get() = _messageUiState
    override fun messageShown(messageId: Long, userMessageResult: UserMessageResult) {
        val messages = _messageUiState.userMessages.toMutableList()
        messages.indexOfFirst { it.id == messageId }.let { userMessageIndex ->
            messages.set(
                userMessageIndex,
                messages[userMessageIndex].copy(userMessageResult = userMessageResult)
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
                        .userMessageResult
                )
            }
            .first()
        messages.remove(newMessage)
        return messageResult
    }
}

interface UserMessageStateHolder {
    enum class UserMessageResult {
        Dismissed,
        ActionPerformed,
    }

    val messageUiState: MessageUiState
    fun messageShown(messageId: Long, userMessageResult: UserMessageResult)
    suspend fun showMessage(message: String, actionLabel: String? = null): UserMessageResult
}
