import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct TimetableState: ViewModelState {
    struct LoadedState: Equatable {
        var timeGroupTimetableItems: [TimetableTimeGroupItems]
        var bookmarks: Set<TimetableItemId>
    }

    var selectedDay: DroidKaigi2023Day = .day1
    var loadedState: LoadingState<LoadedState> = .initial
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
    private var loadTask: Task<Void, Error>?

    deinit {
        loadTask?.cancel()
    }

    func load() {
        state.loadedState = .loading
        loadTask = Task.detached { @MainActor in
            do {
                for try await timetable in self.sessionsData.timetable() {
                    self.cachedTimetable = timetable
                }
            } catch let error {
                self.state.loadedState = .failed(error)
            }
        }
    }

    func selectDay(day: DroidKaigi2023Day) {
        state.selectedDay = day
        applySelectedDayToState()
    }

    func toggleBookmark(_ id: TimetableItemId) {
        Task.detached {
            try await self.sessionsData.toggleBookmark(id)
        }
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
        state.loadedState = .loaded(
            .init(
                timeGroupTimetableItems: timetableTimeGroupItems,
                bookmarks: cachedTimetable.bookmarks
            )
        )
    }
}
