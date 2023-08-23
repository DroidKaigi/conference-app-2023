import Foundation

public struct IdentifiableURL: Identifiable {
    public var id: URL?

    public init(_ id: URL?) {
        self.id = id
    }

    public init(string: String) {
        self.id = URL(string: string)
    }
}
