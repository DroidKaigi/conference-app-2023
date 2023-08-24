import SwiftUI
import Assets
import Component
import shared
import Theme

struct SideEventRow: View {
    var sideEvent: SideEvent
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            VStack(alignment: .leading, spacing: 8) {
                VStack(alignment: .leading, spacing: 4) {
                    // Icon & Title
                    HStack(alignment: .top, spacing: 8) {
                        sideEvent.markIcon
                            .renderingMode(.template)
                            .frame(width: 16, height: 16)
                            .foregroundStyle(sideEvent.markIconColor)

                        Text(sideEvent.title.currentLangTitle)
                            .font(.system(size: 14, weight: .bold))
                    }
                    // Description
                    Text(sideEvent.description_.currentLangTitle)
                        .font(.system(size: 14, weight: .medium))
                }
                // Date
                HStack(alignment: .center, spacing: 4) {
                    Assets.Icons.accessTime.swiftUIImage
                        .frame(width: 16, height: 16)
                    
                    Text(sideEvent.timeText.currentLangTitle)
                        .font(.system(size: 12, weight: .medium))
                }
            }
            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
            
             Spacer(minLength: 0)
            
            // Image
            if let imageLink = sideEvent.link {
                CacheAsyncImage(url: URL(string: imageLink)!) { image in
                    image.resizable()
                } placeholder: {
                    Color.gray
                }
                .frame(width: 88, height: 88)
                .scaledToFill()
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
                )
            } else {
                Color.gray
                    .frame(width: 88, height: 88)
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
    var markIcon: Image {
        switch self.mark {
        case .mark1: return Assets.Icons.favorite.swiftUIImage
        default: return Assets.Icons.favorite.swiftUIImage
        }
    }
    
    var markIconColor: Color {
        switch self.mark {
        case .mark1: return AssetColors.pink.swiftUIColor
        default: return AssetColors.pink.swiftUIColor
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
            mark: .mark1,
            link: "https://github.com/DroidKaigi/conference-app-2023"
        )
    )
}
