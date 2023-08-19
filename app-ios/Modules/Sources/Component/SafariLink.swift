import SwiftUI
import SafariServices

public struct SafariLink<Content>: View where Content: View {
    
    @State private var isSheetPresented: Bool = false
    
    private let url: URL
    private let configuration: SFSafariViewController.Configuration
    private let content: () -> Content
    
    public init(
        url: URL,
        configuration: SFSafariViewController.Configuration? = nil,
        content: @escaping () -> Content
    ) {
        self.url = url
        let defaultConfiguration = SFSafariViewController.Configuration()
        defaultConfiguration.barCollapsingEnabled = false
        self.configuration = configuration ?? defaultConfiguration
        self.content = content
    }
    
    public var body: some View {
        Button {
            isSheetPresented = true
        } label: {
            content()
        }
        .sheet(isPresented: $isSheetPresented) {
            SafariView(
                url: url,
                configuration: configuration
            )
            .ignoresSafeArea()
        }
    }
}

#Preview {
    SafariLink(
        url: URL(string: "https://2023.droidkaigi.jp/")!
    ) {
        Text("Droid Kaigi")
            .padding(16)
    }
    .previewLayout(.sizeThatFits)
}
