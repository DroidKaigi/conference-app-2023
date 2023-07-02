import shared
import SwiftUI

public typealias ViewBuilder<each Params, V: View> = (_ params: repeat each Params) -> V

public struct TimetableView<SessionView: View>: View {
    @ObservedObject var viewModel: TimetableViewModel = .init()
    private let sessionViewBuilder: ViewBuilder<TimetableItem, SessionView>

    public init(sessionViewBuilder: @escaping ViewBuilder<TimetableItem, SessionView>) {
        self.sessionViewBuilder = sessionViewBuilder
    }

    public var body: some View {
        NavigationStack {
            switch viewModel.state {
            case .initial, .loading:
                ProgressView()
            case .failed:
                EmptyView()
            case .loaded(let state):
                ScrollView(.vertical) {
                    LazyVStack(spacing: 32) {
                        ForEach(state.timeGroupTimetableItems) { timetableTimeGroupItems in
                            HStack(alignment: .top, spacing: 28) {
                                VStack(alignment: .center, spacing: 0) {
                                    SessionTimeView(
                                        startsAt: timetableTimeGroupItems.startsAt,
                                        endsAt: timetableTimeGroupItems.endsAt
                                    )
                                }
                                .foregroundColor(.secondary)
                                VStack(spacing: 32) {
                                    ForEach(timetableTimeGroupItems.items, id: \.timetableItem.id.value) { timetableItemWithFavorite in
                                        NavigationLink(value: timetableItemWithFavorite.timetableItem) {
                                            TimetableListItemView(
                                                timetableItemWithFavorite: timetableItemWithFavorite
                                            )
                                        }
                                    }
                                }
                            }
                            .padding(.horizontal, 16)
                        }
                    }
                    .padding(.vertical, 24)
                }
                .navigationDestination(for: TimetableItem.self) { item in
                    sessionViewBuilder(item)
                }
            }
        }
        .task {
            await viewModel.load()
        }
    }
}

// #Preview {
//     TimetableView()
// }
