import Assets
import Theme
import SwiftUI

/// Floor Map Footer Segmented Button
/// [Figma](https://www.figma.com/file/MbElhCEnjqnuodmvwabh9K/DroidKaigi-2023-App-UI?type=design&node-id=56145-70041&mode=dev)
struct FloorMapFooterSegmentedButton: View {
    @Binding private var floor: FloorMapView.Floor
    private let width: CGFloat = 104
    private let height: CGFloat = 40

    init(floor: Binding<FloorMapView.Floor>) {
        _floor = floor
    }

    var body: some View {
        HStack(spacing: 0) {
            // 1F Button
            Button {
                floor = .first
            } label: {
                HalfCapsule(
                    foregroundColor: floor == .first ? AssetColors.Secondary.secondaryContainer.swiftUIColor : AssetColors.floorButtonBg.swiftUIColor
                )
                .rotationEffect(.degrees(180))
                .overlay(alignment: .center) {
                    HStack(spacing: 8) {
                        if floor == .first {
                            Assets.Icons.checkmark.swiftUIImage
                        }
                        Text("1F")
                            .font(.system(size: 14, weight: .medium))
                    }
                    .foregroundStyle(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
                }
            }

            // B1F Button
            Button {
                floor = .basementFirst
            } label: {
                HalfCapsule(
                    foregroundColor: floor == .basementFirst ? AssetColors.Secondary.secondaryContainer.swiftUIColor : AssetColors.floorButtonBg.swiftUIColor
                )
                .overlay(alignment: .center) {
                    HStack(spacing: 8) {
                        if floor == .basementFirst {
                            Assets.Icons.checkmark.swiftUIImage
                        }
                        Text("B1F")
                            .font(.system(size: 14, weight: .medium))
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
                            .stroke(AssetColors.outline.swiftUIColor, lineWidth: borderLineWidth)
                            .frame(width: buttonWidth - borderLineWidth, height: buttonHeight - borderLineWidth)
                    }
                Rectangle()
                    .frame(width: buttonWidth - buttonHeight / 2, height: buttonHeight)
                    .overlay(alignment: .top) {
                        Rectangle()
                            .frame(width: buttonWidth - buttonHeight / 2, height: borderLineWidth)
                            .foregroundStyle(AssetColors.outline.swiftUIColor)
                    }
                    .overlay(alignment: .bottom) {
                        Rectangle()
                            .frame(width: buttonWidth - buttonHeight / 2, height: borderLineWidth)
                            .foregroundStyle(AssetColors.outline.swiftUIColor)
                    }
                    .overlay(alignment: .leading) {
                        // Half of borderLineWidth because of boundary line
                        Rectangle()
                            .frame(width: borderLineWidth / 2, height: buttonHeight)
                            .foregroundStyle(AssetColors.outline.swiftUIColor)
                    }
            }
            .foregroundStyle(foregroundColor)
            .compositingGroup()
        }
    }
}

#Preview {
    @State var floor: FloorMapView.Floor = .first
    return FloorMapFooterSegmentedButton(floor: $floor)
}
