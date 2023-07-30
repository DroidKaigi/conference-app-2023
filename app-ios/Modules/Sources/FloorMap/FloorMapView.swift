import Assets
import SwiftUI
import Theme

public struct FloorMapView: View {
    public init() {}

    public var body: some View {
        NavigationStack {
            ScrollView {
                VStack {
                    VStack(alignment: .leading) {
                        Text("1F")
                            .font(Font.system(size: 24, weight: .medium))
                        Assets.Images.floor1F.swiftUIImage
                    }
                    .padding(.vertical, 24)

                    VStack(alignment: .leading) {
                        Text("B1F")
                            .font(Font.system(size: 24, weight: .medium))
                        Assets.Images.floorB1F.swiftUIImage
                    }
                    .padding(.vertical, 24)
                }
                .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
            }
            .navigationTitle("Floor Map")
        }
    }
}

 #Preview {
     FloorMapView()
 }
