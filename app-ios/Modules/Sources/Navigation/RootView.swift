import Session
import SwiftUI
import Timeline

enum Tab {
    case timeline
}

public struct RootView: View {
    @State var selection = Tab.timeline

    public init() {}

    public var body: some View {
        NavigationStack {
            TabView(selection: $selection) {
                TimelineView()
                    .tag(Tab.timeline)
                    .tabItem {
                        Label("Timeline", systemImage: "circle")
                    }
            }
            .navigationDestination(for: String.self) { title in
                SessionView()
            }
//            .fullScreenCover(item: Binding<Identifiable?>) { title in
//                SessionView()
//            }
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
    }
}
