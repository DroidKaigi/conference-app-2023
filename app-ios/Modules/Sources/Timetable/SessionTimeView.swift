import Model
import SwiftUI
import Theme

struct SessionTimeView: View {
    var startsTimeString: String
    var endsTimeString: String

    var body: some View {
        VStack(spacing: 4) {
            Text(startsTimeString)
                .textStyle(TypographyTokens.titleMedium)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                .frame(height: 24)
            Rectangle()
                .foregroundColor(AssetColors.Outline.outlineVariant.swiftUIColor)
                .frame(width: 2, height: 8)
            Text(endsTimeString)
                .textStyle(TypographyTokens.titleMedium)
                .foregroundStyle(AssetColors.Secondary.secondary.swiftUIColor)
                .frame(height: 24)
        }
    }
}

#if DEBUG
import shared

#Preview {
    SessionTimeView(
        startsTimeString: Timetable.companion.fake().contents.first!.timetableItem.startsTimeString,
        endsTimeString: Timetable.companion.fake().contents.first!.timetableItem.endsTimeString
    )
}

#endif
