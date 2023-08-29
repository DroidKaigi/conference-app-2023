import Dependencies
import Event
import Foundation
import shared

@MainActor
final class SessionViewModel: ObservableObject {
    @Dependency(\.eventKitClient) var eventKitClient
    @Published var isAddingToCalendarConfirming: Bool = false
    let timetableItem: TimetableItem

    init(timetableItem: TimetableItem) {
        self.timetableItem = timetableItem
    }

    func requestAddingToCalendar() async {
        do {
            if try await eventKitClient.requestAccessIfNeeded() {
                isAddingToCalendarConfirming.toggle()
            }
        } catch {
            print(error.localizedDescription)
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
