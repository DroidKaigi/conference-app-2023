import Assets
import Component
import LicenseList
import Model
import shared
import SwiftUI
import Theme

enum AboutRouting: Hashable {
    case contributors
    case license
    case sponsors
    case staffs
}

public struct AboutView<ContributorView: View, StaffView: View, SponsorView: View>: View {
    private let contributorViewProvider: ViewProvider<Void, ContributorView>
    private let staffViewProvider: ViewProvider<Void, StaffView>
    private let sponsorViewProvider: ViewProvider<Void, SponsorView>

    @Environment(\.openURL) private var openURL

    public init(
        contributorViewProvider: @escaping ViewProvider<Void, ContributorView>,
        staffViewProvider: @escaping ViewProvider<Void, StaffView>,
        sponsorViewProvider: @escaping ViewProvider<Void, SponsorView>
    ) {
        self.contributorViewProvider = contributorViewProvider
        self.staffViewProvider = staffViewProvider
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
                            icon: Assets.Icons.schedule.swiftUIImage,
                            title: "日時",
                            content: "2023.09.14(木) 〜 16(土) 3日間"
                        )
                        InformationRow(
                            icon: Assets.Icons.locationOn.swiftUIImage,
                            title: "場所",
                            content: "ベルサール渋谷ガーデン",
                            action: .init(
                                label: "地図を見る",
                                action: {
                                    if let googleMapUrl = URL(string: "https://goo.gl/maps/vv9sE19JvRjYKtSP9") {
                                        openURL(googleMapUrl)
                                    }
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
                    NavigationLink(value: AboutRouting.staffs) {
                        ListTile(
                            icon: Assets.Icons.sentimentVerySatisfied.swiftUIImage,
                            title: "スタッフ"
                        )
                    }
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
                    SafariLink(url: LocaleKt.getDefaultLocale() == .japan ? .codeOfConduct : .codeOfConductEn) {
                        ListTile(
                            icon: Assets.Icons.gavel.swiftUIImage,
                            title: "行動規範"
                        )
                    }
                    Divider()
                    NavigationLink(value: AboutRouting.license) {
                        ListTile(
                            icon: Assets.Icons.fileCopy.swiftUIImage,
                            title: "ライセンス"
                        )
                    }
                    Divider()
                    SafariLink(url: LocaleKt.getDefaultLocale() == .japan ? .privacyPolicy : .privacyPolicyEn) {
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
                .padding(16)
            }
            .navigationTitle("About")
            .navigationDestination(for: AboutRouting.self) { routing in
                switch routing {
                case .contributors:
                    contributorViewProvider(())
                case .staffs:
                    staffViewProvider(())
                case .sponsors:
                    sponsorViewProvider(())
                case .license:
                    LicenseListView(bundle: .module)
                }
            }
        }
    }
}

#Preview {
    AboutView(
        contributorViewProvider: {_ in EmptyView()},
        staffViewProvider: {_ in EmptyView()},
        sponsorViewProvider: {_ in EmptyView()}
    )
}
