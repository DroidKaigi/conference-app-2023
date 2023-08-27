import Combine
import Dependencies
import KMPContainer
import Model
import shared

struct FloorMapViewState: ViewModelState {
    var sideEvents: LoadingState<FloorMapViewModel.FloorSideEvent> = .initial
}

@MainActor
final class FloorMapViewModel: ObservableObject {
    @Dependency(\.sideEventsData) var sideEventsData
    @Published var state: FloorMapViewState = .init()

    struct FloorSideEvent: Equatable {
        var basement: [SideEvent]
        var ground: [SideEvent]
    }

    func load() async {
        state.sideEvents = .loading

        do {
            let sideEvents = try await sideEventsData.sideEvents()
            let floorSideEvent = FloorSideEvent(
                basement: sideEvents.filter { $0.floorLevel == .basement },
                ground: sideEvents.filter { $0.floorLevel == .ground }
            )
            state.sideEvents = .loaded(floorSideEvent)
        } catch let error {
            state.sideEvents = .failed(error)
        }
    }
}
