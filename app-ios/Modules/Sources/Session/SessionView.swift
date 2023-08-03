import Assets
import Model
import shared
import SwiftUI
import Theme

private let startDateFormatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.dateStyle = .medium
    formatter.timeStyle = .short
    return formatter
}()

public struct SessionView: View {
    @ObservedObject var viewModel: SessionViewModel
    @State var isDescriptionExpanded: Bool = false

    private var dateString: String {
        let startString = startDateFormatter.string(
            from: viewModel.timetableItem.startsAt.toDate()
        )
        let endTimeString = viewModel.timetableItem.endsTimeString
        let minutesString = viewModel.timetableItem.minutesString

        return "\(startString) ~ \(endTimeString) (\(minutesString))"
    }

    public init(timetableItem: TimetableItem) {
        self.viewModel = .init(timetableItem: timetableItem)
    }

    public var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Text(viewModel.timetableItem.title.currentLangTitle)
                    .font(Font.system(size: 24, weight: .medium))
                    .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 24)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(AssetColors.Surface.surfaceVariant.swiftUIColor)
                VStack(alignment: .leading) {
                    SessionInformationRow(
                        icon: Assets.Icons.schedule.swiftUIImage,
                        title: "日付",
                        content: dateString
                    )
                    SessionInformationRow(
                        icon: Assets.Icons.locationOn.swiftUIImage,
                        title: "場所",
                        content: viewModel.timetableItem.room.name.currentLangTitle
                    )
                    SessionInformationRow(
                        icon: Assets.Icons.language.swiftUIImage,
                        title: "対応言語",
                        content: viewModel.timetableItem.language.langOfSpeaker
                    )
                    SessionInformationRow(
                        icon: Assets.Icons.category.swiftUIImage,
                        title: "カテゴリ",
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
                        Text(session.description_)
                            .lineLimit(isDescriptionExpanded ? nil : 5)
                        if !isDescriptionExpanded {
                            Button {
                                isDescriptionExpanded = true
                            } label: {
                                Text("続きを読む")
                                    .font(Font.system(size: 14, weight: .medium))
                                    .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                                    .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
                                    .overlay {
                                        Capsule()
                                            .stroke(AssetColors.outline.swiftUIColor)
                                    }

                            }
                        }
                    }
                    .padding(.bottom, 24)
                    .padding(.horizontal, 16)

                    Divider()
                }

                VStack(alignment: .leading, spacing: 16) {
                    Text("対象者")
                        .font(Font.system(size: 14, weight: .semibold))
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    Text(viewModel.timetableItem.targetAudience)
                        .font(Font.system(size: 16))
                        .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                }
                .padding(.vertical, 24)
                .padding(.horizontal, 16)

                Divider()

                VStack(alignment: .leading, spacing: 16) {
                    Text("スピーカー")
                        .font(Font.system(size: 14, weight: .semibold))
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)

                    VStack(alignment: .leading, spacing: 8) {
                        ForEach(viewModel.timetableItem.speakers, id: \.id) { speaker in
                            HStack(spacing: 24) {
                                AsyncImage(url: URL(string: speaker.iconUrl)) { image in
                                    image.resizable()
                                } placeholder: {
                                    Color.gray
                                }
                                .frame(width: 40, height: 40)
                                .scaledToFill()
                                .clipShape(RoundedRectangle(cornerRadius: 12))
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(AssetColors.outline.swiftUIColor, lineWidth: 1)
                                )
                                VStack(alignment: .leading, spacing: 0) {
                                    Text(speaker.name)
                                        .font(Font.system(size: 16))
                                        .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
                                    Text(speaker.tagLine)
                                        .font(Font.system(size: 12))
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
                Button {
                    // TODO: Share
                } label: {
                    Assets.Icons.share.swiftUIImage
                }
            }
            ToolbarItem(placement: .bottomBar) {
                Button {
                    // TODO: Add to Calendar
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

    }
}

#Preview {
    SessionView(timetableItem: TimetableItem.Session.companion.fake())
}
