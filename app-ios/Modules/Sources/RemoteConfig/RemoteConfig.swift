import FirebaseRemoteConfig
import shared

public class RemoteConfigApiImpl: RemoteConfigApi {
    private let remoteConfig = RemoteConfig.remoteConfig()

    public init() {
        remoteConfig.addOnConfigUpdateListener { [weak remoteConfig] configUpdate, error in
            guard error == nil else {
                return
            }

            remoteConfig?.activate()
        }
    }

    public func getBoolean(key: String) async throws -> KotlinBoolean {
        .init(
            bool: remoteConfig.configValue(forKey: key).boolValue
        )
    }
    public func getString(key: String) async throws -> String {
        return remoteConfig.configValue(forKey: key).stringValue ?? ""
    }
}
