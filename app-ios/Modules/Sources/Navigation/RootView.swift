import About
import FloorMap
import Session
import Stamps
import SwiftUI
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
                sessionViewBuilder: { _ in
                    SessionView()
                }
            )
                .tag(Tab.timeline)
                .tabItem {
                    Label("Timeline", systemImage: "circle")
                }
            FloorMapView()
                .tag(Tab.floorMap)
                .tabItem {
                    Label("FloorMap", systemImage: "circle")
                }
            StampsView()
                .tag(Tab.stamps)
                .tabItem {
                    Label("Stamps", systemImage: "circle")
                }
            AboutView()
                .tag(Tab.about)
                .tabItem {
                    Label("About", systemImage: "circle")
                }
        }
    }
}

// #Preview {
//     RootView()
// }
