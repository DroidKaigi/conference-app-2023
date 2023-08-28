import Model
import shared
import SwiftUI

struct TimetableListView: View {
    let timetableTimeGroupItems: [TimetableTimeGroupItems]
    let searchWord: String
    let onToggleBookmark: (TimetableItemId) -> Void

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
                                    searchWord: searchWord,
                                    onToggleBookmark: {
                                        onToggleBookmark(timetableItemWithFavorite.timetableItem.id)
                                    }
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
#Preview {
    TimetableListView(
        timetableTimeGroupItems: [
            TimetableTimeGroupItems(
                startsTimeString: Timetable.companion.fake().contents.first!.timetableItem.startsTimeString,
                endsTimeString: Timetable.companion.fake().contents.first!.timetableItem.endsTimeString,
                items: [Timetable.companion.fake().contents.first!]
            ),
        ],
        searchWord: "",
        onToggleBookmark: {_ in}
    )
}
#endif
