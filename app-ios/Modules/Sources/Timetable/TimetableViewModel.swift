import Foundation
import Model
import shared

struct TimetableState: ViewModelState {
    var selectedDay: DroidKaigi2023Day = .day1
    var timeGroupTimetableItems: LoadingState<[TimetableTimeGroupItems]> = .initial
}

@MainActor
final class TimetableViewModel: ObservableObject {
    @Published private(set) var state: TimetableState = .init()
    private var cachedTimeGroupTimetableItems: [TimetableTimeGroupItems]?

    func load() async {
        state.timeGroupTimetableItems = .loading
        do {
            let timetable = try await FakeSessionsApi().timetable()
            let timetableTimeGroupItems = timetable.timetableItems.map {
                    TimetableTimeGroupItems.Duration(startsAt: $0.startsAt, endsAt: $0.endsAt)
                }
                .map { duration in
                    let items = timetable.contents
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
            cachedTimeGroupTimetableItems = timetableTimeGroupItems
            applySelectedDayToState()
        } catch let error {
            state.timeGroupTimetableItems = .failed(error)
        }
    }

    func selectDay(day: DroidKaigi2023Day) {
        state.selectedDay = day
        applySelectedDayToState()
    }

    private func applySelectedDayToState() {
        guard let cachedTimeGroupTimetableItems = cachedTimeGroupTimetableItems else {
            return
        }
        state.timeGroupTimetableItems = .loaded(
            cachedTimeGroupTimetableItems.filter {
                $0.items.first?.timetableItem.day == state.selectedDay
            }
        )
    }
}
