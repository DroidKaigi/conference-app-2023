import SwiftUI

struct Typography {
    var displayLarge: TextStyle = TypographyTokens.displayLarge
    var displayMedium: TextStyle = TypographyTokens.displayMedium
    var displaySmall: TextStyle = TypographyTokens.displaySmall
    var headlineLarge: TextStyle = TypographyTokens.headlineLarge
    var headlineMedium: TextStyle = TypographyTokens.headlineMedium
    var headlineSmall: TextStyle = TypographyTokens.headlineSmall
    var titleLarge: TextStyle = TypographyTokens.titleLarge
    var titleMedium: TextStyle = TypographyTokens.titleMedium
    var titleSmall: TextStyle = TypographyTokens.titleSmall
    var bodyLarge: TextStyle = TypographyTokens.bodyLarge
    var bodyMedium: TextStyle = TypographyTokens.bodyMedium
    var bodySmall: TextStyle = TypographyTokens.bodySmall
    var labelLarge: TextStyle = TypographyTokens.labelLarge
    var labelMedium: TextStyle = TypographyTokens.labelMedium
    var labelSmall: TextStyle = TypographyTokens.labelSmall
}

struct TextStyle {
    var font: SwiftUI.Font
    var lineHeight: CGFloat
    var letterSpacing: CGFloat?
}

struct TypographyTokens {
    static let displayLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 57), lineHeight: 64)
    static let displayMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 45), lineHeight: 52)
    static let displaySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 36), lineHeight: 44)
    static let headlineLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 32), lineHeight: 40)
    static let headlineMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 28), lineHeight: 36)
    static let headlineSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 24), lineHeight: 32)
    static let titleLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 22), lineHeight: 28)
    static let titleMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 16), lineHeight: 24, letterSpacing: 0.15)
    static let titleSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    static let labelLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    static let labelMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 12), lineHeight: 16, letterSpacing: 0.5)
    static let labelSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 11), lineHeight: 16, letterSpacing: 0.5)
    static let bodyLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 16), lineHeight: 24, letterSpacing: 0.15)
    static let bodyMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 14), lineHeight: 20, letterSpacing: 0.25)
    static let bodySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 12), lineHeight: 16, letterSpacing: 0.4)
}
