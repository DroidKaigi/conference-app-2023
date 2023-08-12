import Assets
import Model
import shared
import SwiftUI
import Theme

struct TimetableDayHeader: View {
    let selectedDay: DroidKaigi2023Day
    let onSelect: (DroidKaigi2023Day) -> Void

    var body: some View {
        HStack(spacing: 8) {
            ForEach(
                [DroidKaigi2023Day].fromKotlinArray(DroidKaigi2023Day.values()),
                id: \.ordinal
            ) { (day: DroidKaigi2023Day) in
                Button {
                    onSelect(day)
                } label: {
                    VStack(spacing: 0) {
                        Text(day.name)
                            .font(Font.system(size: 12, weight: .semibold))
                        Text("\(day.dayOfMonth)")
                            .font(Font.system(size: 24, weight: .semibold))
                            .frame(height: 32)
                    }
                    .padding(4)
                    .frame(maxWidth: .infinity)
                    .foregroundStyle(
                        selectedDay == day
                        ? AssetColors.Primary.onPrimary.swiftUIColor
                        : AssetColors.Surface.onSurfaceVariant.swiftUIColor)
                    .background(
                        selectedDay == day
                        ? AssetColors.Primary.primary.swiftUIColor
                        : Color.clear
                    )
                    .clipShape(Capsule())
                }
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
        .background(AssetColors.Surface.surface.swiftUIColor)
    }
}

#Preview {
    TimetableDayHeader(selectedDay: .day1, onSelect: { _ in })
}
