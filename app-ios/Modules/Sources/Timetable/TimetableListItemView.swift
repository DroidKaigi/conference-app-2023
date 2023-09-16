import Assets
import shared
import SwiftUI
import Theme

struct TimetableListItemView: View {
    let timetableItemWithFavorite: TimetableItemWithFavorite
    let searchWord: String
    let onToggleBookmark: () -> Void

    var timetableItem: TimetableItem {
        self.timetableItemWithFavorite.timetableItem
    }

    var body: some View {
        HStack(alignment: .top) {
            VStack(alignment: .leading, spacing: 0) {
                Spacer().frame(height: 16)
                // TODO apply like flexbox layout
                HStack(spacing: SpacingTokens.xxs) {
                    SessionTag(
                        timetableItem.room.name.currentLangTitle,
                        labelColor: AssetColors.Custom.hallText.swiftUIColor,
                        backgroundColor: timetableItem.room.type.toColor()
                    )
                    ForEach(timetableItem.language.labels, id: \.self) { label in
                        SessionTag(
                            label,
                            labelColor: AssetColors.Surface.onSurfaceVariant.swiftUIColor,
                            strokeColor: AssetColors.Outline.outline.swiftUIColor
                        )
                    }
                }
                Spacer().frame(height: 8)
                Text(addHighlightAttributes(title: timetableItem.title.currentLangTitle, searchWord: searchWord))
                    .textStyle(TypographyTokens.titleLarge)
                    .multilineTextAlignment(.leading)
                    .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                if let session = timetableItem as? TimetableItem.Session {
                    if let message = session.message {
                        Spacer().frame(height: 8)
                        HStack(spacing: SpacingTokens.xxs) {
                            Assets.Icons.error.swiftUIImage
                                .renderingMode(.template)
                            Text(message.currentLangTitle)
                                .textStyle(TypographyTokens.labelMedium)
                                .multilineTextAlignment(.leading)
                        }
                        .foregroundStyle(AssetColors.Error.error.swiftUIColor)
                        Spacer().frame(height: 4)
                    }
                    if !session.speakers.isEmpty {
                        Spacer().frame(height: 8)
                        VStack {
                            ForEach(session.speakers, id: \.self) { speaker in
                                PersonLabel(speaker: speaker)
                            }
                        }
                    }
                }
                Spacer().frame(height: 16)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            Button(
                action: onToggleBookmark,
                label: {
                    if timetableItemWithFavorite.isFavorited {
                        Assets.Icons.bookmark.swiftUIImage
                    } else {
                        Assets.Icons.bookmarkBorder.swiftUIImage
                    }
                }
            )
            .frame(width: 56, height: 56)
            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
        }
    }

    private func addHighlightAttributes(title: String, searchWord: String) -> AttributedString {
        var attributedString = AttributedString(title)
        if let range = attributedString.range(of: searchWord, options: .caseInsensitive) {
            attributedString[range].underlineStyle = .single
            attributedString[range].backgroundColor = AssetColors.Secondary.secondaryContainer.swiftUIColor
        }
        return attributedString
    }
}

private extension RoomType {
    func toColor() -> Color {
        let colorAsset = switch self {
        case .rooma: AssetColors.Custom.hallA
        case .roomb: AssetColors.Custom.hallB
        case .roomc: AssetColors.Custom.hallC
        case .roomd: AssetColors.Custom.hallD
        case .roome: AssetColors.Custom.hallE
        case .roomde: AssetColors.Custom.hallD
        default: AssetColors.Custom.white
        }
        return colorAsset.swiftUIColor
    }
}

#Preview {
    TimetableListItemView(
        timetableItemWithFavorite: TimetableItemWithFavorite.companion.fake(),
        searchWord: "",
        onToggleBookmark: {}
    )
}
