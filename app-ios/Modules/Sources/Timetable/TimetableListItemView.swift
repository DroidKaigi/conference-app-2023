import SwiftUI
import shared

struct TimetableListItemView: View {
    let timetableItemWithFavorite: TimetableItemWithFavorite
    let onTap: () -> Void

    var body: some View {
        Button {
            onTap()
        } label: {
            HStack(alignment: .top, spacing: 12) {
                VStack(alignment: .leading, spacing: 8) {
                    Text(timetableItemWithFavorite.timetableItem.title.jaTitle)
                        .multilineTextAlignment(.leading)
                        .font(Font.system(size: 22, weight: .medium, design: .default))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    if let session = timetableItemWithFavorite.timetableItem as? TimetableItem.Session,
                       !session.speakers.isEmpty {
                        VStack(spacing: 0) {
                            HStack(spacing: 8) {
                                ForEach(session.speakers, id: \.self) { speaker in
                                    PersonLabel(
                                        name: speaker.name,
                                        iconUrl: speaker.iconUrl
                                    )
                                }
                            }
                            if let message = session.message {
                                HStack(spacing: 4) {
                                    Text(message.currentLangTitle)
                                        .foregroundColor(.red)
                                        .font(Font.system(size: 12, weight: .regular, design: .default))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//#Preview {
//    TimetableListItemView(
//        timetableItemWithFavorite: TimetableItemWithFavorite.companion.fake(),
//        onTap: {}
//    )
//}
