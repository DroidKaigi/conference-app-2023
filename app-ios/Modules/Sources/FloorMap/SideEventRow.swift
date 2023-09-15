import Assets
import Component
import shared
import SwiftUI
import Theme

struct SideEventRow: View {
    var sideEvent: SideEvent

    var body: some View {
        HStack(alignment: .top, spacing: SpacingTokens.s) {
            VStack(alignment: .leading, spacing: SpacingTokens.xs) {
                VStack(alignment: .leading, spacing: SpacingTokens.xxs) {
                    // Icon & Title
                    HStack(alignment: .top, spacing: SpacingTokens.xs) {
                        sideEvent.icon
                            .renderingMode(.template)
                            .frame(width: 16, height: 16)
                            .foregroundStyle(sideEvent.iconColor)

                        Text(sideEvent.title.currentLangTitle)
                            .textStyle(TypographyTokens.labelLarge)
                    }
                    // Description
                    Text(sideEvent.description_.currentLangTitle)
                        .textStyle(TypographyTokens.bodyMedium)
                }
                // Date
                HStack(alignment: .center, spacing: SpacingTokens.xxs) {
                    Assets.Icons.accessTime.swiftUIImage
                        .frame(width: 16, height: 16)

                    Text(sideEvent.timeText.currentLangTitle)
                        .textStyle(TypographyTokens.bodySmall)
                }
            }
            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)

            Spacer(minLength: 0)

            // Image
            if let imageLink = sideEvent.imageLink, let imageUrl = URL(string: imageLink) {
                Group {
                    CacheAsyncImage(url: imageUrl) { image in
                        image.resizable()
                    } placeholder: {
                        Color.gray
                    }
                }
                .frame(width: 88, height: 88)
                .scaledToFill()
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
                )
            }
        }
    }
}

private extension SideEvent {
    var icon: Image {
        switch self.markShape {
        case .favorite: return Assets.Icons.favorite.swiftUIImage
        case .circle: return
            Assets.Icons.circle
            .swiftUIImage
        case .square: return
            Assets.Icons.square
            .swiftUIImage
        default: return Assets.Icons.favorite.swiftUIImage
        }
    }

    var iconColor: Color {
        switch self.markColor {
        case .pink: return  AssetColors.MarkColor.pink.swiftUIColor
        case .blue: return AssetColors.MarkColor.blue.swiftUIColor
        case .orange: return AssetColors.MarkColor.orange.swiftUIColor
        case .red: return AssetColors.MarkColor.red.swiftUIColor
        case .purple: return AssetColors.MarkColor.purple.swiftUIColor
        default: return AssetColors.MarkColor.pink.swiftUIColor
        }
    }
}

#Preview {
    SideEventRow(
        sideEvent: SideEvent(
            title: MultiLangText(
                jaTitle: "アプリFiresideチャット(これは仮で後で消えます)",
                enTitle: "App Fireside chat(This is demo event and will be deleted later)"
            ),
            description: MultiLangText(
                jaTitle: "地下一階でDroidKaigiアプリの開発について、開発者と一緒に語りましょう！(これは仮で後で消えます)",
                enTitle: "(Basement)Let's talk about the development of the DroidKaigi app with the developers!(This is demo event and will be deleted later)"
            ),
            timeText: MultiLangText(
                jaTitle: "DAY1-DAY2 10:00-11:00",
                enTitle: "DAY1-DAY2 10:00-11:00"
            ),
            floorLevel: .basement,
            markColor: .pink,
            markShape: .favorite,
            link: "https://github.com/DroidKaigi/conference-app-2023",
            imageLink: "https://2023.droidkaigi.jp/static/12059b53c8c9813a85c1c44f8692a2c0/img_04.jpg"
        )
    )
}
