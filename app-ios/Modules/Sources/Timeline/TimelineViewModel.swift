import Foundation

final class TimelineViewModel: ObservableObject {
    @Published var titles: [String] = ["Hoge", "Fuga", "Piyo"]

    func addSession() {
        titles.append(UUID().uuidString)
    }
}
