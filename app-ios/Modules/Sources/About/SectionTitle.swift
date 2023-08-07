import SwiftUI

struct SectionTitle: View {
    let title: String

    var body: some View {
        Text(title)
            .font(Font.system(size: 16, weight: .medium))
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.top, 16)
    }
}

#Preview {
    SectionTitle(title: "SectionTitle")
}
