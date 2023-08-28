import Assets
import Component
import Model
import shared
import SwiftUI
import Theme

enum TimetableRouting: Hashable {
    case bookmark
    case search
    case session(TimetableItem)
}

public struct TimetableView<SessionView: View>: View {
    @ObservedObject var viewModel: TimetableViewModel = .init()
    private let sessionViewBuilder: ViewProvider<TimetableItem, SessionView>

    // Determines whether or not to collapse.
    private let verticalOffsetThreshold = -142.0

    // When offset value is exceed the threshold, TimetableDayHeader collapse with animation.
    @State private var shouldCollapse = false

    public init(sessionViewBuilder: @escaping ViewProvider<TimetableItem, SessionView>) {
        self.sessionViewBuilder = sessionViewBuilder
    }

    public var body: some View {
        switch viewModel.state.timeGroupTimetableItems {
        case .initial, .loading:
            ProgressView()
                .task {
                    await viewModel.load()
                }
        case .failed:
            EmptyView()
        case .loaded(let state):
            NavigationStack {
                ZStack(alignment: .topLeading) {
                    ZStack(alignment: .top) {
                        HStack(spacing: 0) {
                            Spacer()
                            Assets.Images.droidHotSpring.swiftUIImage
                        }
                        HStack(spacing: 0) {
                            VStack(alignment: .leading, spacing: 0) {
                                Text("DroidKaigi\n2023")
                                    .font(Font.system(size: 36))
                                Text("at Bellesalle Shibuya Garden")
                                    .font(Font.system(size: 12, weight: .semibold))
                            }
                            .padding(.horizontal, 16)
                            .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                            Spacer()
                        }
                    }
                    ScrollViewWithVerticalOffset(
                        onOffsetChange: { offset in
                            shouldCollapse = (offset < verticalOffsetThreshold)
                        },
                        content: {
                            Spacer().frame(height: 130)
                            LazyVStack(spacing: 0, pinnedViews: .sectionHeaders) {
                                Section(
                                    header: TimetableDayHeader(
                                        selectedDay: viewModel.state.selectedDay,
                                        shouldCollapse: shouldCollapse,
                                        onSelect: {
                                            viewModel.selectDay(day: $0)
                                        }
                                    )
                                    .frame(height: shouldCollapse ? 53 : 82)
                                    .animation(.easeInOut(duration: 0.08), value: shouldCollapse)
                                ) {
                                    TimetableListView(timetableTimeGroupItems: state, searchWord: "")
                                }
                            }
                            .background(AssetColors.Surface.surface.swiftUIColor)
                            .clipShape(RoundedCornersShape(corners: [.topLeft, .topRight], cornerRadius: 40))
                        }
                    )
                    .navigationDestination(for: TimetableRouting.self) { routing in
                        switch routing {
                        case .bookmark:
                            BookmarkView(sessionViewBuilder: sessionViewBuilder)
                        case .search:
                            SearchView(sessionViewBuilder: sessionViewBuilder)
                        case .session(let item):
                            sessionViewBuilder(item)
                        }
                    }
                }
                .background(AssetColors.Surface.surfaceVariant.swiftUIColor)
                .toolbarBackground(AssetColors.Surface.surfaceVariant.swiftUIColor, for: .navigationBar)
                .toolbar {
                    Group {
                        ToolbarItem(placement: .topBarLeading) {
                            Assets.Icons.droidkaigi.swiftUIImage
                        }
                        ToolbarItem {
                            NavigationLink(value: TimetableRouting.search) {
                                Assets.Icons.search.swiftUIImage
                            }
                        }
                        ToolbarItem {
                            NavigationLink(value: TimetableRouting.bookmark) {
                                Assets.Icons.bookmarks.swiftUIImage
                            }
                        }
                        ToolbarItem {
                            Assets.Icons.gridView.swiftUIImage
                        }
                    }
                }
            }
        }
    }
}

 #Preview {
     TimetableView<EmptyView> { _ in
         EmptyView()
     }
 }
