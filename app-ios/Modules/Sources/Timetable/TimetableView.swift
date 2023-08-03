import Assets
import Model
import shared
import SwiftUI
import Theme

public struct TimetableView<SessionView: View>: View {
    @ObservedObject var viewModel: TimetableViewModel = .init()
    private let sessionViewBuilder: ViewProvider<TimetableItem, SessionView>

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
                    HStack(alignment: .top) {
                        VStack {
                            Text("DroidKaigi\n2023")
                                .font(Font.system(size: 36))
                            Text("at Bellesalle Shibuya Garden")
                                .font(Font.system(size: 12, weight: .semibold))
                        }
                        .padding(.horizontal, 16)
                        .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                        Assets.Images.droidHotSpring.swiftUIImage
                    }
                    ScrollView(.vertical) {
                        Spacer().frame(height: 130)
                        LazyVStack(spacing: 0, pinnedViews: .sectionHeaders) {
                            Section(
                                header: TimetableDayHeader(
                                    selectedDay: viewModel.state.selectedDay
                                ) {
                                        viewModel.selectDay(day: $0)
                                }
                            ) {
                                TimetableListView(timetableTimeGroupItems: state)
                            }
                        }
                        .background(AssetColors.Surface.surface.swiftUIColor)
                    }
                    .navigationDestination(for: TimetableItem.self) { item in
                        sessionViewBuilder(item)
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
                            Assets.Icons.search.swiftUIImage
                        }
                        ToolbarItem {
                            Assets.Icons.bookmarkBorder.swiftUIImage
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
