import shared
import SwiftUI
import Theme

public struct SponsorView: View {
    @StateObject var viewModel: SponsorViewModel = .init()

    public init() {}

    public var body: some View {
        Group {
            switch viewModel.state.planGroupedSponsors {
            case .initial, .loading:
                ProgressView()
                    .task {
                        await viewModel.load()
                    }
            case .loaded(let planGroupedSponsors):
                ScrollView {
                    LazyVStack(spacing: 24) {
                        ForEach(planGroupedSponsors.keys.sorted(by: {
                            $0.ordinal < $1.ordinal
                        }), id: \.self) { plan in
                            SponsorGridView(
                                title: plan.name,
                                sponsors: planGroupedSponsors[plan] ?? [],
                                columns: plan.column
                            ) { _ in }
                        }
                    }
                    .padding(16)
                }
            case .failed:
                EmptyView()
            }
        }
        .navigationTitle("Sponsors")
        .background(AssetColors.Surface.surface.swiftUIColor)
    }
}

#Preview {
    SponsorView()
}

private extension Plan {
    var column: Int {
        switch self {
        case .platinum:
            return 1
        case .gold:
            return 2
        case .supporter:
            return 3
        default:
            return 3
        }
    }
}
