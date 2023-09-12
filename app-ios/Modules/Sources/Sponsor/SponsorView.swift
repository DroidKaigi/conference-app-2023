import Assets
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
                    LazyVStack(spacing: SpacingTokens.xl) {
                        ForEach(planGroupedSponsors.keys.sorted(by: {
                            $0.ordinal < $1.ordinal
                        }), id: \.self) { plan in
                            SponsorGridView(
                                title: plan.title,
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
        .navigationTitle(L10n.Sponsor.title)
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

    var title: String {
        switch self {
        case .platinum:
            return L10n.Sponsor.platinumSponsors
        case .gold:
            return L10n.Sponsor.goldSponsors
        case .supporter:
            return L10n.Sponsor.supporters
        default:
            return ""
        }
    }
}
