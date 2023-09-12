import Dependencies

public enum EventKitClientKey: DependencyKey {
    public static var liveValue: EventKitClientProtocol = EventKitClient()
}

public extension DependencyValues {
   var eventKitClient: EventKitClientProtocol {
       get { self[EventKitClientKey.self] }
       set { self[EventKitClientKey.self] = newValue }
   }
}
