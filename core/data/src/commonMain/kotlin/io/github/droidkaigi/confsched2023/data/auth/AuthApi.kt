package io.github.droidkaigi.confsched2023.data.auth

public interface AuthApi {
    public suspend fun authIfNeeded()
}
