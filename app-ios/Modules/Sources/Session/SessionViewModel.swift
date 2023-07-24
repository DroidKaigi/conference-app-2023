import Foundation

final class SessionViewModel: ObservableObject {
    @Published var titles: [String] = ["Hoge", "Fuga", "Piyo"]

    func addSession() {
        titles.append(UUID().uuidString)
    }
}
