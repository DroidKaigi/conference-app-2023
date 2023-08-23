import FirebaseRemoteConfig
import shared

public class RemoteConfigApiImpl: RemoteConfigApi {
    public init() {}
    public func getBoolean(key: String) async throws -> KotlinBoolean {
        .init(
            bool: RemoteConfig.remoteConfig().configValue(forKey: key).boolValue
        )
    }
}
