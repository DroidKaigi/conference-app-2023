import Dependencies
import Event
import Foundation
import shared

@MainActor
final class SessionViewModel: ObservableObject {
    @Dependency(\.eventKitClient) var eventKitClient
    let timetableItem: TimetableItem

    init(timetableItem: TimetableItem) {
        self.timetableItem = timetableItem
    }

    func requestEventAccessIfNeeded() async -> Bool {
        do {
            return try await eventKitClient.requestAccessIfNeeded()
        } catch {
            print(error.localizedDescription)
            return false
        }
    }

    func addToCalendar() {
        do {
            try eventKitClient.addEvent(
                title: timetableItem.title.currentLangTitle,
                startDate: timetableItem.startsAt.toDate(),
                endDate: timetableItem.endsAt.toDate()
            )
        } catch {
            print(error.localizedDescription)
        }
    }
}
