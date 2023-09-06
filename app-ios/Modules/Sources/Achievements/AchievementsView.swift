import Assets
import shared
import SwiftUI
import Theme

public struct AchievementsView: View {
    @ObservedObject var viewModel: AchievementsViewModel = .init()

    public init() {}

    public var body: some View {
        switch viewModel.state.loadedState {
        case .initial, .loading:
            ProgressView()
                .task {
                    await viewModel.load()
                }
        case .failed:
            EmptyView()
        case .loaded(let state):
            NavigationStack {
                ScrollView {
                    VStack(spacing: 24) {
                        Text(state.description)
                            .font(Font.system(size: 16))
                            .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                        LazyVGrid(columns: [.init(), .init()]) {
                            if state.achievements.contains(AchievementsItemId) {
                                Assets.Images.achievementArcticFoxActive.swiftUIImage
                            } else {
                                Assets.Images.achievementArcticFox.swiftUIImage
                            }
                            Assets.Images.achievementBumblebee.swiftUIImage
                            Assets.Images.achievementChipmunk.swiftUIImage
                            Assets.Images.achievementDolphin.swiftUIImage
                            // TODO: Find good render way
                            Assets.Images.achievementElectricEel.swiftUIImage
                        }
                    }
                    .padding(16)
                }
                .background(AssetColors.Surface.surface.swiftUIColor)
                .navigationTitle("Achievements")
            }
        }
    }
}

// #Preview {
//     AchievementsView()
// }
