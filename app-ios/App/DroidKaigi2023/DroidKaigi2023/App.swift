import Navigation
import SwiftUI
import Theme

@main
struct MainApp: App {
    init() { FontAssets.registerAllCustomFonts() }
    
    var body: some Scene {
        WindowGroup {
            RootView()
        }
    }
}
