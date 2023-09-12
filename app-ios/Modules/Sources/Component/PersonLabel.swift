import SwiftUI
import Theme

public struct PersonLabel: View {
    let name: String
    let iconUrlString: String
    let notes: String?

    public init(name: String, iconUrlString: String, notes: String? = nil) {
        self.name = name
        self.iconUrlString = iconUrlString
        self.notes = notes
    }

    public var body: some View {
        HStack(alignment: .center, spacing: SpacingTokens.xl) {
            HStack(spacing: -8) {
                CacheAsyncImage(url: URL(string: iconUrlString)) { image in
                    image.resizable()
                } placeholder: {
                    Color.gray
                }
                .frame(width: 60, height: 60)
                .scaledToFill()
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
                )
            }

            VStack(alignment: .leading) {
                Text(name)
                    .textStyle(TypographyTokens.bodyLarge)
                    .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                    .lineLimit(2)
                if let notes = notes {
                    Text(notes)
                        .textStyle(TypographyTokens.bodySmall)
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                }
            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

#Preview {
    PersonLabel(name: "ry-itto", iconUrlString: "https://github.com/ry-itto.png", notes: "iOS lead")
}
