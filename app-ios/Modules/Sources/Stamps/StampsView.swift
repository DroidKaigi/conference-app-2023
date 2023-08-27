import Assets
import SwiftUI
import Theme

public struct StampsView: View {
    public init() {}
    public var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 24) {
                    Text("会場の各部屋に設置されたNFCタグにスマホをかざしてバッジを集めてみましょう。イベント最終日には、全てのバッジを集めた方にDroidKaigiのオリジナルグッズをプレゼントします。")
                        .font(Font.system(size: 16))
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    LazyVGrid(columns: [.init(), .init()]) {
                        Assets.Images.stampArcticFox.swiftUIImage
                        Assets.Images.stampBumblebee.swiftUIImage
                        Assets.Images.stampChipmunk.swiftUIImage
                        Assets.Images.stampDolphin.swiftUIImage
                        // TODO: Find good render way
                        Assets.Images.stampElectricEel.swiftUIImage
                    }
                }
                .padding(16)
            }
            .background(AssetColors.Surface.surface.swiftUIColor)
            .navigationTitle("Stamps")
        }
    }
}

// #Preview {
//     StampsView()
// }
