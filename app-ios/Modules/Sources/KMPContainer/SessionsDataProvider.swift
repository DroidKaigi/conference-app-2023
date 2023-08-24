import Dependencies
import shared

public struct SessionsDataProvider {
    private static var sessionsRepository: SessionsRepository {
        Container.shared.get(type: SessionsRepository.self)
    }

    public let timetable: () -> AsyncThrowingStream<Timetable, Error>
    public let toggleBookmark: (TimetableItemId) async throws -> Void
}

extension SessionsDataProvider: DependencyKey {
    @MainActor
    static public var liveValue: SessionsDataProvider = SessionsDataProvider(
        timetable: {
            sessionsRepository.getTimetableStream().stream()
        },
        toggleBookmark: { @MainActor id in
            try await sessionsRepository.toggleBookmark(id: id)
        }
    )

    public static var testValue: SessionsDataProvider = SessionsDataProvider(
        timetable: {
            .init {
                Timetable.companion.fake()
            }
        },
        toggleBookmark: {_ in}
    )
}

public extension DependencyValues {
    var sessionsData: SessionsDataProvider {
        get { self[SessionsDataProvider.self] }
        set { self[SessionsDataProvider.self] = newValue }
    }
}
