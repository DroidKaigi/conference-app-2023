import Assets
import Model
import shared
import SwiftUI
import Theme

struct TimetableDayHeader: View {
    let selectedDay: DroidKaigi2023Day
    let onSelect: (DroidKaigi2023Day) -> Void

    var body: some View {
        ZStack {
            AssetColors.Surface.surface.swiftUIColor
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
                    }
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .background(alignment: .center) {
                GeometryReader { geometry in
                    Capsule()
                        .fill(AssetColors.Primary.primary.swiftUIColor)
                        .frame(width: calcButtonWidth(width: geometry.size.width), height: 56)
                        .offset(x: calcDynamicTabHorizontalOffset(width: geometry.size.width), y: 10)
                        .animation(.easeInOut(duration: 0.16), value: selectedDay)
                }
            }
        }
    }

    private func calcDynamicTabHorizontalOffset(width: CGFloat) -> CGFloat {
        let buttonAreaWidth = calcButtonWidth(width: width)
        switch selectedDay {
        case .day1:
            return 16.0
        case .day2:
            return 24.0 + buttonAreaWidth
        case .day3:
            return 32.0 + buttonAreaWidth * 2
        default:
            return 24.0 + buttonAreaWidth
        }
    }

    private func calcButtonWidth(width: CGFloat) -> CGFloat {
        (width - 48.0) / 3.0
    }
}

#Preview {
    TimetableDayHeader(selectedDay: .day1, onSelect: { _ in })
}
