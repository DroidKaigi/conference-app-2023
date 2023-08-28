import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct BookmarkState: ViewModelState {
    var selectedDay: DroidKaigi2023Day?
    var timeGroupTimetableItems: LoadingState<[TimetableTimeGroupItems]> = .initial
}

@MainActor
final class BookmarkViewModel: ObservableObject {
    @Dependency(\.sessionsData) var sessionsData
    @Published private(set) var state: BookmarkState = .init()
    private var cachedTimetable: Timetable? {
        didSet {
            applySelectedDayToState()
        }
    }
    private var loadTask: Task<Void, Error>?

    deinit {
        loadTask?.cancel()
    }

    func load() async {
        state.timeGroupTimetableItems = .loading
        loadTask = Task {
            do {
                for try await timetable in sessionsData.timetable() {
                    cachedTimetable = timetable
                }
            } catch let error {
                state.timeGroupTimetableItems = .failed(error)
            }
        }
    }

    func selectDay(day: DroidKaigi2023Day?) {
        state.selectedDay = day
        applySelectedDayToState()
    }

    func toggleBookmark(_ id: TimetableItemId) {
        Task {
            try await self.sessionsData.toggleBookmark(id)
        }
    }

    private func applySelectedDayToState() {
        guard let cachedTimetable = cachedTimetable else {
            return
        }
        let timetableContents = state.selectedDay.map { day in
            cachedTimetable.dayTimetable(droidKaigi2023Day: day).contents
        } ?? cachedTimetable.contents
        let timetableTimeGroupItems = timetableContents
            .map { content in
                let items = timetableContents
                    .filter { itemWithFavorite in
                        itemWithFavorite.timetableItem.startsTimeString == content.timetableItem.startsTimeString && itemWithFavorite.timetableItem.endsTimeString == content.timetableItem.endsTimeString
                    }
                    .sorted {
                        $0.timetableItem.room.name.currentLangTitle < $1.timetableItem.room.name.currentLangTitle
                    }
                return TimetableTimeGroupItems(
                    startsTimeString: content.timetableItem.startsTimeString,
                    endsTimeString: content.timetableItem.endsTimeString,
                    items: items
                )
            }
        state.timeGroupTimetableItems = .loaded(
            timetableTimeGroupItems
        )
    }
}
