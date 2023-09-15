import Assets
import Component
import Model
import shared
import SwiftUI
import Theme

public struct SessionView: View {
    @ObservedObject private(set) var viewModel: SessionViewModel
    @State private var isDescriptionExpanded: Bool = false
    @State private var canBeExpanded: Bool = false
    @State private var isAddingToCalendarConfirming: Bool = false
    @State private var presentingURL: IdentifiableURL?

    public init(timetableItem: TimetableItem) {
        self.viewModel = .init(timetableItem: timetableItem)
    }

    public var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Text(viewModel.timetableItem.title.currentLangTitle)
                    .textStyle(TypographyTokens.headlineMedium)
                    .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 24)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(AssetColors.Surface.surfaceVariant.swiftUIColor)
                VStack(alignment: .leading) {
                    InformationRow(
                        icon: Assets.Icons.schedule.swiftUIImage,
                        title: L10n.Session.date,
                        content: viewModel.timetableItem.formattedDateTimeString
                    )
                    InformationRow(
                        icon: Assets.Icons.locationOn.swiftUIImage,
                        title: L10n.Session.place,
                        content: viewModel.timetableItem.room.name.currentLangTitle
                    )
                    InformationRow(
                        icon: Assets.Icons.language.swiftUIImage,
                        title: L10n.Session.supportedLanguages,
                        content: viewModel.timetableItem.getSupportedLangString(isJapaneseLocale: LocaleKt.getDefaultLocale() == .japan)
                    )
                    InformationRow(
                        icon: Assets.Icons.category.swiftUIImage,
                        title: L10n.Session.category,
                        content: viewModel.timetableItem.category.title.currentLangTitle
                    )
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 20)
                .padding(.horizontal, 16)
                .background(
                    AssetColors.Surface.surfaceContainerLow.swiftUIColor
                )
                .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.s))
                .padding(.vertical, 20)
                .padding(.horizontal, 16)

                if let session = viewModel.timetableItem as? TimetableItem.Session {
                    SessionDescriptionView(content: session.description_.currentLangTitle)
                        .padding(.bottom, 24)
                        .padding(.horizontal, 16)

                    Divider()

                    VStack(alignment: .leading, spacing: SpacingTokens.m) {
                        Text(L10n.Session.targetAudience)
                            .textStyle(TypographyTokens.titleLarge)
                            .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                        Text(viewModel.timetableItem.targetAudience)
                            .textStyle(TypographyTokens.bodyLarge)
                            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                    }
                    .padding(.vertical, 24)
                    .padding(.horizontal, 16)

                    Divider()

                    VStack(alignment: .leading, spacing: SpacingTokens.m) {
                        Text(L10n.Session.speakers)
                            .textStyle(TypographyTokens.titleLarge)
                            .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                        VStack(alignment: .leading, spacing: SpacingTokens.xs) {
                            ForEach(viewModel.timetableItem.speakers, id: \.id) { speaker in
                                HStack(spacing: SpacingTokens.xl) {
                                    CacheAsyncImage(url: URL(string: speaker.iconUrl)) { image in
                                        image.resizable()
                                    } placeholder: {
                                        Color.gray
                                    }
                                    .frame(width: 40, height: 40)
                                    .scaledToFill()
                                    .clipShape(RoundedRectangle(cornerRadius: RadiusTokens.s))
                                    .overlay(
                                        RoundedRectangle(cornerRadius: RadiusTokens.s)
                                            .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
                                    )
                                    VStack(alignment: .leading, spacing: 0) {
                                        Text(speaker.name)
                                            .textStyle(TypographyTokens.bodyLarge)
                                            .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                                        Text(speaker.tagLine)
                                            .textStyle(TypographyTokens.bodySmall)
                                            .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                                    }
                                }
                            }
                        }
                    }
                    .padding(.vertical, 24)
                    .padding(.horizontal, 16)
                } else if let special = viewModel.timetableItem as? TimetableItem.Special {
                    SessionDescriptionView(content: special.description_.currentLangTitle)
                        .padding(.bottom, 24)
                        .padding(.horizontal, 16)
                }
            }
            .background(AssetColors.Surface.surface.swiftUIColor)
//            .toolbar {
//                ToolbarItem(placement: .topBarLeading) {
//                    Text(timetableItem.title.currentLangTitle)
//                        .font(Font.system(size: 24, weight: .medium))
//                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
//                }
//            }
//            .navigationBarTitleDisplayMode(.inline)
        }
        .background(AssetColors.Surface.surfaceVariant.swiftUIColor)
        .toolbarBackground(
            AssetColors.Surface.surfaceVariant.swiftUIColor,
            for: .navigationBar
        )
        .toolbar {
            ToolbarItem(placement: .bottomBar) {
                if let url = URL(string: viewModel.timetableItem.url) {
                    ShareLink(item: url,
                              label: { Assets.Icons.share.swiftUIImage })
                }
            }
            ToolbarItem(placement: .bottomBar) {
                Button {
                    Task {
                        if await viewModel.requestEventAccessIfNeeded() {
                            isAddingToCalendarConfirming.toggle()
                        }
                    }
                } label: {
                    Assets.Icons.calendarAddOn.swiftUIImage
                }
            }
            ToolbarItem(placement: .bottomBar) {
                Spacer()
            }
            ToolbarItem(placement: .bottomBar) {
                Button {
                    // TODO: favorite
                } label: {
                    Assets.Icons.bookmarkBorder.swiftUIImage
                }
            }
        }
        .confirmationDialog("", isPresented: $isAddingToCalendarConfirming) {
            Button(L10n.Session.addToCalendar) {
                viewModel.addToCalendar()
            }

            Button(L10n.Session.cancel, role: .cancel) {
                isAddingToCalendarConfirming = false
            }
        }
        .sheet(item: $presentingURL) { url in
            if let url = url.id {
                SafariView(url: url)
                    .ignoresSafeArea()
            }
        }
        .environment(\.openURL, OpenURLAction { url in
            presentingURL = IdentifiableURL(url)
            return .handled
        })
    }
}

#Preview {
    SessionView(timetableItem: TimetableItem.Session.companion.fake())
}
