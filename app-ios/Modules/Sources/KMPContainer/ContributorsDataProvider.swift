import Dependencies
import shared

public struct ContributorsDataProvider {
    public let contributors: () async throws -> [Contributor]
}

extension ContributorsDataProvider: DependencyKey {
    public static var liveValue: ContributorsDataProvider = ContributorsDataProvider(
        contributors: { @MainActor in
            try await FakeContributorsApiClient().contributors()
        }
    )
}

public extension DependencyValues {
    var contributorsData: ContributorsDataProvider {
        get { self[ContributorsDataProvider.self] }
        set { self[ContributorsDataProvider.self] = newValue }
    }
}
