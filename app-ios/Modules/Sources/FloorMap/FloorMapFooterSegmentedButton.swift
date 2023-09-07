import Assets
import shared
import SwiftUI
import Theme

/// Floor Map Footer Segmented Button
/// [Figma](https://www.figma.com/file/MbElhCEnjqnuodmvwabh9K/DroidKaigi-2023-App-UI?type=design&node-id=56145-70041&mode=dev)
struct FloorMapFooterSegmentedButton: View {
    @Binding private var floor: FloorLevel
    private let width: CGFloat = 104
    private let height: CGFloat = 40

    init(floor: Binding<FloorLevel>) {
        _floor = floor
    }

    var body: some View {
        HStack(spacing: 0) {
            // 1F Button
            Button {
                floor = .ground
            } label: {
                HalfCapsule(
                    foregroundColor: floor == .ground ? AssetColors.Secondary.secondaryContainer.swiftUIColor : AssetColors.floorButtonBg.swiftUIColor
                )
                .rotationEffect(.degrees(180))
                .overlay(alignment: .center) {
                    HStack(spacing: 8) {
                        if floor == .ground {
                            Assets.Icons.checkmark.swiftUIImage
                        }
                        Text("1F")
                            .font(Font.custom(FontAssets.Montserrat.medium, size: 14))
                    }
                    .foregroundStyle(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
                }
            }

            // B1F Button
            Button {
                floor = .basement
            } label: {
                HalfCapsule(
                    foregroundColor: floor == .basement ? AssetColors.Secondary.secondaryContainer.swiftUIColor : AssetColors.floorButtonBg.swiftUIColor
                )
                .overlay(alignment: .center) {
                    HStack(spacing: 8) {
                        if floor == .basement {
                            Assets.Icons.checkmark.swiftUIImage
                        }
                        Text("B1F")
                            .font(Font.custom(FontAssets.Montserrat.medium, size: 14))
                    }
                    .foregroundStyle(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
                }
            }
        }
    }

    /// Half a capsule
    private struct HalfCapsule: View {
        let foregroundColor: Color
        private let buttonWidth: CGFloat = 104
        private let buttonHeight: CGFloat = 40
        private let borderLineWidth: CGFloat = 1

        var body: some View {
            ZStack(alignment: .leading) {
                Capsule()
                    .frame(width: buttonWidth, height: buttonHeight)
                    .overlay {
                        Capsule()
                            .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: borderLineWidth)
                            .frame(width: buttonWidth - borderLineWidth, height: buttonHeight - borderLineWidth)
                    }
                Rectangle()
                    .frame(width: buttonWidth - buttonHeight / 2, height: buttonHeight)
                    .overlay(alignment: .top) {
                        Rectangle()
                            .frame(width: buttonWidth - buttonHeight / 2, height: borderLineWidth)
                            .foregroundStyle(AssetColors.Outline.outline.swiftUIColor)
                    }
                    .overlay(alignment: .bottom) {
                        Rectangle()
                            .frame(width: buttonWidth - buttonHeight / 2, height: borderLineWidth)
                            .foregroundStyle(AssetColors.Outline.outline.swiftUIColor)
                    }
                    .overlay(alignment: .leading) {
                        // Half of borderLineWidth because of boundary line
                        Rectangle()
                            .frame(width: borderLineWidth / 2, height: buttonHeight)
                            .foregroundStyle(AssetColors.Outline.outline.swiftUIColor)
                    }
            }
            .foregroundStyle(foregroundColor)
            .compositingGroup()
        }
    }
}

#Preview {
    @State var floor: FloorLevel = .ground
    return FloorMapFooterSegmentedButton(floor: $floor)
}
