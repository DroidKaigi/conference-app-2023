import Dependencies
import Foundation
import KMPContainer
import Model
import shared

struct SearchState: ViewModelState {
    struct LoadedState: Equatable {
        let days: [DroidKaigi2023Day] = DroidKaigi2023Day.entries
        var categories: [TimetableCategory]
        var sessionTypes: [TimetableSessionType]
        var rooms: [TimetableRoom]
        var languages: [TimetableLanguage]
        var timeGroupTimetableItems: [TimetableTimeGroupItems] = []
    }

    var filters: Filters = .init(
        days: [],
        categories: [],
        sessionTypes: [],
        languages: [],
        filterFavorite: false,
        searchWord: ""
    )
    var loadingState: LoadingState<LoadedState> = .initial
}

@MainActor
final class SearchViewModel: ObservableObject {
    @Dependency(\.sessionsData) var sessionsData
    @Published private(set) var state: SearchState = .init()
    private var cachedTimetable: Timetable? {
        didSet {
            applyFiltersToState()
        }
    }
    private var loadTask: Task<Void, Error>?

    deinit {
        loadTask?.cancel()
    }

    func load() async {
        state.loadingState = .loading
        loadTask = Task {
            do {
                for try await timetable in sessionsData.timetable() {
                    cachedTimetable = timetable
                }
            } catch let error {
                state.loadingState = .failed(error)
            }
        }
    }

    func filter(_ apply: (Filters) -> Filters) {
        let newFilters = apply(state.filters)
        state.filters = newFilters
        applyFiltersToState()
    }

    func toggleBookmark(_ id: TimetableItemId) {
        Task {
            try await self.sessionsData.toggleBookmark(id)
        }
    }

    private func applyFiltersToState() {
        guard let cachedTimetable = cachedTimetable else {
            return
        }
        let timetableContents = cachedTimetable.filtered(filters: state.filters).contents
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
        state.loadingState = .loaded(
            .init(
                categories: cachedTimetable.categories,
                sessionTypes: cachedTimetable.sessionTypes,
                rooms: cachedTimetable.rooms,
                languages: cachedTimetable.languages,
                timeGroupTimetableItems: timetableTimeGroupItems
            )
        )
    }
}
