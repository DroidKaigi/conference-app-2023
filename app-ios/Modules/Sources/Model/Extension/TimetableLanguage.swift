import shared

extension Lang {
    public var timetable: TimetableLanguage {
        .init(
            langOfSpeaker: title.currentLangTitle,
            isInterpretationTarget: !title.jaTitle.isEmpty && !title.enTitle.isEmpty
        )
    }
}
