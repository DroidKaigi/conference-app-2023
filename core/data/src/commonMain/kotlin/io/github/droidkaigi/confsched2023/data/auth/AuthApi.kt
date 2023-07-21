package io.github.droidkaigi.confsched2023.data.auth

interface AuthApi {
    suspend fun authIfNeeded()
}
