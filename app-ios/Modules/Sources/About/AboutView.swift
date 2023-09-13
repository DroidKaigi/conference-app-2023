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
                        .resizable()
                        .scaledToFit()
                        .frame(maxWidth: .infinity)
                    Spacer().frame(height: 16)
                    Text(L10n.About.description)
                        .textStyle(TypographyTokens.bodyLarge)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Spacer().frame(height: 12)
                    VStack(alignment: .leading, spacing: SpacingTokens.s) {
                        InformationRow(
                            icon: Assets.Icons.schedule.swiftUIImage,
                            title: L10n.About.dateTitle,
                            content: L10n.About.dateDescription
                        )
                        InformationRow(
                            icon: Assets.Icons.locationOn.swiftUIImage,
                            title: L10n.About.placeTitle,
                            content: L10n.About.placeDescription,
                            action: .init(
                                label: L10n.About.viewMap,
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
                    .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.s))
                    Spacer().frame(height: 32)
                    SectionTitle(title: L10n.About.creditsTitle)
                    NavigationLink(value: AboutRouting.staffs) {
                        ListTile(
                            icon: Assets.Icons.sentimentVerySatisfied.swiftUIImage,
                            title: L10n.About.staff
                        )
                    }
                    Divider()
                    NavigationLink(value: AboutRouting.contributors) {
                        ListTile(
                            icon: Assets.Icons.diversity.swiftUIImage,
                            title: L10n.About.contributors
                        )
                    }
                    Divider()
                    NavigationLink(value: AboutRouting.sponsors) {
                        ListTile(
                            icon: Assets.Icons.apartment.swiftUIImage,
                            title: L10n.About.sponsors
                        )
                    }
                    Divider()
                    SectionTitle(title: L10n.About.othersTitle)
                    SafariLink(url: LocaleKt.getDefaultLocale() == .japan ? .codeOfConduct : .codeOfConductEn) {
                        ListTile(
                            icon: Assets.Icons.gavel.swiftUIImage,
                            title: L10n.About.codeOfConduct
                        )
                    }
                    Divider()
                    NavigationLink(value: AboutRouting.license) {
                        ListTile(
                            icon: Assets.Icons.fileCopy.swiftUIImage,
                            title: L10n.About.licenses
                        )
                    }
                    Divider()
                    SafariLink(url: LocaleKt.getDefaultLocale() == .japan ? .privacyPolicy : .privacyPolicyEn) {
                        ListTile(
                            icon: Assets.Icons.privacyTip.swiftUIImage,
                            title: L10n.About.privacyPolicy
                        )
                    }
                    Divider()
                    HStack(spacing: SpacingTokens.s) {
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

                    Text(L10n.About.appVersion)
                        .textStyle(TypographyTokens.labelLarge)
                    Spacer().frame(height: 8)
                    Text(Bundle.main.formattedVersion)
                        .textStyle(TypographyTokens.labelLarge)
                    Spacer().frame(height: 8)
                    Text(L10n.About.androidRobotDescription)
                        .textStyle(TypographyTokens.labelSmall)
                        .foregroundStyle(AssetColors.About.androidRobotDescription.swiftUIColor)
                        .multilineTextAlignment(.center)
                }
                .padding(16)
            }
            .background(AssetColors.Surface.surface.swiftUIColor)
            .navigationTitle(L10n.About.title)
            .navigationDestination(for: AboutRouting.self) { routing in
                switch routing {
                case .contributors:
                    contributorViewProvider(())
                case .staffs:
                    staffViewProvider(())
                case .sponsors:
                    sponsorViewProvider(())
                case .license:
                    LicenseListView(bundle: .myModule)
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
