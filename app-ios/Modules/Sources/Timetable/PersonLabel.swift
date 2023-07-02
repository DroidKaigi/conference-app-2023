import SwiftUI

struct PersonLabel: View {
    let name: String
    let iconUrl: String

    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            AsyncImage(url: URL(string: iconUrl))
            .frame(width: 24, height: 24)
            .clipShape(Circle())
            Text(name)
                .font(Font.system(size: 12, weight: .medium))
                .frame(height: 16)
        }
        .foregroundColor(.secondary
        )
    }
}

// Waiting for '#Preview is only available in iOS 17 or newer' bug fixes
// https://developer.apple.com/forums/thread/731182
//
// #Preview {
//     PersonLabel(
//         name: "speaker name",
//         iconUrl: "https://placehold.jp/24x24.png"
//     )
// }
