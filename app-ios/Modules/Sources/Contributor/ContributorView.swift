import Component
import SwiftUI

public struct ContributorView: View {
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
                            PersonLabel(
                                name: contributor.username,
                                iconUrlString: contributor.iconUrl
                            )
                        }
                    }
                    .padding(16)
                }
            }
        }
        .navigationTitle("Contributor")
    }
}

#Preview {
    ContributorView()
}
