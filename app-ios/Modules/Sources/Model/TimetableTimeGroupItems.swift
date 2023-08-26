import shared

public struct TimetableTimeGroupItems: Identifiable, Equatable, Hashable {
    public var id: String {
        UUID().uuidString
    }

    public var startsTimeString: String
    public var endsTimeString: String
    public var items: [TimetableItemWithFavorite]

    public init(startsTimeString: String, endsTimeString: String, items: [TimetableItemWithFavorite]) {
        self.startsTimeString = startsTimeString
        self.endsTimeString = endsTimeString
        self.items = items
    }
}
