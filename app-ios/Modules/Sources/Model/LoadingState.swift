public enum LoadingState<State: Equatable>: Equatable {
    case initial
    case loading
    case loaded(State)
    case failed(Error)

    public static func == (lhs: LoadingState<State>, rhs: LoadingState<State>) -> Bool {
        switch (lhs, rhs) {
        case (.initial, .initial),
            (.loading, .loading):
            return true
        case (.loaded(let lState), .loaded(let rState)):
            return lState == rState
        case (.failed(let lError), .failed(let rError)):
            return lError.localizedDescription == rError.localizedDescription
        default:
            return false
        }
    }
}
