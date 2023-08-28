import Model
import SwiftUI

struct TimetableListView: View {
    let timetableTimeGroupItems: [TimetableTimeGroupItems]
    let searchWord: String

    var body: some View {
        VStack(spacing: 0) {
            ForEach(timetableTimeGroupItems) { timetableTimeGroupItem in
                HStack(alignment: .top, spacing: 16) {
                    SessionTimeView(
                        startsTimeString: timetableTimeGroupItem.startsTimeString,
                        endsTimeString: timetableTimeGroupItem.endsTimeString
                    )
                    VStack(spacing: 0) {
                        ForEach(timetableTimeGroupItem.items, id: \.timetableItem.id.value) { timetableItemWithFavorite in
                            NavigationLink(value: TimetableRouting.session(timetableItemWithFavorite.timetableItem)) {
                                TimetableListItemView(
                                    timetableItemWithFavorite: timetableItemWithFavorite,
                                    searchWord: searchWord
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
                startsTimeString: Timetable.companion.fake().contents.first!.timetableItem.startsTimeString,
                endsTimeString: Timetable.companion.fake().contents.first!.timetableItem.endsTimeString,
                items: [Timetable.companion.fake().contents.first!]
            ),
        ],
        searchWord: ""
    )
}

#endif
