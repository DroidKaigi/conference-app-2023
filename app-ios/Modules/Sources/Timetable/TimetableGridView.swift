import Model
import shared
import SwiftUI
import Theme

struct TimetableGridView: View {
    let roomHeaderSize: CGSize = .init(width: 194, height: 40)
    let gridSize: CGSize = .init(width: 194, height: 310)

    let timetableRoomGroupItems: [TimetableRoomGroupItems]
    let hours: [Date]

    init(
        day: DroidKaigi2023Day,
        timetableRoomGroupItems: [TimetableRoomGroupItems]
    ) {
        self.timetableRoomGroupItems = timetableRoomGroupItems

        var dateComponents = Calendar(identifier: .gregorian)
            .dateComponents(in: TimeZone(identifier: "Asia/Tokyo")!, from: day.start.toDate())
        hours = (10 ... 19).compactMap { hour in
            dateComponents.hour = hour
            dateComponents.minute = 0
            return dateComponents.date
        }
    }

    var body: some View {
        ScrollView(.vertical) {
            HStack(alignment: .top, spacing: 0) {
                VStack(spacing: 0) {
                    Text(hourFormatter.string(from: hours[0]))
                        .textStyle(TypographyTokens.labelMedium)
                        .frame(height: roomHeaderSize.height + 8, alignment: .bottom)

                    ForEach(hours[1...], id: \.self) { hour in
                        Text(hourFormatter.string(from: hour))
                            .textStyle(TypographyTokens.labelMedium)
                            .frame(height: gridSize.height, alignment: .bottom)
                    }
                }
                .padding(.leading, SpacingTokens.m)
                .frame(width: 75 - 8, alignment: .leading)

                VStack(spacing: 0) {
                    Divider().frame(height: roomHeaderSize.height, alignment: .bottom)

                    ForEach(hours[1...], id: \.self) { _ in
                        Divider().frame(height: gridSize.height, alignment: .bottom)
                    }
                }
                .frame(width: 8)

                ZStack(alignment: .topLeading) {
                    VStack(spacing: 0) {
                        Divider().frame(height: roomHeaderSize.height, alignment: .bottom)

                        ForEach(hours[1...], id: \.self) { _ in
                            Divider().frame(height: gridSize.height, alignment: .bottom)
                        }
                    }

                    ScrollView(.horizontal) {
                        HStack(alignment: .top, spacing: 0) {
                            Divider()

                            ForEach(timetableRoomGroupItems) { timetableRoomGroupItem in
                                VStack(spacing: 0) {
                                    Text(timetableRoomGroupItem.room.name.currentLangTitle)
                                        .textStyle(TypographyTokens.titleSmall)
                                        .frame(height: roomHeaderSize.height)

                                    ZStack {
                                        ForEach(timetableRoomGroupItem.items, id: \.timetableItem.id.value) { timetableItemWithFavorite in
                                            let frame = itemFrame(
                                                timetableItemWithFavorite: timetableItemWithFavorite,
                                                startTime: hours[0],
                                                gridSize: gridSize
                                            )

                                            TimetableGridItemView(
                                                timetableItemWithFavorite: timetableItemWithFavorite
                                            )
                                            .frame(width: frame.width, height: frame.height)
                                            .position(x: frame.origin.x, y: frame.origin.y)
                                        }
                                    }
                                }
                                .frame(width: gridSize.width)

                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }

    private func itemFrame(
        timetableItemWithFavorite: TimetableItemWithFavorite,
        startTime: Date,
        gridSize: CGSize
    ) -> CGRect {
        let item = timetableItemWithFavorite.timetableItem

        let heightPerSecond = gridSize.height / (60 * 60)
        let itemSpacing = NSDirectionalEdgeInsets(top: 1, leading: 1, bottom: 1, trailing: 1)

        let itemSize = CGSize(
            width: gridSize.width - itemSpacing.leading - itemSpacing.trailing,
            height: CGFloat(item.endsAt.epochSeconds - item.startsAt.epochSeconds) * heightPerSecond
                - itemSpacing.top - itemSpacing.bottom
        )
        let itemPosition = CGPoint(
            x: itemSize.width / 2 + itemSpacing.leading,
            y: (CGFloat(item.startsAt.epochSeconds) - startTime.timeIntervalSince1970) * heightPerSecond
                + itemSize.height / 2 + itemSpacing.top
        )
        return CGRect(origin: itemPosition, size: itemSize)
    }
}

private let hourFormatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.calendar = Calendar(identifier: .gregorian)
    formatter.locale = Locale(identifier: "Asia/Tokyo")
    formatter.dateStyle = .none
    formatter.timeStyle = .short
    return formatter
}()

#if DEBUG
#Preview {
    TimetableGridView(
        day: Timetable.companion.fake().contents.first!.timetableItem.day!,
        timetableRoomGroupItems: [
            TimetableRoomGroupItems(
                room: Timetable.companion.fake().contents.first!.timetableItem.room,
                items: [Timetable.companion.fake().contents.first!]
            ),
        ]
    )
}
#endif
