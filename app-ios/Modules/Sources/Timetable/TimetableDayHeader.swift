import Assets
import Model
import shared
import SwiftUI
import Theme

struct TimetableDayHeader: View {
    let selectedDay: DroidKaigi2023Day
    let shouldCollapse: Bool
    let onSelect: (DroidKaigi2023Day) -> Void

    // Define margin values to calculate horizontal position for capsule rectangle
    private let buttonAreaLeadingMargin = 16.0
    private let buttonTrailingMargin = 16.0
    private let betweenButtonMargin = 8.0

    // Define all button count to calculate horizontal position for capsule rectangle
    private var buttonsCount: Int {
        Int(DroidKaigi2023Day.values().size)
    }

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
                            if !shouldCollapse {
                                Text("\(day.dayOfMonth)")
                                    .font(Font.system(size: 24, weight: .semibold))
                                    .frame(height: 32)
                            }
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
                        .frame(
                            width: calculateButtonWidth(deviceWidth: geometry.size.width),
                            height: calculateButtonHeight()
                        )
                        .offset(x: calculateDynamicTabHorizontalOffset(deviceWidth: geometry.size.width), y: 10)
                        .animation(.easeInOut(duration: 0.16), value: selectedDay)
                }
            }
        }
    }

    private func calculateDynamicTabHorizontalOffset(deviceWidth: CGFloat) -> CGFloat {
        let buttonAreaWidth = calculateButtonWidth(deviceWidth: deviceWidth)
        // Get the index value corresponding to `selectedDay` and use it for calculation
        let indexBySelectedDay = getIndexBySelectedDay()
        return buttonAreaLeadingMargin + (betweenButtonMargin + buttonAreaWidth) * CGFloat(indexBySelectedDay)
    }

    private func calculateButtonHeight() -> CGFloat {
        return shouldCollapse ? CGFloat(27) : CGFloat(56)
    }

    private func getIndexBySelectedDay() -> Int {
        Int(selectedDay.ordinal)
    }

    private func calculateButtonWidth(deviceWidth: CGFloat) -> CGFloat {
        // Calculate button width considering related margins
        let excludeTotalMargin = calculateExcludeTotalMargin()
        return (deviceWidth - excludeTotalMargin) / CGFloat(buttonsCount)
    }

    private func calculateExcludeTotalMargin() -> CGFloat {
        let totalBetweenButtonMargin = betweenButtonMargin * CGFloat(buttonsCount - 1)
        return buttonAreaLeadingMargin + buttonTrailingMargin + totalBetweenButtonMargin
    }
}

#Preview {
    TimetableDayHeader(selectedDay: .day1, shouldCollapse: false, onSelect: { _ in })
}
