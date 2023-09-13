import AsyncAlgorithms
import CryptoKit
import DeepLink
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
    private let deepLink = DeepLink()
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

    func read(
        didInvalidateHandler: @escaping () -> Void
    ) async -> Achievement? {
        do {
            let shortLink = try await nfcReader.read(
                didInvalidateHandler: didInvalidateHandler,
                urlResolver: { [weak deepLink] url in
                    await deepLink?.canBeResolved(from: url) ?? false
                }
            )
            let dynamicLink = try await deepLink.dynamicLink(shortLink: shortLink)!
            return try await deepLink.handleDynamicLink(dynamicLink: dynamicLink)
        } catch {
            print(error)
        }
        return nil
    }
}
