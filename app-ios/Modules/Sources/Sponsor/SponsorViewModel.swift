import Foundation
import Model
import shared

struct SponsorState: ViewModelState {
    var planGroupedSponsors: LoadingState<[Plan: [Sponsor]]> = .initial
}

@MainActor
final class SponsorViewModel: ObservableObject {
    @Published private(set) var state: SponsorState = .init()

    func load() async {
        state.planGroupedSponsors = .loading

        do {
            let sponsors = try await FakeSponsorsApiClient().sponsors()

            state.planGroupedSponsors = .loaded(
                [Plan: [Sponsor]](grouping: sponsors) {
                    $0.plan
                }
            )
        } catch let error {
            state.planGroupedSponsors = .failed(error)
        }
    }
}
