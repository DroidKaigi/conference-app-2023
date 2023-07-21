package io.github.droidkaigi.confsched2023.testing.data.auth

import io.github.droidkaigi.confsched2023.data.auth.AuthApi

class FakeAuthApi : AuthApi {
    override suspend fun authIfNeeded() {
        // Do nothing
    }
}
