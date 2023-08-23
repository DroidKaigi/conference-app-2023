import Component
import SwiftUI

public struct StaffView: View {
    @ObservedObject var viewModel: StaffViewModel = .init()

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
                    LazyVStack(spacing: 20) {
                        ForEach(staffs, id: \.id) { staff in
                            PersonLabel(
                                name: staff.username,
                                iconUrlString: staff.iconUrl
                            )
                        }
                    }
                    .padding(16)
                }
            }
        }
        .navigationTitle("Staff")
    }
}

#Preview {
    StaffView()
}
