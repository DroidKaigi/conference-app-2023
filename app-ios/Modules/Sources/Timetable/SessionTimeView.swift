import SwiftUI

struct SessionTimeView: View {
    var startsAt: Date
    var endsAt: Date

    var body: some View {
        VStack {
            Text(startsAt.description)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
                .frame(height: 24)
            Rectangle()
                .frame(width: 1, height: 4)
            Text(endsAt.description)
                .font(Font(UIFont.systemFont(ofSize: 16, weight: .bold)))
                .frame(height: 24)
        }
    }
}

//#Preview {
//    SessionTimeView(startsAt: .distantPast, endsAt: .distantFuture)
//}
