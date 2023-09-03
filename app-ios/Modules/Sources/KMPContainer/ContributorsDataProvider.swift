import Dependencies
import shared

public struct ContributorsDataProvider {
    private static var contributorsRepository: ContributorsRepository {
        Container.shared.get(type: ContributorsRepository.self)
    }

    public let refresh: () async throws -> Void
    public let contributors: () -> AsyncThrowingStream<[Contributor], Error>
}

extension ContributorsDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: ContributorsDataProvider = ContributorsDataProvider(
        refresh: { @MainActor in
            try await contributorsRepository.refresh()
        },
        contributors: {
            contributorsRepository.contributors().stream()
        }
    )

    public static var testValue: ContributorsDataProvider = ContributorsDataProvider(
        refresh: {},
        contributors: {
            .init {
                Contributor.companion.fakes()
            }
        }
    )
}

 public extension DependencyValues {
    var contributorsData: ContributorsDataProvider {
        get { self[ContributorsDataProvider.self] }
        set { self[ContributorsDataProvider.self] = newValue }
    }
     var contributorsRepository: ContributorsRepository {
         return Container.shared.get(type: ContributorsRepository.self)
     }
}
