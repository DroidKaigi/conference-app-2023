import Foundation
import shared

final class SessionViewModel: ObservableObject {
    let timetableItem: TimetableItem

    init(timetableItem: TimetableItem) {
        self.timetableItem = timetableItem
    }
}
