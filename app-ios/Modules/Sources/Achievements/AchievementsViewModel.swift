import AsyncAlgorithms
import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct AchievementsViewState: ViewModelState {
    struct LoadedState: Equatable {
        var description: String
        var achievements: Set<Achievement>
    }

    var loadedState: LoadingState<LoadedState> = .initial
}

@MainActor
class AchievementsViewModel: ObservableObject {
    @Dependency(\.achievementData) var achievementData
    @Published var state: AchievementsViewState = .init()
    private var loadTask: Task<Void, Error>?

    deinit {
        loadTask?.cancel()
    }

    func load() async {
        state.loadedState = .loading

        loadTask = Task.detached { @MainActor in
            do {
                for try await (description, achievements) in combineLatest(
                    self.achievementData.achievementDetailDescription(),
                    self.achievementData.achievements()
                ) {
                    self.state.loadedState = .loaded(
                        .init(
                            description: description,
                            achievements: achievements
                        )
                    )
                }
            } catch {
                self.state.loadedState = .failed(error)
            }
        }
    }
}
