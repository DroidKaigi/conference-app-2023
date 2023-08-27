import About
import Assets
import Contributor
import FloorMap
import Session
import Sponsor
import Staff
import Stamps
import SwiftUI
import Theme
import Timetable

enum Tab {
    case timeline
    case floorMap
    case stamps
    case about
}

public struct RootView: View {
    @State var selection = Tab.timeline

    public init() {}

    public var body: some View {
        TabView(selection: $selection) {
            TimetableView(
                sessionViewBuilder: { timetableItem in
                    SessionView(timetableItem: timetableItem)
                }
            )
                .tag(Tab.timeline)
                .tabItem {
                    Label {
                        Text("Timetable")
                    } icon: {
                        if selection == .timeline {
                            Assets.Icons.timetable.swiftUIImage
                                .renderingMode(.template)
                        } else {
                            Assets.Icons.timetableFillOff.swiftUIImage
                                .renderingMode(.template)
                        }
                    }
                }
            FloorMapView()
                .tag(Tab.floorMap)
                .tabItem {
                    Label {
                        Text("FloorMap")
                    } icon: {
                        if selection == .floorMap {
                            Assets.Icons.floorMap.swiftUIImage
                                .renderingMode(.template)
                        } else {
                            Assets.Icons.floorMapFillOff.swiftUIImage
                                .renderingMode(.template)
                        }
                    }
                }
            StampsView()
                .tag(Tab.stamps)
                .tabItem {
                    Label {
                        Text("Stamps")
                    } icon: {
                        if selection == .stamps {
                            Assets.Icons.stamp.swiftUIImage
                                .renderingMode(.template)
                        } else {
                            Assets.Icons.stampFillOff.swiftUIImage
                                .renderingMode(.template)
                        }
                    }
                }
            AboutView(
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
                .tag(Tab.about)
                .tabItem {
                    Label {
                        Text("About")
                    } icon: {
                        if selection == .about {
                            Assets.Icons.info.swiftUIImage
                                .renderingMode(.template)
                        } else {
                            Assets.Icons.infoFillOff.swiftUIImage
                                .renderingMode(.template)
                        }
                    }
                }
        }
        .tint(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
    }
}

// #Preview {
//     RootView()
// }
