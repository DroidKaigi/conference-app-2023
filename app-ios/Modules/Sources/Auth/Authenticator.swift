import FirebaseAuth
import shared

public class AuthenticatorImpl: Authenticator {
    public init() {}
    public func currentUser() async throws -> shared.User? {
        let currentUser = Auth.auth().currentUser
        let idToken = try await currentUser?.getIDToken()

        return idToken.map(User.init(idToken:))
    }

    public func signInAnonymously() async throws -> shared.User? {
        let result = try await Auth.auth().signInAnonymously()
        let idToken = try await result.user.getIDToken()
        return User(idToken: idToken)
    }
}
