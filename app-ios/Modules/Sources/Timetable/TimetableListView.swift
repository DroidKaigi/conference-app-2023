import Model
import SwiftUI

struct TimetableListView: View {
    let timeGroupTimetableItems: [TimetableTimeGroupItems]

    var body: some View {
        VStack(spacing: 0) {
            ForEach(timeGroupTimetableItems) { timetableTimeGroupItems in
                HStack(alignment: .top, spacing: 16) {
                    SessionTimeView(
                        startsAt: timetableTimeGroupItems.startsAt,
                        endsAt: timetableTimeGroupItems.endsAt
                    )
                    VStack(spacing: 0) {
                        ForEach(timetableTimeGroupItems.items, id: \.timetableItem.id.value) { timetableItemWithFavorite in
                            TimetableListItemView(
                                timetableItemWithFavorite: timetableItemWithFavorite
                            )
                        }
                    }
                }
                .padding(.horizontal, 8)
                .padding(.vertical, 8)
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
        timeGroupTimetableItems: [
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
