import Model
import SwiftUI

struct TimetableListView: View {
    let timetableTimeGroupItems: [TimetableTimeGroupItems]

    var body: some View {
        VStack(spacing: 0) {
            ForEach(timetableTimeGroupItems) { timetableTimeGroupItem in
                HStack(alignment: .top, spacing: 16) {
                    SessionTimeView(
                        startsAt: timetableTimeGroupItem.startsAt,
                        endsAt: timetableTimeGroupItem.endsAt
                    )
                    VStack(spacing: 0) {
                        ForEach(timetableTimeGroupItem.items, id: \.timetableItem.id.value) { timetableItemWithFavorite in
                            NavigationLink(value: TimetableRouting.session(timetableItemWithFavorite.timetableItem)) {
                                TimetableListItemView(
                                    timetableItemWithFavorite: timetableItemWithFavorite
                                )
                            }
                        }
                    }
                }
                .padding(8)
                Divider()
            }
        }
        .padding(.vertical, 24)
    }
}

#if DEBUG
import shared

#Preview {
    TimetableListView(
        timetableTimeGroupItems: [
            TimetableTimeGroupItems(
                duration: .init(
                    startsAt: Timetable.companion.fake().contents.first!.timetableItem.startsAt,
                    endsAt: Timetable.companion.fake().contents.first!.timetableItem.endsAt
                ),
                items: [Timetable.companion.fake().contents.first!]
            ),
        ]
    )
}

#endif
