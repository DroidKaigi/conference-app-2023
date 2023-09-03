import Dependencies
import Foundation
import KMPContainer
import Model

struct RootViewState: ViewModelState {
    var isStampEnabled: LoadingState<Bool> = .initial
}

@MainActor
class RootViewModel: ObservableObject {
    @Dependency(\.stampData) var stampData
    @Published var state: RootViewState = .init()

    func load() async {
        state.isStampEnabled = .loading

        do {
            for try await isStampEnabled in stampData.stampEnabled() {
                state.isStampEnabled = .loaded(isStampEnabled)
            }
        } catch {
            state.isStampEnabled = .failed(error)
        }
    }
}
