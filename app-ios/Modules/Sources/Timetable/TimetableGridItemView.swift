import Assets
import Component
import shared
import SwiftUI
import Theme

struct TimetableGridItemView: View {
    let timetableItemWithFavorite: TimetableItemWithFavorite

    var timetableItem: TimetableItem {
        self.timetableItemWithFavorite.timetableItem
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(timetableItem.title.currentLangTitle)
                .textStyle(TypographyTokens.labelLarge)
                .multilineTextAlignment(.leading)

            HStack(spacing: 3) {
                Assets.Icons.schedule.swiftUIImage
                    .renderingMode(.template)
                Text("\(timetableItem.startsTimeString) - \(timetableItem.endsTimeString)")
                    .textStyle(TypographyTokens.bodySmall)
            }

            Spacer()

            if let session = timetableItem as? TimetableItem.Session {
                HStack(alignment: .center, spacing: 4) {
                    if session.speakers.count > 1 {
                        ForEach(session.speakers, id: \.self) { speaker in
                            speakerIcon(speaker)
                        }
                    } else if let speaker = session.speakers.first {
                        speakerIcon(speaker)
                        Text(speaker.name)
                            .textStyle(TypographyTokens.labelMedium)
                            .lineLimit(2)
                    }

                    Spacer()

                    if session.message != nil {
                        Assets.Icons.info.swiftUIImage
                            .resizable()
                            .renderingMode(.template)
                            .frame(width: 16, height: 16)
                            .foregroundStyle(AssetColors.Error.errorContainer.swiftUIColor)
                    }
                }
            }
        }
        .padding(RadiusTokens.s)
        .frame(maxWidth: .infinity, alignment: .leading)
        .foregroundStyle(AssetColors.Custom.hallText.swiftUIColor)
        .background(timetableItem.room.type.toColor())
        .clipShape(RoundedRectangle(cornerRadius: 4))
    }

    private func speakerIcon(_ speaker: TimetableSpeaker) -> some View {
        CacheAsyncImage(url: URL(string: speaker.iconUrl)) { image in
            image.resizable()
        } placeholder: {
            Color.gray
        }
        .frame(width: 32, height: 32)
        .scaledToFill()
        .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.xs))
        .overlay(
            RoundedRectangle(cornerRadius: RadiusTokens.xs)
                .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
        )
    }
}

#Preview {
    TimetableGridItemView(
        timetableItemWithFavorite: TimetableItemWithFavorite.companion.fake()
    )
}
