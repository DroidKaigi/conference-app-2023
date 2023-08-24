import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct SponsorState: ViewModelState {
    var planGroupedSponsors: LoadingState<[Plan: [Sponsor]]> = .initial
}

@MainActor
final class SponsorViewModel: ObservableObject {
    @Dependency(\.sponsorsData) var sponsorsData
    @Published private(set) var state: SponsorState = .init()

    func load() async {
        state.planGroupedSponsors = .loading

        do {
            try await sponsorsData.refresh()
            for try await sponsors in sponsorsData.sponsors() {
                state.planGroupedSponsors = .loaded(
                    [Plan: [Sponsor]](grouping: sponsors) {
                        $0.plan
                    }
                )
            }
        } catch let error {
            state.planGroupedSponsors = .failed(error)
        }
    }
}
