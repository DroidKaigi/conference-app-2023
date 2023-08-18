package io.github.droidkaigi.confsched2023.data

import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.model.AppError
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.util.cio.ChannelReadException
import kotlinx.coroutines.TimeoutCancellationException

public class NetworkService(public val httpClient: HttpClient, public val authApi: AuthApi) {

    public suspend inline operator fun <reified T : Any> invoke(
        block: () -> T,
    ): T = try {
        authApi.authIfNeeded()
        block()
    } catch (e: Throwable) {
        throw e.toAppError()
    }
}

public fun Throwable.toAppError(): AppError {
    return when (this) {
        is AppError -> this
        is ResponseException ->
            return AppError.ApiException.ServerException(this)
        is ChannelReadException ->
            return AppError.ApiException.NetworkException(this)
        is TimeoutCancellationException,
        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        -> {
            AppError.ApiException
                .TimeoutException(this)
        }
        else -> AppError.UnknownException(this)
    }
}
