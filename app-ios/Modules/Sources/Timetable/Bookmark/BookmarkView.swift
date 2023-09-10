import Assets
import Model
import shared
import SwiftUI
import Theme

enum BookmarkRouting: Hashable {
    case session(TimetableItem)
}

struct BookmarkView<SessionView: View>: View {
    @StateObject var viewModel: BookmarkViewModel = .init()
    private let sessionViewBuilder: ViewProvider<TimetableItem, SessionView>

    public init(sessionViewBuilder: @escaping ViewProvider<TimetableItem, SessionView>) {
        self.sessionViewBuilder = sessionViewBuilder
    }

    var body: some View {
        Group {
            switch viewModel.state.timeGroupTimetableItems {
            case .initial, .loading:
                ProgressView()
                    .task {
                        await viewModel.load()
                    }
            case .loaded(let timetableItems):
                if timetableItems.isEmpty {
                    BookmarkEmptyView()
                } else {
                    VStack(alignment: .leading, spacing: 0) {
                        HStack {
                            FilterLabel(
                                title: "全て",
                                isSelection: false,
                                isSelected: viewModel.state.selectedDay == nil,
                                onSelect: {
                                    viewModel.selectDay(day: nil)
                                },
                                onDeselect: nil
                            )
                            ForEach([DroidKaigi2023Day].fromKotlinArray(DroidKaigi2023Day.values())) { day in
                                FilterLabel(
                                    title: day.name,
                                    isSelection: false,
                                    isSelected: viewModel.state.selectedDay == day,
                                    onSelect: {
                                        viewModel.selectDay(day: day)
                                    },
                                    onDeselect: nil
                                )
                            }
                        }
                        ScrollView {
                            TimetableListView(
                                timetableTimeGroupItems: timetableItems,
                                searchWord: "",
                                onToggleBookmark: { id in
                                    viewModel.toggleBookmark(id)
                                }
                            )
                        }
                        .padding(.horizontal, 16)
                    }
                }
            case .failed:
                EmptyView()
            }
        }
        .background(AssetColors.Surface.surface.swiftUIColor)
        .navigationTitle("Bookmark")
    }
}

private struct BookmarkEmptyView: View {
    var body: some View {
        VStack(spacing: 0) {
            Assets.Icons.bookmark.swiftUIImage
                .resizable()
                .frame(width: 36, height: 36)
                .padding(24)
                .background(AssetColors.Secondary.secondaryContainer.swiftUIColor)
                .clipShape(RoundedRectangle(cornerRadius: 24))
            Spacer().frame(height: 24)
            Text("登録されたセッションがありません")
                .font(Font.custom(FontAssets.Montserrat.medium, size: 22))
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
            Spacer().frame(height: 8)
            Text("気になるセッションをブックマークに追加して集めてみましょう！")
                .font(Font.custom(FontAssets.Montserrat.medium, size: 14))
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    }
}

#Preview {
    BookmarkView(
        sessionViewBuilder: {_ in EmptyView()}
    )
}
