import Assets
import Model
import shared
import SwiftUI
import Theme

enum BookmarkRouting: Hashable {
    case session(TimetableItem)
}

struct BookmarkView<SessionView: View>: View {
    @ObservedObject var viewModel: TimetableViewModel = .init()
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
                    TimetableListView(
                        timetableTimeGroupItems: timetableItems
                    )
                }
            case .failed:
                EmptyView()
            }
        }
        .navigationTitle("Bookmark")
    }
}

private struct BookmarkEmptyView: View {
    var body: some View {
        VStack(spacing: 0) {
            Assets.Icons.bookmark.swiftUIImage
                .padding(24)
                .background(AssetColors.Secondary.secondaryContainer.swiftUIColor)
                .clipShape(RoundedRectangle(cornerRadius: 24))
            Spacer().frame(height: 24)
            Text("登録されたセッションがありません")
            Spacer().frame(height: 8)
            Text("気になるセッションをブックマークに追加して集めてみましょう！")
        }
    }
}

#Preview {
    BookmarkView(
        sessionViewBuilder: {_ in EmptyView()}
    )
}
