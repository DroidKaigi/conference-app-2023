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
    @Environment(\.colorScheme) var colorScheme
    @ObservedObject var viewModel: TimetableViewModel = .init()
    private let sessionViewBuilder: ViewProvider<TimetableItem, SessionView>
    let gradient = Gradient(stops: [
        .init(color: AssetColors.Surface.surfaceGradientTOP.swiftUIColor, location: 0.0),
        .init(color: AssetColors.Surface.surfaceGradientBottom.swiftUIColor, location: 0.15)
    ])

    // Determines whether or not to collapse.
    private let verticalOffsetThreshold = -142.0

    // When offset value is exceed the threshold, TimetableDayHeader collapse with animation.
    @State private var shouldCollapse = false
    @State private var shouldTickBookmark = false

    public init(sessionViewBuilder: @escaping ViewProvider<TimetableItem, SessionView>) {
        self.sessionViewBuilder = sessionViewBuilder
    }

    public var body: some View {
        switch viewModel.state.loadedState {
        case .initial, .loading:
            ProgressView()
                .onAppear {
                    viewModel.load()
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
                                    .font(Font.custom(FontAssets.Montserrat.medium, size: 36))
                                Text("at Bellesalle Shibuya Garden")
                                    .font(Font.custom(FontAssets.Montserrat.semiBold, size: 12))
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
                                    TimeTableTopView(timetableTimeGroupItems: state.timeGroupTimetableItems)
                                        .environmentObject(viewModel)
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
                .background(LinearGradient(gradient: gradient, startPoint: .top, endPoint: .bottom))
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
                            .buttonStyle(.plain)
                        }
                        ToolbarItem {
                            NavigationLink(value: TimetableRouting.bookmark) {
                                ZStack {
                                    Assets.Icons.bookmarks.swiftUIImage
                                    if shouldTickBookmark {
                                        (colorScheme == .light
                                            ? LottieAssets.addToBookmarkLightJson
                                            : LottieAssets.addToBookmarkDarkJson)
                                        .swiftUIAnimation(loopMode: .playOnce) { _ in
                                            shouldTickBookmark = false
                                        }
                                    }
                                }
                            }
                            .buttonStyle(.plain)
                        }
                        ToolbarItem {
                            Assets.Icons.gridView.swiftUIImage
                        }
                    }
                }
                .onChangeWithPrevious(of: state.bookmarks) { previous, newValue in
                    if (previous?.count ?? 0) < newValue.count {
                        shouldTickBookmark = true
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
