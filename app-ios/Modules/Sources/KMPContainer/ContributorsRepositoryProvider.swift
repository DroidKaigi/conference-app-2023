import Dependencies
import shared

public struct ContributorsRepositoryProvider {
    public let contributorsRepository: any ContributorsRepository
}

extension ContributorsRepositoryProvider: DependencyKey {
    @MainActor
    public static var liveValue: ContributorsRepositoryProvider = ContributorsRepositoryProvider(
        contributorsRepository: Container.shared.get(type: ContributorsRepository.self) as ContributorsRepository
    )

    public static var testValue: ContributorsRepositoryProvider = ContributorsRepositoryProvider(
        contributorsRepository: DefaultContributorsRepository(
                contributorsApi: FakeContributorsApiClient()
            )
    )
}

 public extension DependencyValues {
    var contributorsRepositoryData: ContributorsRepositoryProvider {
        get { self[ContributorsRepositoryProvider.self] }
        set { self[ContributorsRepositoryProvider.self] = newValue }
    }
}
