import SwiftUI
import Theme

struct ListTile: View {
    let icon: Image
    let title: String

    var body: some View {
        HStack(spacing: SpacingTokens.s) {
            icon
                .renderingMode(.template)
                .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
            Text(title)
                .textStyle(TypographyTokens.labelLarge)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 24)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

#Preview {
    ListTile(
        icon: Image(systemName: "calendar"),
        title: "カレンダー"
    )
}
