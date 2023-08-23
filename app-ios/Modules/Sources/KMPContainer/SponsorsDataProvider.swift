import Dependencies
import shared

public struct SponsorsDataProvider {
    private static var sponsorsRepository: SponsorsRepository {
        Container.shared.get(type: SponsorsRepository.self)
    }

    public let refresh: () async throws -> Void
    public let sponsors: () -> AsyncThrowingStream<[Sponsor], Error>
}

extension SponsorsDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: SponsorsDataProvider = SponsorsDataProvider(
        refresh: { @MainActor in
            try await sponsorsRepository.refresh()
        },
        sponsors: {
            sponsorsRepository.sponsors().stream()
        }
    )

    public static var testValue: SponsorsDataProvider = SponsorsDataProvider(
        refresh: {},
        sponsors: {
            .init {
                Sponsor.companion.fakes()
            }
        }
    )
}

public extension DependencyValues {
    var sponsorsData: SponsorsDataProvider {
        get { self[SponsorsDataProvider.self] }
        set { self[SponsorsDataProvider.self] = newValue }
    }
}
