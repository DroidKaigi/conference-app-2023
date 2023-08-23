import Model
import SwiftUI
import Theme

struct SessionTimeView: View {
    var startsTimeString: String
    var endsTimeString: String

    var body: some View {
        VStack(spacing: 4) {
            Text(startsTimeString)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
                .frame(height: 24)
            Rectangle()
                .foregroundColor(AssetColors.Outline.outlineVariant.swiftUIColor)
                .frame(width: 2, height: 8)
            Text(endsTimeString)
                .foregroundStyle(AssetColors.Secondary.secondary.swiftUIColor)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
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
