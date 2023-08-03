import shared

extension TimetableItemWithFavorite: Identifiable {
    public var id: String {
        timetableItem.id.value
    }
}
