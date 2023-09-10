import Auth
import RemoteConfig
import shared

struct Container {
    static let shared: Container = .init()

    private let entryPoint: KmpEntryPoint
    private init() {
        entryPoint = .init()
        entryPoint.doInit(
            remoteConfigApi: RemoteConfigApiImpl(),
            authenticator: AuthenticatorImpl()
        )
    }

    func get<TypeProtocol, ReturnType>(type: TypeProtocol) -> ReturnType where TypeProtocol: Protocol {
            guard let object = entryPoint.get(objCProtocol: type) as? ReturnType else {
                fatalError("Not found instance for \(type)")
            }
            return object
        }
}
