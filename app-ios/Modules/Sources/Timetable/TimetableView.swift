import SwiftUI


public struct TimetableView: View {
    @ObservedObject var viewModel: TimetableViewModel = .init()

    public init() {}

    public var body: some View {
        ScrollView(.vertical) {
            LazyVStack(spacing: 32) {
                ForEach(viewModel.timeGroupTimetableItems) { timetableTimeGroupItems in
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
                                TimetableListItemView(
                                    timetableItemWithFavorite: timetableItemWithFavorite,
                                    onTap: {
                                    }
                                )
                            }
                        }
                    }
                    .padding(.horizontal, 16)
                }
            }
            .padding(.vertical, 24)
//            .background(
//                ScrollDetector(coordinateSpace: .named("TimetableListView"))
//                    .onDetect { position in
//                        onScroll(position)
//                    }
//            )
        }
    }
}

//#Preview {
//    TimetableView()
//}
