import Component
import Model
import SwiftUI

public struct ContributorView: View {
    @State var presentingURL: IdentifiableURL?
    @ObservedObject var viewModel: ContributorViewModel = .init()

    public init() {}

    public var body: some View {
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
                                    presentingURL = IdentifiableURL(string: profileUrl)
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
