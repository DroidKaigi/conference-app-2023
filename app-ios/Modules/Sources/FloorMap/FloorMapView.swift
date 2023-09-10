import Assets
import shared
import SwiftUI
import Theme

public struct FloorMapView: View {
    @StateObject var viewModel: FloorMapViewModel = .init()
    @State private var floor: FloorLevel = .ground

    public init() {}

    public var body: some View {
        NavigationStack {
            Group {
                switch viewModel.state.sideEvents {
                case .initial, .loading:
                    ProgressView()
                        .task {
                            await viewModel.load()
                        }
                case .failed:
                    EmptyView()
                case .loaded(let floorSideEvent):
                    ZStack {
                        ScrollView {
                            switch floor {
                            case .ground: groundMapView(sideEvents: floorSideEvent.ground)
                            case .basement: basementMapView(sideEvents: floorSideEvent.basement)
                            default: groundMapView(sideEvents: floorSideEvent.ground)
                            }
                            Spacer().frame(height: 8 + 40 + 24) // row bottom padding + button height + button bottom padding 
                        }
                        .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                        VStack(spacing: 0) {
                            Spacer()
                            FloorMapFooterSegmentedButton(floor: $floor)
                                .padding(.bottom, 24)
                        }
                    }
                }
            }
            .navigationTitle("Floor Map")
        }
    }

    /// first floor map
    private func groundMapView(sideEvents: [SideEvent]) -> some View {
        VStack(alignment: .leading, spacing: 24) {
            VStack(alignment: .leading, spacing: 8) {
                Text("1F")
                    .font(Font.custom(FontAssets.Montserrat.medium, size: 24))
                Assets.Images.floor1F.swiftUIImage
                    .resizable()
                    .scaledToFit()
            }

            sideEventList(sideEvents: sideEvents)
        }
        .padding(.horizontal, 16)
    }

    /// basement first floor map
    private func basementMapView(sideEvents: [SideEvent]) -> some View {
        VStack(alignment: .leading, spacing: 24) {
            VStack(alignment: .leading, spacing: 8) {
                Text("B1F")
                    .font(Font.custom(FontAssets.Montserrat.medium, size: 24))
                Assets.Images.floorB1F.swiftUIImage
                    .resizable()
                    .scaledToFit()
            }

            sideEventList(sideEvents: sideEvents)
        }
        .padding(.horizontal, 16)
    }

    /// Side Events List
    private func sideEventList(sideEvents: [SideEvent]) -> some View {
        LazyVStack(spacing: 16) {
            ForEach(0..<sideEvents.count, id: \.self) { index in
                SideEventRow(sideEvent: sideEvents[index])

                if index != sideEvents.count - 1 {
                    Divider()
                        .frame(height: 1)
                        .foregroundStyle(AssetColors.Outline.outlineVariant.swiftUIColor)
                }
            }
        }
    }
}

#Preview {
    FloorMapView()
}
