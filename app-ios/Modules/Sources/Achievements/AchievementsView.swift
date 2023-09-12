import Assets
import shared
import SwiftUI
import Theme

public struct AchievementsView: View {
    @ObservedObject var viewModel: AchievementsViewModel = .init()
    @State private var obtainedAchievement: Achievement?

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
                    ZStack {
                        VStack(spacing: SpacingTokens.xl) {
                            Text(state.description)
                                .font(Font.system(size: 16))
                                .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                            LazyVGrid(columns: [.init(), .init()]) {
                                AchievementImage(
                                    target: Achievement.arcticfox,
                                    savedAchievements: state.achievements,
                                    activeImage: Assets.Images.achievementArcticFoxActive,
                                    inactiveImage: Assets.Images.achievementArcticFox
                                )
                                AchievementImage(
                                    target: Achievement.bumblebee,
                                    savedAchievements: state.achievements,
                                    activeImage: Assets.Images.achievementBumbleBeeActive,
                                    inactiveImage: Assets.Images.achievementBumblebee
                                )
                                AchievementImage(
                                    target: Achievement.chipmunk,
                                    savedAchievements: state.achievements,
                                    activeImage: Assets.Images.achievementChipmunkActive,
                                    inactiveImage: Assets.Images.achievementChipmunk
                                )
                                AchievementImage(
                                    target: Achievement.dolphin,
                                    savedAchievements: state.achievements,
                                    activeImage: Assets.Images.achievementDolphinActive,
                                    inactiveImage: Assets.Images.achievementDolphin
                                )
                                // TODO: Find good render way
                                AchievementImage(
                                    target: Achievement.electriceel,
                                    savedAchievements: state.achievements,
                                    activeImage: Assets.Images.achievementElectricEelActive,
                                    inactiveImage: Assets.Images.achievementElectricEel
                                )
                            }
                            Button {
                                Task {
                                    let result = await viewModel.read()
                                    obtainedAchievement = result
                                }
                            } label: {
                                Text("Scan NFC Tag")
                            }
                            .buttonStyle(.bordered)
                            .controlSize(.large)
                        }
                        .padding(16)
                        if let achievement = obtainedAchievement?.toLottie() {
                            AssetColors.Custom.black.swiftUIColor.opacity(0.84)
                            achievement.swiftUIAnimation(loopMode: .playOnce) { _ in
                                obtainedAchievement = nil
                            }
                        }
                    }
                }
                .background(AssetColors.Surface.surface.swiftUIColor)
                .navigationTitle("Achievements")
            }
        }
    }
}

struct AchievementImage: View {
    let target: Achievement
    let savedAchievements: Set<Achievement>
    let activeImage: ImageAsset
    let inactiveImage: ImageAsset

    var body: some View {
        if savedAchievements.contains(target) {
            activeImage.swiftUIImage
        } else {
            inactiveImage.swiftUIImage
        }
    }
}

private extension Achievement {
    func toLottie() -> LottieAnimation? {
        switch self {
        case .arcticfox:
            return LottieAssets.achievementAJson
        case .bumblebee:
            return LottieAssets.achievementBJson
        case .chipmunk:
            return LottieAssets.achievementCJson
        case .dolphin:
            return LottieAssets.achievementDJson
        case .electriceel:
            return LottieAssets.achievementEJson
        default:
            return nil
        }
    }
}

// #Preview {
//     AchievementsView()
// }
