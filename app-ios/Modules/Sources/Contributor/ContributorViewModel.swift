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
    @Published var state: ContributorViewState = .init()

    func load() async {
        state.contributors = .loading

        do {
            let contributors = try await contributorsData.contributors()
            state.contributors = .loaded(contributors)
        } catch let error {
            state.contributors = .failed(error)
        }
    }
}
