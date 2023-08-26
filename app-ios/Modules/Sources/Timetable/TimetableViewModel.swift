import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct TimetableState: ViewModelState {
    var selectedDay: DroidKaigi2023Day = .day1
    var timeGroupTimetableItems: LoadingState<[TimetableTimeGroupItems]> = .initial
}

@MainActor
final class TimetableViewModel: ObservableObject {
    @Dependency(\.sessionsData) var sessionsData
    @Published private(set) var state: TimetableState = .init()
    private var cachedTimetable: Timetable? {
        didSet {
            applySelectedDayToState()
        }
    }

    func load() async {
        state.timeGroupTimetableItems = .loading
        do {
            for try await timetable in sessionsData.timetable() {
                cachedTimetable = timetable
            }
        } catch let error {
            state.timeGroupTimetableItems = .failed(error)
        }
    }

    func selectDay(day: DroidKaigi2023Day) {
        state.selectedDay = day
        applySelectedDayToState()
    }

    private func applySelectedDayToState() {
        guard let cachedTimetable = cachedTimetable else {
            return
        }
        let timetableTimeGroupItems = cachedTimetable.dayTimetable(droidKaigi2023Day: state.selectedDay)
            .timetableItems
            .map { item in
                let items = cachedTimetable.contents
                    .filter { itemWithFavorite in
                        itemWithFavorite.timetableItem.startsTimeString == item.startsTimeString && itemWithFavorite.timetableItem.endsTimeString == item.endsTimeString
                    }
                    .sorted {
                        $0.timetableItem.room.name.currentLangTitle < $1.timetableItem.room.name.currentLangTitle
                    }
                return TimetableTimeGroupItems(
                    startsTimeString: item.startsTimeString,
                    endsTimeString: item.endsTimeString,
                    items: items
                )
            }
        state.timeGroupTimetableItems = .loaded(
            timetableTimeGroupItems
        )
    }
}
