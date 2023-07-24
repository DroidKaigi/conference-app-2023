public enum LoadingState<State> {
    case initial
    case loading
    case loaded(State)
    case failed(Error)
}
