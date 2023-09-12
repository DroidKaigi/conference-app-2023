import Assets
import SwiftUI
import Theme

struct FilterLabel: View {
    let title: String
    let isSelection: Bool
    let isSelected: Bool
    let onSelect: (() -> Void)?
    let onDeselect: (() -> Void)?

    var body: some View {
        Button {
            if isSelected {
                onDeselect?()
            } else {
                onSelect?()
            }
        } label: {
            HStack {
                if isSelected {
                    Assets.Icons.check.swiftUIImage
                }
                Text(title)
                    .font(Font.custom(FontAssets.Montserrat.medium, size: 14))
                if isSelection {
                    Assets.Icons.arrowDropDown.swiftUIImage
                }
            }
            .padding(.horizontal, 8)
            .padding(.vertical, 6)
            .background {
                if isSelected {
                    AssetColors.Secondary.secondaryContainer.swiftUIColor
                } else {
                    AssetColors.Surface.surface.swiftUIColor
                }
            }
            .foregroundStyle(
                isSelected
                ? AssetColors.Secondary.onSecondaryContainer.swiftUIColor
                : AssetColors.Surface.onSurface.swiftUIColor
            )
            .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.xxs))
            .overlay {
                if !isSelected {
                    RoundedRectangle(cornerRadius: RadiusTokens.xxs)
                        .inset(by: 0.5)
                        .stroke(
                            AssetColors.Outline.outline.swiftUIColor,
                            lineWidth: 1
                        )
                } else {
                    EmptyView()
                }
            }
        }
        .disabled(onSelect == nil && onDeselect == nil)
    }
}

#Preview {
    FilterLabel(title: "Filter", isSelection: false, isSelected: false, onSelect: nil, onDeselect: nil)
}
