import shared

extension Kotlinx_datetimeInstant {
    public func toDate(calendar: Calendar = .current) -> Date {
        return Date(timeIntervalSince1970: TimeInterval(toEpochMilliseconds()))
    }
}
