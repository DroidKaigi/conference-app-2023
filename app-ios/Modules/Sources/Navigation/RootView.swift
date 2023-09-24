import About
import Achievements
import Assets
import Contributor
import FloorMap
import Session
import Sponsor
import Staff
import SwiftUI
import Theme
import Timetable

enum Tab: Int, CaseIterable, Identifiable {
    var id: Int {
        rawValue
    }
    
    case timeline
    case floorMap
    case achievements
    case about
}

public struct RootView: View {
    @Environment(\.horizontalSizeClass) var horizontalSizeClass
    @Environment(\.verticalSizeClass) var verticalSizeClass
    @StateObject var viewModel: RootViewModel = .init()
    @State var selection = Tab.timeline
    @State var selectionForSidebar: Tab.ID? = Tab.timeline.id

    public init() {}

    public var body: some View {
        switch viewModel.state.isAchivementEnabled {
        case .initial, .loading:
            ProgressView()
                .task {
                    await viewModel.load()
                }
        case .failed:
            EmptyView()
        case .loaded(let isAchivementEnabled):
            let timetableView = TimetableView(
                sessionViewBuilder: { timetableItem in
                    SessionView(timetableItem: timetableItem)
                }
            )
            let aboutView = AboutView(
                contributorViewProvider: { _ in
                    ContributorView()
                },
                staffViewProvider: { _ in
                    StaffView()
                },
                sponsorViewProvider: { _ in
                    SponsorView()
                }
            )
            Group {
                if horizontalSizeClass == .regular && verticalSizeClass == .regular {
                    NavigationSplitView(columnVisibility: .constant(.doubleColumn)) {
                        List(Tab.allCases, selection: $selectionForSidebar) {
                            let selected = $0.id == selectionForSidebar
                            switch $0 {
                            case .timeline:
                                TimetableViewLabel(selected: selected)
                            case .floorMap:
                                FloorMapViewLabel(selected: selected)
                            case .achievements:
                                AchievementsViewLabel(selected: selected)
                            case .about:
                                AboutViewLabel(selected: selected)
                            }
                        }
                    } detail: {
                        if let selectionForSidebar, let tab = Tab(rawValue: selectionForSidebar) {
                            switch tab {
                            case .timeline:
                                timetableView
                            case .floorMap:
                                FloorMapView()
                            case .achievements:
                                AchievementsView()
                            case .about:
                                aboutView
                            }
                        } else {
                            fatalError()
                        }
                    }
                    .navigationSplitViewStyle(.balanced)
                } else {
                    TabView(selection: $selection) {
                        timetableView
                            .tag(Tab.timeline)
                            .tabItem {
                                TimetableViewLabel(selected: selection == .timeline)
                            }
                        FloorMapView()
                            .tag(Tab.floorMap)
                            .tabItem {
                                FloorMapViewLabel(selected: selection == .floorMap)
                            }
        //                if isAchivementEnabled {
                            AchievementsView()
                                .tag(Tab.achievements)
                                .tabItem {
                                    AchievementsViewLabel(selected: selection == .achievements)
                                }
        //                }
                        aboutView
                            .tag(Tab.about)
                            .tabItem {
                                AboutViewLabel(selected: selection == .about)
                            }
                    }
                }
            }
            .tint(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
        }
    }
}

private struct TimetableViewLabel: View {
    let selected: Bool
    var body: some View {
        Label {
            Text("Timetable")
        } icon: {
            if selected {
                Assets.Icons.timetable.swiftUIImage
                    .renderingMode(.template)
            } else {
                Assets.Icons.timetableFillOff.swiftUIImage
                    .renderingMode(.template)
            }
        }
    }
}

private struct FloorMapViewLabel: View {
    let selected: Bool
    var body: some View {
        Label {
            Text("Floor Map")
        } icon: {
            if selected {
                Assets.Icons.floorMap.swiftUIImage
                    .renderingMode(.template)
            } else {
                Assets.Icons.floorMapFillOff.swiftUIImage
                    .renderingMode(.template)
            }
        }
    }
}

private struct AchievementsViewLabel: View {
    let selected: Bool
    var body: some View {
        Label {
            Text("Achievements")
        } icon: {
            if selected {
                Assets.Icons.achievements.swiftUIImage
                    .renderingMode(.template)
            } else {
                Assets.Icons.achievementsFillOff.swiftUIImage
                    .renderingMode(.template)
            }
        }
    }
}

private struct AboutViewLabel: View {
    let selected: Bool
    var body: some View {
        Label {
            Text("About")
        } icon: {
            if selected {
                Assets.Icons.info.swiftUIImage
                    .renderingMode(.template)
            } else {
                Assets.Icons.infoFillOff.swiftUIImage
                    .renderingMode(.template)
            }
        }
    }
}

// #Preview {
//     RootView()
// }
