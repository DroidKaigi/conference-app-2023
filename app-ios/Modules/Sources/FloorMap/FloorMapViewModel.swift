import Combine
import Dependencies
import KMPContainer
import Model
import shared

struct FloorMapViewState: ViewModelState {
    var sideEvents: LoadingState<FloorSideEvent> = .initial
}

struct FloorSideEvent: Equatable {
    var basement: [SideEvent]
    var ground: [SideEvent]
}

@MainActor
final class FloorMapViewModel: ObservableObject {
    @Dependency(\.sideEventsData) var sideEventsData
    @Published var state: FloorMapViewState = .init()
    
    func load() async {
        state.sideEvents = .loading
        
        do {
            let sideEvents = try await sideEventsData.sideEvents()
            print("sideEvent", sideEvents)
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


