import shared

public protocol TimetableFilter: Identifiable, Equatable {
    var title: MultiLangText { get }
}

extension DroidKaigi2023Day: TimetableFilter {
    public var title: MultiLangText {
        .init(jaTitle: name, enTitle: name)
    }
}
extension TimetableCategory: TimetableFilter {}
extension TimetableLanguage: TimetableFilter {
    public var title: MultiLangText {
        .init(jaTitle: langOfSpeaker, enTitle: langOfSpeaker)
    }
}
extension TimetableRoom: TimetableFilter {
    public var title: MultiLangText {
        self.name
    }
}
extension TimetableSessionType: TimetableFilter {
    public var title: MultiLangText {
        self.label
    }
}
extension Lang: TimetableFilter {
    public var title: MultiLangText {
        .init(jaTitle: name, enTitle: name)
    }
}
