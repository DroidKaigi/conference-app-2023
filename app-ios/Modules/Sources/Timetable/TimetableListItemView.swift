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
                // TODO apply like flexbox layout
                LazyHStack(spacing: 4) {
                    InfoLabel(
                        timetableItem.room.name.currentLangTitle,
                        labelColor: AssetColors.Custom.hallText.swiftUIColor,
                        backgroundColor: timetableItem.room.type.toColor()
                    )
                    ForEach(timetableItem.language.labels, id: \.self) { label in
                        InfoLabel(
                            label,
                            labelColor: AssetColors.Surface.onSurfaceVariant.swiftUIColor,
                            strokeColor: AssetColors.outline.swiftUIColor
                        )
                    }
                }
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
                                .multilineTextAlignment(.leading)
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

private extension RoomType {
    func toColor() -> Color {
        let colorAsset = switch self {
        case .rooma: AssetColors.Custom.hallA
        case .roomb: AssetColors.Custom.hallB
        case .roomc: AssetColors.Custom.hallC
        case .roomd: AssetColors.Custom.hallD
        case .roome: AssetColors.Custom.hallE
        default: AssetColors.Custom.white
        }
        return colorAsset.swiftUIColor
    }
}

#Preview {
    TimetableListItemView(
        timetableItemWithFavorite: TimetableItemWithFavorite.companion.fake()
    )
}
