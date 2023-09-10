import SwiftUI

struct Typography {
    var displayLarge: TextStyle = TypographyTokens.DisplayLarge
    var displayMedium: TextStyle = TypographyTokens.DisplayMedium
    var displaySmall: TextStyle = TypographyTokens.DisplaySmall
    var headlineLarge: TextStyle = TypographyTokens.HeadlineLarge
    var headlineMedium: TextStyle = TypographyTokens.HeadlineMedium
    var headlineSmall: TextStyle = TypographyTokens.HeadlineSmall
    var titleLarge: TextStyle = TypographyTokens.TitleLarge
    var titleMedium: TextStyle = TypographyTokens.TitleMedium
    var titleSmall: TextStyle = TypographyTokens.TitleSmall
    var bodyLarge: TextStyle = TypographyTokens.BodyLarge
    var bodyMedium: TextStyle = TypographyTokens.BodyMedium
    var bodySmall: TextStyle = TypographyTokens.BodySmall
    var labelLarge: TextStyle = TypographyTokens.LabelLarge
    var labelMedium: TextStyle = TypographyTokens.LabelMedium
    var labelSmall: TextStyle = TypographyTokens.LabelSmall
}

struct TextStyle {
    var font: SwiftUI.Font
    var lineHeight: CGFloat
    var letterSpacing: CGFloat?
}

struct TypographyTokens {
    static let DisplayLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 57), lineHeight: 64)
    static let DisplayMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 45), lineHeight: 52)
    static let DisplaySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 36), lineHeight: 44)
    static let HeadlineLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 32), lineHeight: 40)
    static let HeadlineMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 28), lineHeight: 36)
    static let HeadlineSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 24), lineHeight: 32)
    static let TitleLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 22), lineHeight: 28)
    static let TitleMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 16), lineHeight: 24, letterSpacing: 0.15)
    static let TitleSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    static let LabelLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    static let LabelMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 12), lineHeight: 16, letterSpacing: 0.5)
    static let LabelSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 11), lineHeight: 16, letterSpacing: 0.5)
    static let BodyLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 16), lineHeight: 24, letterSpacing: 0.15)
    static let BodyMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 14), lineHeight: 20, letterSpacing: 0.25)
    static let BodySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 12), lineHeight: 16, letterSpacing: 0.4)
}
