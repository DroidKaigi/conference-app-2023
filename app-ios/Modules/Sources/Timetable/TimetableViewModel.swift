import Foundation
import Model
import shared

struct TimetableState {
    var timeGroupTimetableItems: [TimetableTimeGroupItems]
}

@MainActor
final class TimetableViewModel: ObservableObject {
    @Published var state: LoadingState<TimetableState> = .initial

    func load() async {
        await MainActor.run {
            state = .loading
        }
        do {
            let timetable = try await FakeSessionsApi().sessions()
            let timetableTimeGroupItems = timetable.timetableItems.map {
                    TimetableTimeGroupItems.Duration(startsAt: $0.startsAt, endsAt: $0.endsAt)
                }
                .map { duration in
                    let items = Timetable.companion.fake().contents
                        .filter { itemWithFavorite in
                            itemWithFavorite.timetableItem.startsAt == duration.startsAt && itemWithFavorite.timetableItem.endsAt == duration.endsAt
                        }
                        .sorted {
                            $0.timetableItem.room.sort < $1.timetableItem.room.sort
                        }
                    return TimetableTimeGroupItems(
                        duration: duration,
                        items: items
                    )
                }
            await MainActor.run {
                state = .loaded(
                    .init(timeGroupTimetableItems: timetableTimeGroupItems)
                )
            }
        } catch let error {
            await MainActor.run {
                state = .failed(error)
            }
        }
    }
}
