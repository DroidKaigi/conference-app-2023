import Combine
import Dependencies
import KMPContainer
import Model
import shared

struct ContributorViewState: ViewModelState {
    var contributors: LoadingState<[Contributor]> = .initial
}

@MainActor
final class ContributorViewModel: ObservableObject {
    @Dependency(\.contributorsData) var contributorsData
    @Dependency(\.contributorsRepository) var contributorsRepository
    @Published var state: ContributorViewState = .init()

    func load() async {
        state.contributors = .loading

        do {
            try await contributorsData.refresh()
            for try await contributors in contributorsData.contributors() {
                state.contributors = .loaded(contributors)
            }
        } catch let error {
            state.contributors = .failed(error)
        }
    }
    
    func getRepositoryForCompose() -> ContributorsRepository{
        return contributorsRepository
    }
}
