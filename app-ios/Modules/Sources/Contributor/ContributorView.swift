import Component
import Dependencies
import Model
import shared
import SwiftUI

public struct ContributorView: View {
    @State var presentingURL: IdentifiableURL?

    @State private var selection: ViewType = .swiftUi
    private enum ViewType: Hashable {
        case swiftUi
        case compose
    }

    public init() {}

    public var body: some View {
        VStack {
            Picker("select view type", selection: $selection) {
                Text("SwiftUI").tag(ViewType.swiftUi)
                Text("Compose").tag(ViewType.compose)
            }
            .pickerStyle(.segmented)

            switch selection {
            case .swiftUi:
                ContributorSwiftUIView { url in
                    presentingURL = .init(string: url)
                }
            case .compose:
                ContributorComposeView { url in
                    presentingURL = .init(string: url)
                }
            }
            Spacer()
        }
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
