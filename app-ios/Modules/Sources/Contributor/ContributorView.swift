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
            onNavigationIconClick: {
                
            },
            onContributorItemClick: { contributorUrl in
                NSLog("contributorUrl:"+contributorUrl)
            }
        )
        .navigationTitle("Contributor")
    }
}

#Preview {
    ContributorView()
}

struct ContributorComposeView: UIViewControllerRepresentable {
    let contributorsRepository: ContributorsRepository
    let onNavigationIconClick: () -> Void
    let onContributorItemClick: (String) -> Void

    init(_ contributorsRepository: ContributorsRepository, onNavigationIconClick: @escaping () -> Void, onContributorItemClick: @escaping (String) -> Void) {
        self.contributorsRepository = contributorsRepository
        self.onNavigationIconClick = onNavigationIconClick
        self.onContributorItemClick = onContributorItemClick
    }

    func makeUIViewController(context: Context) -> UIViewController {
        let vc = DarwinContributorsKt.contributorViewController(
            contributorsRepository: contributorsRepository,
            isTopAppBarHidden: true,
            onNavigationIconClick: onNavigationIconClick,
            onContributorItemClick: onContributorItemClick
        )
        vc.overrideUserInterfaceStyle = .light
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
