import Assets
import Model
import shared
import SwiftUI
import Theme

enum BookmarkRouting: Hashable {
    case session(TimetableItem)
}

struct BookmarkView<SessionView: View>: View {
    @ObservedObject var viewModel: BookmarkViewModel = .init()
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
                    ScrollView {
                        VStack {
                            TimetableListView(
                                timetableTimeGroupItems: timetableItems
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
                .font(Font.system(size: 22))
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurface.swiftUIColor)
            Spacer().frame(height: 8)
            Text("気になるセッションをブックマークに追加して集めてみましょう！")
                .font(Font.system(size: 14))
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
