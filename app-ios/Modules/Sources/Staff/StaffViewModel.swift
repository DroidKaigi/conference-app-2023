import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct StaffState: ViewModelState {
    var staffs: LoadingState<[Staff]> = .initial
}

@MainActor
final class StaffViewModel: ObservableObject {
    @Dependency(\.staffsData) var staffsData
    @Published private(set) var state: StaffState = .init()

    func load() async {
        state.staffs = .loading
        do {
            for try await staffs in staffsData.staffs() {
                state.staffs = .loaded(staffs)
            }
        } catch let error {
            state.staffs = .failed(error)
        }
    }
}
