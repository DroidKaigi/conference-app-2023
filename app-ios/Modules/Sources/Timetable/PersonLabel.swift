import Component
import shared
import SwiftUI
import Theme

struct PersonLabel: View {
    let speaker: TimetableSpeaker

    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            CacheAsyncImage(url: URL(string: speaker.iconUrl)) { image in
                image.resizable()
            } placeholder: {
                Color.gray
            }
            .frame(width: 40, height: 40)
            .scaledToFill()
            .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.s))
            .overlay(
                RoundedRectangle(cornerRadius: RadiusTokens.s)
                    .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
            )

            Text(speaker.name)
                .textStyle(TypographyTokens.bodyMedium)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                .lineLimit(2)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

// Waiting for '#Preview is only available in iOS 17 or newer' bug fixes
// https://developer.apple.com/forums/thread/731182
//
#Preview {
    PersonLabel(
        speaker: TimetableSpeaker(
            id: UUID().uuidString,
            name: "speaker name",
            iconUrl: "https://placehold.jp/40x40.png",
            bio: "Bio",
            tagLine: ""
        )
    )
}
