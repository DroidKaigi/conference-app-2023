import shared

public struct TimetableRoomGroupItems: Identifiable, Equatable, Hashable {
    public var id: String {
        UUID().uuidString
    }

    public var room: TimetableRoom
    public var items: [TimetableItemWithFavorite]

    public init(room: TimetableRoom, items: [TimetableItemWithFavorite]) {
        self.room = room
        self.items = items
    }
}
