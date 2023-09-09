import Model
import shared
import SwiftUI

struct TimeTableTopView: View {

    let timetableTimeGroupItems: [TimetableTimeGroupItems]

    // TimetableViewModel class carries out bridging via @EnvironmentObject.
    @EnvironmentObject var viewModel: TimetableViewModel

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
                                    searchWord: "",
                                    onToggleBookmark: {
                                        viewModel.toggleBookmark(timetableItemWithFavorite.timetableItem.id)
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

#Preview {
    TimeTableTopView(
        timetableTimeGroupItems: [
            TimetableTimeGroupItems(
                startsTimeString: Timetable.companion.fake().contents.first!.timetableItem.startsTimeString,
                endsTimeString: Timetable.companion.fake().contents.first!.timetableItem.endsTimeString,
                items: [Timetable.companion.fake().contents.first!]
            ),
        ],
        viewModel: .init()
    )
}
