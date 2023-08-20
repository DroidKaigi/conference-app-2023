import Assets
import SwiftUI
import Theme

public struct FloorMapView: View {
    enum Floor {
        case first
        case basementFirst
    }

    @State private var floor: Floor = .first

    public init() {}

    public var body: some View {
        NavigationStack {
            ZStack {
                ScrollView {
                    switch floor {
                    case .first: firstFloorMapView()
                    case .basementFirst: basementFirstFloorMapView()
                    }
                }
                .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                VStack(spacing: 0) {
                    Spacer()
                    FloorMapFooterSegmentedButton(floor: $floor)
                        .padding(.bottom, 24)
                }
            }
            .navigationTitle("Floor Map")
        }
    }

    /// first floor map
    private func firstFloorMapView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("1F")
                .font(Font.system(size: 24, weight: .medium))
            Assets.Images.floor1F.swiftUIImage
        }
        .padding(.vertical, 24)
    }

    /// basement first floor map
    private func basementFirstFloorMapView() -> some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("B1F")
                .font(Font.system(size: 24, weight: .medium))
            Assets.Images.floorB1F.swiftUIImage
        }
        .padding(.vertical, 24)
    }
}

#Preview {
    FloorMapView()
}
