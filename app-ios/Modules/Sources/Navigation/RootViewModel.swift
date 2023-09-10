import Dependencies
import Foundation
import KMPContainer
import Model

struct RootViewState: ViewModelState {
    var isAchivementEnabled: LoadingState<Bool> = .initial
}

@MainActor
class RootViewModel: ObservableObject {
    @Dependency(\.achievementData) var achievementData
    @Published var state: RootViewState = .init()

    func load() async {
        state.isAchivementEnabled = .loading

        do {
            for try await isAchievementEnabled in achievementData.achievementEnabled() {
                state.isAchivementEnabled = .loaded(isAchievementEnabled)
            }
        } catch {
            state.isAchivementEnabled = .failed(error)
        }
    }
}
