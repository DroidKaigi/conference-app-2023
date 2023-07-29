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
                            Section(header: HStack(spacing: 8) {
                                ForEach(
                                    [DroidKaigi2023Day].fromKotlinArray(DroidKaigi2023Day.values()),
                                    id: \.ordinal
                                ) { day in
                                    let startDay = Calendar.current.component(.day, from: day.start.toDate())
                                    Button {
                                        viewModel.selectDay(day: day)
                                    } label: {
                                        VStack(spacing: 0) {
                                            Text(day.name)
                                                .font(Font.system(size: 12, weight: .semibold))
    //                                        if viewStore.showDate {
                                                Text("\(startDay)")
                                                    .font(Font.system(size: 24, weight: .semibold))
                                                    .frame(height: 32)
    //                                        }
                                        }
                                        .padding(4)
                                        .frame(maxWidth: .infinity)
                                        .foregroundStyle(
                                            viewModel.state.selectedDay == day
                                            ? AssetColors.Primary.onPrimary.swiftUIColor
                                            : AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                                        .background(
                                            viewModel.state.selectedDay == day
                                            ? AssetColors.Primary.primary.swiftUIColor
                                            : Color.clear
                                        )
                                        .clipShape(Capsule())
                                    }
                                }
                            }
                            .padding(.horizontal, 16)
                            .padding(.vertical, 12)
                            .background(AssetColors.Surface.surface.swiftUIColor)
                            ) {
                                TimetableListView(timeGroupTimetableItems: state)
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
                            Assets.Icons.search.swiftUIImage
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
