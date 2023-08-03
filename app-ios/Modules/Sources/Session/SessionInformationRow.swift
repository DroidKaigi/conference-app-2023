import SwiftUI
import Theme

struct SessionInformationRow: View {
    let icon: Image
    let title: String
    let content: String

    var body: some View {
        HStack {
            icon
            Text(title)
                .font(Font.system(size: 14, weight: .semibold))
            Text(content)
                .font(Font.system(size: 14, weight: .semibold))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .foregroundStyle(
            AssetColors.Surface.onSurfaceVariant.swiftUIColor
        )
    }
}

#Preview {
    SessionInformationRow(
        icon: Image(systemName: "clock"),
        title: "日付",
        content: "2023.09.14 / 11:20 ~ 12:00 (40分)"
    )
}
