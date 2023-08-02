import Assets
import shared
import SwiftUI
import Theme

struct TimetableListItemView: View {
    let timetableItemWithFavorite: TimetableItemWithFavorite

    var timetableItem: TimetableItem {
        self.timetableItemWithFavorite.timetableItem
    }

    var body: some View {
        HStack(alignment: .top) {
            VStack(alignment: .leading, spacing: 0) {
                Spacer().frame(height: 4)
                Text(timetableItem.language.langOfSpeaker)
                    .font(Font.system(size: 12, weight: .medium))
                    .padding(.horizontal, 8)
                    .padding(.vertical, 4)
                    .overlay(
                        RoundedRectangle(cornerRadius: 4)
                            .inset(by: 0.5)
                            .stroke(AssetColors.outline.swiftUIColor, lineWidth: 1)
                    )
                    .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                Spacer().frame(height: 12)
                Text(timetableItem.title.currentLangTitle)
                    .multilineTextAlignment(.leading)
                    .font(Font.system(size: 22, weight: .medium, design: .default))
                    .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                    .frame(maxWidth: .infinity, alignment: .leading)
                if let session = timetableItem as? TimetableItem.Session {
                    if let message = session.message {
                        Spacer().frame(height: 8)
                        HStack(spacing: 4) {
                            Assets.Icons.error.swiftUIImage
                                .renderingMode(.template)
                            Text(message.currentLangTitle)
                                .font(Font.system(size: 12, weight: .regular, design: .default))
                        }
                        .foregroundStyle(AssetColors.Error.error.swiftUIColor)
                        Spacer().frame(height: 4)
                    }
                    if !session.speakers.isEmpty {
                        Spacer().frame(height: 8)
                        PersonLabel(
                            speakers: session.speakers
                        )
                    }
                }
                Spacer().frame(height: 8)
            }
            Button(
                action: {
                    // TODO: favorite action
                },
                label: {
                    if timetableItemWithFavorite.isFavorited {
                        Assets.Icons.bookmark.swiftUIImage
                    } else {
                        Assets.Icons.bookmarkBorder.swiftUIImage
                    }
                }
            )
            .frame(width: 52, height: 52)
            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
        }
    }
}

 #Preview {
     TimetableListItemView(
         timetableItemWithFavorite: TimetableItemWithFavorite.companion.fake()
     )
 }
