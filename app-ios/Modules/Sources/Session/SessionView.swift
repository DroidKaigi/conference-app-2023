import SwiftUI

public struct SessionView: View {
    @ObservedObject var viewModel: SessionViewModel = .init()

    public init() {}

    public var body: some View {
        Text("Session View")
    }
}

struct SessionView_Previews: PreviewProvider {
    static var previews: some View {
        SessionView()
    }
}
