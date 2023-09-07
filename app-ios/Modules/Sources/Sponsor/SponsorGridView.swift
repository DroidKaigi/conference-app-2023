import Component
import Model
import shared
import SwiftUI
import Theme

struct SponsorGridView: View {

    let title: String
    let sponsors: [Sponsor]
    let columns: Int
    var action: (Sponsor) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(title)
                .font(Font.custom(FontAssets.Montserrat.medium, size: 22))
                .fontWeight(.medium)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
            LazyVGrid(columns: Array(repeating: GridItem(spacing: 16), count: columns), spacing: 16) {
                ForEach(sponsors) { sponsor in
                    SponsorItemView(sponsor: sponsor) {
                        action(sponsor)
                    }
                }
            }
        }
    }
}

struct SponsorItemView: View {

    let sponsor: Sponsor
    let action: () -> Void

    var body: some View {
        ZStack {
            AssetColors.Custom.white.swiftUIColor
            Button {
                action()
            } label: {
                CacheAsyncImage(url: URL(string: sponsor.logo)) { image in
                    image
                        .resizable()
                        .scaledToFit()
                        .frame(minWidth: 0, maxWidth: .infinity)
                        .frame(minHeight: 0, maxHeight: .infinity)
                } placeholder: {
                    AssetColors.Custom.white.swiftUIColor
                }
            }
        }
        .frame(height: sponsor.plan.itemHeight)
        .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
    }
}

private extension Plan {
    var itemHeight: CGFloat {
        switch self {
        case .platinum:
            return 112
        case .gold:
            return 112
        case .supporter:
            return 72
        default:
            return 72
        }
    }
}
