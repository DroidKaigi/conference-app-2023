import FirebaseRemoteConfig
import shared

public class RemoteConfigApiImpl: RemoteConfigApi {
    public init() {}
    public func getBoolean(key: String) async throws -> KotlinBoolean {
        .init(
            bool: RemoteConfig.remoteConfig().configValue(forKey: key).boolValue
        )
    }
    public func getString(key: String) async throws -> KotlinString {
        .init(
            string: RemoteConfig.remoteConfig().configValue(forKey: key)
        )
    }
}
