import Foundation

final class SessionViewModel {
    @Published var titles: [String] = ["Hoge", "Fuga", "Piyo"]

    func addSession() {
        titles.append(UUID().uuidString)
    }
}
