import SwiftUI
import Theme

private let formatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.dateStyle = .none
    formatter.timeStyle = .short
    return formatter
}()

struct SessionTimeView: View {
    var startsAt: Date
    var endsAt: Date

    var body: some View {
        VStack(spacing: 4) {
            Text(formatter.string(from: startsAt))
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
                .frame(height: 24)
            Rectangle()
                .foregroundColor(AssetColors.Outline.outlineVariant.swiftUIColor)
                .frame(width: 2, height: 8)
            Text(formatter.string(from: endsAt))
                .foregroundStyle(AssetColors.Secondary.secondary.swiftUIColor)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
                .frame(height: 24)
        }
    }
}

 #Preview {
     SessionTimeView(startsAt: .distantPast, endsAt: .distantFuture)
 }
