import Dependencies
import Foundation
import KMPContainer
import Model

struct RootViewState: ViewModelState {
    var isAchivementEnabled: LoadingState<Bool> = .initial
}

@MainActor
class RootViewModel: ObservableObject {
    @Dependency(\.stampData) var stampData
    @Published var state: RootViewState = .init()

    func load() async {
        state.isAchivementEnabled = .loading

        do {
            for try await isStampEnabled in stampData.stampEnabled() {
                state.isAchivementEnabled = .loaded(isStampEnabled)
            }
        } catch {
            state.isAchivementEnabled = .failed(error)
        }
    }
}
