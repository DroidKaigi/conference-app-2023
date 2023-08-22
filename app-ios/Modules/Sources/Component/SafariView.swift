import SafariServices
import SwiftUI

public struct SafariView: UIViewControllerRepresentable {
    private let url: URL
    private let configuration: SFSafariViewController.Configuration
    
    public init(
        url: URL,
        configuration: SFSafariViewController.Configuration? = nil
    ) {
        self.url = url
        let defaultConfiguration = SFSafariViewController.Configuration()
        defaultConfiguration.barCollapsingEnabled = false
        self.configuration = configuration ?? defaultConfiguration
    }
    
    public func makeUIViewController(context: Context) -> some UIViewController {
        let safariViewController = SFSafariViewController(
            url: url,
            configuration: configuration
        )
        return safariViewController
    }
    
    public func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}

#Preview {
    SafariView(
        url: URL(string: "https://2023.droidkaigi.jp/")!
    )
}
