import Navigation
import SwiftUI
import Theme

@main
struct MainApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    init() { FontAssets.registerAllCustomFonts() }

    var body: some Scene {
        WindowGroup {
            RootView()
        }
    }
}
