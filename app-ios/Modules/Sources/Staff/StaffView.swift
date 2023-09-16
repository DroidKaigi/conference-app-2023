import Component
import Model
import shared
import SwiftUI
import Theme

public struct StaffView: View {
    @State var presentingURL: IdentifiableURL?
    @StateObject var viewModel: StaffViewModel = .init()

    public init() {}

    public var body: some View {
        Group {
            switch viewModel.state.staffs {
            case .initial, .loading:
                ProgressView()
                    .task {
                        await viewModel.load()
                    }
            case .failed:
                EmptyView()
            case .loaded(let staffs):
                ScrollView {
                    LazyVStack(spacing: SpacingTokens.l) {
                        ForEach(staffs, id: \.id) { staff in
                            Button {
                                presentingURL = IdentifiableURL(string: staff.profileUrl)
                            } label: {
                                PersonLabel(
                                    name: staff.username,
                                    iconUrlString: staff.iconUrl
                                )
                            }
                        }
                    }
                    .padding(16)
                }
            }
        }
        .navigationTitle("Staff")
        .sheet(item: $presentingURL) { url in
            if let url = url.id {
                SafariView(url: url)
                    .ignoresSafeArea()
            }
        }
    }
}

#Preview {
    StaffView()
}
