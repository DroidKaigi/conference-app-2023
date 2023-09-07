import SwiftUI
import Theme

struct SectionTitle: View {
    let title: String

    var body: some View {
        Text(title)
            .font(Font.custom(FontAssets.Montserrat.medium, size: 16))
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.top, 16)
    }
}

#Preview {
    SectionTitle(title: "SectionTitle")
}
