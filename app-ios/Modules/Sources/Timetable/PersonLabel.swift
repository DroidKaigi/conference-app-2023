import Component
import shared
import SwiftUI
import Theme

struct PersonLabel: View {
    let speakers: [TimetableSpeaker]

    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            HStack(spacing: -8) {
                ForEach(speakers.map(\.iconUrl), id: \.self) { iconUrl in
                    CacheAsyncImage(url: URL(string: iconUrl)) { image in
                        image.resizable()
                    } placeholder: {
                        Color.gray
                    }
                    .frame(width: 40, height: 40)
                    .scaledToFill()
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(AssetColors.outline.swiftUIColor, lineWidth: 1)
                    )
                }
            }

            Text(speakers.map(\.name).joined(separator: ","))
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                .font(Font.system(size: 14, weight: .medium))
                .lineLimit(2)
        }
    }
}

// Waiting for '#Preview is only available in iOS 17 or newer' bug fixes
// https://developer.apple.com/forums/thread/731182
//
 #Preview {
     PersonLabel(
        speakers: [TimetableSpeaker](repeating: TimetableSpeaker(id: UUID().uuidString, name: "speaker name", iconUrl: "https://placehold.jp/40x40.png", bio: "Bio", tagLine: ""), count: 3)
     )
 }
