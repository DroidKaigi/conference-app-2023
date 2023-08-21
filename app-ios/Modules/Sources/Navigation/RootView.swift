import About
import Assets
import Contributor
import FloorMap
import Session
import Sponsor
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
                        Assets.Icons.timetable.swiftUIImage
                            .renderingMode(.template)
                    }
                }
            FloorMapView()
                .tag(Tab.floorMap)
                .tabItem {
                    Label {
                        Text("FloorMap")
                    } icon: {
                        Assets.Icons.map.swiftUIImage
                            .renderingMode(.template)
                    }
                }
            StampsView()
                .tag(Tab.stamps)
                .tabItem {
                    Label {
                        Text("Stamps")
                    } icon: {
                        Assets.Icons.badge.swiftUIImage
                            .renderingMode(.template)
                    }
                }
            AboutView(
                contributorViewProvider: { _ in
                    ContributorView()
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
                        Assets.Icons.info.swiftUIImage
                            .renderingMode(.template)
                    }
                }
        }
        .tint(AssetColors.Secondary.onSecondaryContainer.swiftUIColor)
    }
}

// #Preview {
//     RootView()
// }
