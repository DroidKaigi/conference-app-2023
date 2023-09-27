import shared

extension Kotlinx_datetimeInstant {
    public func toDate() -> Date {
        return Date(timeIntervalSince1970: TimeInterval(epochSeconds))
    }
}
