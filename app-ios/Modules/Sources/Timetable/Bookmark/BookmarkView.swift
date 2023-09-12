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
                VStack(alignment: .leading, spacing: 0) {
                    HStack {
                        FilterLabel(
                            title: L10n.Bookmark.allDay,
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
                    .padding(.horizontal, 16)
                    if timetableItems.map({ $0.items }).flatMap({ $0 }).isEmpty {
                        BookmarkEmptyView()
                    } else {
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
        .navigationTitle(L10n.Bookmark.title)
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
            Text(L10n.Bookmark.bookmarksNotFound)
                .textStyle(TypographyTokens.titleLarge)
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
            Spacer().frame(height: 8)
            Text(L10n.Bookmark.bookmarksNotFoundNote)
                .textStyle(TypographyTokens.bodyMedium)
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
