package io.github.droidkaigi.confsched2023.data.auth
public data class User(
    val idToken: String,
)

public interface Authenticator {
    public suspend fun currentUser(): User?
    public suspend fun signInAnonymously(): User?
}
