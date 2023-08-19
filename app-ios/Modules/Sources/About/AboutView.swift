import Assets
import Component
import Model
import SwiftUI
import Theme

enum AboutRouting: Hashable {
    case contributors
    case sponsors
}

public struct AboutView<ContributorView: View, SponsorView: View>: View {
    private let contributorViewProvider: ViewProvider<Void, ContributorView>
    private let sponsorViewProvider: ViewProvider<Void, SponsorView>

    public init(
        contributorViewProvider: @escaping ViewProvider<Void, ContributorView>,
        sponsorViewProvider: @escaping ViewProvider<Void, SponsorView>
    ) {
        self.contributorViewProvider = contributorViewProvider
        self.sponsorViewProvider = sponsorViewProvider
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
                    NavigationLink(value: AboutRouting.sponsors) {
                        ListTile(
                            icon: Assets.Icons.apartment.swiftUIImage,
                            title: "スポンサー"
                        )
                    }
                    Divider()
                    SectionTitle(title: "Others")
                    SafariLink(url: .codeOfConduct) {
                        ListTile(
                            icon: Assets.Icons.gavel.swiftUIImage,
                            title: "行動規範"
                        )
                    }
                    Divider()
                    ListTile(
                        icon: Assets.Icons.fileCopy.swiftUIImage,
                        title: "ライセンス"
                    )
                    Divider()
                    SafariLink(url: .privacyPolicy) {
                        ListTile(
                            icon: Assets.Icons.privacyTip.swiftUIImage,
                            title: "プライバシーポリシー"
                        )
                    }
                    Divider()
                    HStack(spacing: 12) {
                        SafariLink(url: .youtube) {
                            Assets.Icons.youtube.swiftUIImage
                        }
                        SafariLink(url: .twitter) {
                            Assets.Icons.twitter.swiftUIImage
                        }
                        SafariLink(url: .medium) {
                            Assets.Icons.medium.swiftUIImage
                        }
                    }
                    .padding(.vertical, 24)

                    Text("アプリバージョン")
                        .font(Font.system(size: 14, weight: .medium))
                    Spacer().frame(height: 8)
                    Text(Bundle.main.formattedVersion)
                        .font(Font.system(size: 14, weight: .medium))
                }
                .padding(.horizontal, 16)
            }
            .navigationTitle("About")
            .navigationDestination(for: AboutRouting.self) { routing in
                switch routing {
                case .contributors:
                    contributorViewProvider(())
                case .sponsors:
                    sponsorViewProvider(())
                }
            }
        }
    }
}

 #Preview {
     AboutView(
        contributorViewProvider: {_ in EmptyView()},
        sponsorViewProvider: {_ in EmptyView()}
     )
 }
