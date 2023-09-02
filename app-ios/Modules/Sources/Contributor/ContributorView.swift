import Component
import Dependencies
import Model
import shared
import SwiftUI

public struct ContributorView: View {
    @State var presentingURL: IdentifiableURL?

    public init() {}

    public var body: some View {
        Group {
//            ContributorSwiftUIView { url in
//                presentingURL = .init(string: url)
//            }

            ContributorComposeView { url in
                presentingURL = .init(string: url)
            }
        }
        ContributorComposeView(
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

struct ContributorSwiftUIView: View {
    @ObservedObject var viewModel: ContributorViewModel = .init()
    let onContributorItemClick: (String) -> Void

    var body: some View {
        Group {
            switch viewModel.state.contributors {
            case .initial, .loading:
                ProgressView()
                    .task {
                        await viewModel.load()
                    }
            case .failed:
                EmptyView()
            case .loaded(let contributors):
                ScrollView {
                    LazyVStack(spacing: 20) {
                        ForEach(contributors, id: \.id) { contributor in
                            Button {
                                if let profileUrl = contributor.profileUrl {
                                    onContributorItemClick(profileUrl)
                                }
                            } label: {
                                PersonLabel(
                                    name: contributor.username,
                                    iconUrlString: contributor.iconUrl
                                )
                            }
                        }
                    }
                    .padding(16)
                }
            }
        }
    }
}

struct ContributorComposeView: UIViewControllerRepresentable {
    @Dependency(\.contributorsRepositoryData) var contributorsRepositoryData
    @Environment(\.colorScheme) var colorScheme
    let onContributorItemClick: (String) -> Void

    init(onContributorItemClick: @escaping (String) -> Void) {
        self.onContributorItemClick = onContributorItemClick
    }

    func makeUIViewController(context: Context) -> UIViewController {
        let vc = DarwinContributorsKt.contributorViewController(
            contributorsRepository: contributorsRepositoryData.contributorsRepository,
            onContributorItemClick: onContributorItemClick
        )
        vc.overrideUserInterfaceStyle = .init(colorScheme)
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
