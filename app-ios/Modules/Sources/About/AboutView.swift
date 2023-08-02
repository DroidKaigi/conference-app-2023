import Assets
import SwiftUI
import Theme

public struct AboutView: View {
    public init() {}
    public var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 0) {
                    Assets.Images.aboutKeyVisual.swiftUIImage
                    Spacer().frame(height: 16)
                    Text("DroidKaigiはAndroid技術情報の共有とコミュニケーションを目的に開催されるエンジニアが主役のAndroidカンファレンスです。")
                        .font(Font.system(size: 16))
                    Spacer().frame(height: 12)
                    VStack(alignment: .leading, spacing: 12) {
                        HStack(spacing: 0) {
                            Assets.Icons.info.swiftUIImage
                            Spacer().frame(width: 8)
                            Text("日時")
                                .font(Font.system(size: 14, weight: .semibold))
                            Spacer().frame(width: 12)
                            Text("2023.09.14(木) 〜 16(土) 3日間")
                                .font(Font.system(size: 14, weight: .semibold))
                        }
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundStyle(
                            AssetColors.Surface.onSurfaceVariant.swiftUIColor
                        )
                        HStack(spacing: 0) {
                            Assets.Icons.info.swiftUIImage
                            Spacer().frame(width: 8)
                            Text("場所")
                                .font(Font.system(size: 14, weight: .semibold))
                            Spacer().frame(width: 12)
                            Text("ベルサール渋谷ガーデン")
                                .font(Font.system(size: 14, weight: .semibold))
                            Spacer().frame(width: 8)
                            Button {
                                // TODO: Open map
                            } label: {
                                Text("地図を見る")
                                    .font(Font.system(size: 14, weight: .semibold))
                                    .underline()
                                    .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                            }
                        }
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .foregroundStyle(
                            AssetColors.Surface.onSurfaceVariant.swiftUIColor
                        )
                    }
                    .padding(.vertical, 20)
                    .padding(.horizontal, 16)
                    // TODO: Use SurfaceContainerLow
                    .background(AssetColors.Surface.surfaceContainer.swiftUIColor)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    Spacer().frame(height: 32)
                    SectionTitle(title: "Credits")
                    ListTile(
                        icon: Assets.Icons.sentimentVerySatisfied.swiftUIImage,
                        title: "スタッフ"
                    )
                    Divider()
                    ListTile(
                        icon: Assets.Icons.diversity.swiftUIImage,
                        title: "コントリビューター"
                    )
                    Divider()
                    ListTile(
                        icon: Assets.Icons.apartment.swiftUIImage,
                        title: "スポンサー"
                    )
                    Divider()
                    SectionTitle(title: "Others")
                    ListTile(
                        icon: Assets.Icons.gavel.swiftUIImage,
                        title: "行動規範"
                    )
                    Divider()
                    ListTile(
                        icon: Assets.Icons.fileCopy.swiftUIImage,
                        title: "ライセンス"
                    )
                    Divider()
                    ListTile(
                        icon: Assets.Icons.privacyTip.swiftUIImage,
                        title: "プライバシーポリシー"
                    )
                    Divider()
                    HStack(spacing: 12) {
                        Button {
                            // TODO: open youtube
                        } label: {
                            Assets.Icons.youtube.swiftUIImage
                        }
                        Button {
                            // TODO: open Twitter
                        } label: {
                            Assets.Icons.twitter.swiftUIImage
                        }
                        Button {
                            // TODO: open medium
                        } label: {
                            Assets.Icons.medium.swiftUIImage
                        }
                    }
                    .padding(.vertical, 24)

                    Text("アプリバージョン")
                        .font(Font.system(size: 14, weight: .medium))
                    Spacer().frame(height: 8)
                    Text("1.2")
                        .font(Font.system(size: 14, weight: .medium))
                }
                .padding(.horizontal, 16)
            }
            .navigationTitle("About")
        }
    }
}

 #Preview {
     AboutView()
 }
