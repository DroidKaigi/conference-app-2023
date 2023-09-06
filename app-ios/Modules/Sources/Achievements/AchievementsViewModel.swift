import AsyncAlgorithms
import CryptoKit
import Dependencies
import Foundation
import KMPContainer
import Model
import NFC
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
    private let nfcReader = NFCReader()
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

    func read() async -> Achievement? {
        do {
            if let urlString = try await nfcReader.read(), let url = URL(string: urlString) {
                let hashedString = idToSha256(id: url.lastPathComponent)

                let target: Achievement? = switch hashedString {
                case Achievement.arcticfox.sha256:
                    Achievement.arcticfox
                case Achievement.bumblebee.sha256:
                    Achievement.bumblebee
                case Achievement.chipmunk.sha256:
                    Achievement.chipmunk
                case Achievement.dolphin.sha256:
                    Achievement.dolphin
                case Achievement.electriceel.sha256:
                    Achievement.electriceel
                default:
                    nil
                }

                if let achievement = target {
                    try await achievementData.saveAchievement(achievement)
                    return achievement
                }
            }
        } catch {
            print(error)
        }
        return nil
    }

    private func idToSha256(id: String) -> String? {
        guard let data = id.data(using: .utf8) else {
            return nil
        }
        let digest = SHA256.hash(data: data)
        return digest.compactMap { String(format: "%02x", $0) }.joined()
    }
}
