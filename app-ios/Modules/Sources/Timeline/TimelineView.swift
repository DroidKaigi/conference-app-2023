import SwiftUI

public struct TimelineView: View {
    @ObservedObject var viewModel: TimelineViewModel = .init()

    public init() {}

    public var body: some View {
        VStack {
            List(viewModel.titles, id: \.self) { title in
                NavigationLink(value: title) {
                    Text(title)
                }
            }
            Button("Add Session") {
                viewModel.addSession()
            }
        }
    }
}

struct TimelineView_Previews: PreviewProvider {
    static var previews: some View {
        TimelineView()
    }
}
