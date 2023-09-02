import Navigation
import SwiftUI
import Assets

@main
struct MainApp: App {
    init() { FontAssets.registerAllCustomFonts() }
    
    var body: some Scene {
        WindowGroup {
            RootView()
        }
    }
}
