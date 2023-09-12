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
                    .font(Font.custom(FontAssets.Montserrat.medium, size: 24))
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
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .padding(.vertical, 20)
                .padding(.horizontal, 16)

                if let session = viewModel.timetableItem as? TimetableItem.Session {
                    VStack(alignment: .leading, spacing: 16) {
                        Text(.init(session.description_.currentLangTitle))
                            .textSelection(.enabled)
                            .lineLimit(isDescriptionExpanded ? nil : 5)
                            .background {
                                ViewThatFits(in: .vertical) {
                                    Text(session.description_.currentLangTitle)
                                        .hidden()
                                    // Just for receiving onAppear event if the description exceeds its line limit
                                    Color.clear
                                        .onAppear {
                                            canBeExpanded = true
                                        }
                                }
                            }
                        if canBeExpanded {
                            Button {
                                isDescriptionExpanded = true
                                canBeExpanded = false
                            } label: {
                                Text(L10n.Session.readMore)
                                    .font(Font.custom(FontAssets.Montserrat.medium, size: 14))
                                    .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                                    .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
                                    .overlay {
                                        Capsule()
                                            .stroke(AssetColors.Outline.outline.swiftUIColor)
                                    }
                            }
                        }
                    }
                    .padding(.bottom, 24)
                    .padding(.horizontal, 16)

                    Divider()
                }

                VStack(alignment: .leading, spacing: 16) {
                    Text(L10n.Session.targetAudience)
                        .font(Font.custom(FontAssets.Montserrat.semiBold, size: 14))
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    Text(viewModel.timetableItem.targetAudience)
                        .font(Font.custom(FontAssets.Montserrat.medium, size: 16))
                        .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)

                Divider()

                VStack(alignment: .leading, spacing: 16) {
                    Text(L10n.Session.speakers)
                        .font(Font.custom(FontAssets.Montserrat.semiBold, size: 14))
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)

                    VStack(alignment: .leading, spacing: 8) {
                        ForEach(viewModel.timetableItem.speakers, id: \.id) { speaker in
                            HStack(spacing: 24) {
                                CacheAsyncImage(url: URL(string: speaker.iconUrl)) { image in
                                    image.resizable()
                                } placeholder: {
                                    Color.gray
                                }
                                .frame(width: 40, height: 40)
                                .scaledToFill()
                                .clipShape(RoundedRectangle(cornerRadius: 12))
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(AssetColors.Outline.outline.swiftUIColor, lineWidth: 1)
                                )
                                VStack(alignment: .leading, spacing: 0) {
                                    Text(speaker.name)
                                        .font(Font.custom(FontAssets.Montserrat.medium, size: 16))
                                        .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                                    Text(speaker.tagLine)
                                        .font(Font.custom(FontAssets.Montserrat.medium, size: 12))
                                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                                }
                            }
                        }
                    }
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)
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
