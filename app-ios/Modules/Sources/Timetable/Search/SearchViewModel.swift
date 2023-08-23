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

    func load() async {
        state.loadingState = .loading
        do {
            let timetable = try await sessionsData.timetable()
            cachedTimetable = timetable
        } catch let error {
            state.loadingState = .failed(error)
        }
    }

    func filter(_ apply: (Filters) -> Filters) {
        let newFilters = apply(state.filters)
        state.filters = newFilters
        applyFiltersToState()
    }

    private func applyFiltersToState() {
        guard let cachedTimetable = cachedTimetable else {
            return
        }
        let timetableContents = cachedTimetable.filtered(filters: state.filters).contents
        let timetableTimeGroupItems = timetableContents
            .map {
                TimetableTimeGroupItems.Duration(startsAt: $0.timetableItem.startsAt, endsAt: $0.timetableItem.endsAt)
            }
            .map { duration in
                let items = timetableContents
                    .filter { itemWithFavorite in
                        itemWithFavorite.timetableItem.startsAt == duration.startsAt && itemWithFavorite.timetableItem.endsAt == duration.endsAt
                    }
                    .sorted {
                        $0.timetableItem.room.name.currentLangTitie < $1.timetableItem.room.name.currentLangTitle
                    }
                return TimetableTimeGroupItems(
                    duration: duration,
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
