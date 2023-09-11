import SwiftUI

public extension Text {
    func textStyle(_ style: TextStyle) -> some View {
        return self.font(style.font)
            .lineSpacing(style.lineHeight)
            .modifier(LetterSpacingModifier(spacing: style.letterSpacing ?? 0))
    }
}

public struct LetterSpacingModifier: ViewModifier {
    var spacing: CGFloat
    public func body(content: Content) -> some View {
        content.padding(.leading, spacing/2)
            .padding(.trailing, spacing/2)
    }
}
