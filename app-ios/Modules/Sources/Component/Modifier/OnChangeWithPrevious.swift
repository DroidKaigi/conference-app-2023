import SwiftUI

struct OnChangeWithPrevious<T: Equatable>: ViewModifier {
    var value: T
    var onChange: (T?, T) -> Void

    func body(content: Content) -> some View {
        if #available(iOS 17.0, *) {
            content.onChange(of: value) { oldValue, newValue in
                onChange(oldValue, newValue)
            }
        } else {
            content.onChange(of: value) { [value] newValue in
                onChange(value, newValue)
            }
        }
    }
}

public extension View {
    func onChangeWithPrevious<T: Equatable>(of value: T, onChange: @escaping (T?, T) -> Void) -> some View {
        modifier(OnChangeWithPrevious(value: value, onChange: onChange))
    }
}
