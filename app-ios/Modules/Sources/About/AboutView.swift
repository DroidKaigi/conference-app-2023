import Assets
import Component
import Model
import SwiftUI
import Theme

enum AboutRouting: Hashable {
    case contributors
}

public struct AboutView<ContributorView: View>: View {
    private let contributorViewProvider: ViewProvider<Void, ContributorView>

    public init(contributorViewProvider: @escaping ViewProvider<Void, ContributorView>) {
        self.contributorViewProvider = contributorViewProvider
    }

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
                        InformationRow(
                            icon: Assets.Icons.info.swiftUIImage,
                            title: "日時",
                            content: "2023.09.14(木) 〜 16(土) 3日間"
                        )
                        InformationRow(
                            icon: Assets.Icons.info.swiftUIImage,
                            title: "場所",
                            content: "ベルサール渋谷ガーデン",
                            action: .init(
                                label: "地図を見る",
                                action: {
                                    // TODO: Open map
                                }
                            )
                        )
                    }
                    .padding(.vertical, 20)
                    .padding(.horizontal, 16)
                    .background(AssetColors.Surface.surfaceContainerLow.swiftUIColor)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    Spacer().frame(height: 32)
                    SectionTitle(title: "Credits")
                    ListTile(
                        icon: Assets.Icons.sentimentVerySatisfied.swiftUIImage,
                        title: "スタッフ"
                    )
                    Divider()
                    NavigationLink(value: AboutRouting.contributors) {
                        ListTile(
                            icon: Assets.Icons.diversity.swiftUIImage,
                            title: "コントリビューター"
                        )
                    }
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
            .navigationDestination(for: AboutRouting.self) { routing in
                switch routing {
                case .contributors:
                    contributorViewProvider(())
                }
            }
        }
    }
}

 #Preview {
     AboutView(
        contributorViewProvider: {_ in EmptyView()}
     )
 }
