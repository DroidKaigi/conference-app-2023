import Component
import KMPContainer
import Model
import shared
import SwiftUI

public struct ContributorView: View {
    @State var presentingURL: IdentifiableURL?
    @ObservedObject var viewModel: ContributorViewModel = .init()

    public init() {}

    public var body: some View {
        ContributorComposeView(
            viewModel.getRepositoryForCompose(),
            onContributorItemClick: { contributorUrl in
                presentingURL = IdentifiableURL(string: contributorUrl)
            }
        )
        .navigationTitle("Contributor")
        .sheet(item: $presentingURL) { url in
            if let url = url.id {
                SafariView(url: url)
                    .ignoresSafeArea()
            }
        }
    }
}

#Preview {
    ContributorView()
}

struct ContributorComposeView: UIViewControllerRepresentable {
    let contributorsRepository: ContributorsRepository
    let onContributorItemClick: (String) -> Void

    init(_ contributorsRepository: ContributorsRepository, onContributorItemClick: @escaping (String) -> Void) {
        self.contributorsRepository = contributorsRepository
        self.onContributorItemClick = onContributorItemClick
    }

    func makeUIViewController(context: Context) -> UIViewController {
        let vc = DarwinContributorsKt.contributorViewController(
            contributorsRepository: contributorsRepository,
            onContributorItemClick: onContributorItemClick
        )
        vc.overrideUserInterfaceStyle = .light
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
