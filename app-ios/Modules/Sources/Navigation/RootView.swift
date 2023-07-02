import Session
import SwiftUI
import Timetable

enum Tab {
    case timeline
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
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
    }
}
