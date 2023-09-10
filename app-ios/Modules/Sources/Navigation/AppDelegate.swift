import DeepLink
import FirebaseCore
import UIKit

public class AppDelegate: UIResponder, UIApplicationDelegate {
    private let deepLink = DeepLink()

    public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        FirebaseApp.configure()
        return true
    }

    public func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        Task {
            if let url = userActivity.webpageURL {
                do {
                    if let dynamicLink = try await deepLink.dynamicLink(shortLink: url) {
                        try await deepLink.handleDynamicLink(dynamicLink: dynamicLink)
                    }
                } catch {
                    print(error)
                }
            }
        }

        return true
    }

    public func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        let config = UISceneConfiguration(name: nil, sessionRole: connectingSceneSession.role)
        config.delegateClass = SceneDelegate.self
        return config
    }
}

class SceneDelegate: UIResponder, UIWindowSceneDelegate {
    private let deepLink = DeepLink()

    func scene(_ scene: UIScene, continue userActivity: NSUserActivity) {
        Task {
            if let url = userActivity.webpageURL {
                do {
                    if let dynamicLink = try await deepLink.dynamicLink(shortLink: url) {
                        try await deepLink.handleDynamicLink(dynamicLink: dynamicLink)
                    }
                } catch {
                    print(error)
                }
            }
        }
    }
}
