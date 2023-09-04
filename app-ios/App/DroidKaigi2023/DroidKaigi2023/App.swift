import Assets
import Navigation
import SwiftUI

@main
struct MainApp: App {
    init() { FontAssets.registerAllCustomFonts() }
    
    var body: some Scene {
        WindowGroup {
            RootView()
        }
    }
}
