import Assets
import shared
import SwiftUI
import Theme

struct TimetableListItemView: View {

    @Environment(\.colorScheme) var colorScheme

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
                    // TODO use AssertColors
                    let labelColor: Color = if colorScheme == .light {
                        .white
                    } else {
                        .black
                    }

                    InfoLabel(
                        timetableItem.room.name.currentLangTitle,
                        labelColor: labelColor,
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

private extension RoomIndex {
    func toColor() -> Color {
        let colorAsset = switch self {
        case .room1: AssetColors.Custom.hallA
        case .room2: AssetColors.Custom.hallB
        case .room3: AssetColors.Custom.hallC
        case .room4: AssetColors.Custom.hallD
        case .room5: AssetColors.Custom.hallE
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
