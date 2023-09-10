import SwiftUI

public struct Typography {
    public var displayLarge: TextStyle = TypographyTokens.displayLarge
    public var displayMedium: TextStyle = TypographyTokens.displayMedium
    public var displaySmall: TextStyle = TypographyTokens.displaySmall
    public var headlineLarge: TextStyle = TypographyTokens.headlineLarge
    public var headlineMedium: TextStyle = TypographyTokens.headlineMedium
    public var headlineSmall: TextStyle = TypographyTokens.headlineSmall
    public var titleLarge: TextStyle = TypographyTokens.titleLarge
    public var titleMedium: TextStyle = TypographyTokens.titleMedium
    public var titleSmall: TextStyle = TypographyTokens.titleSmall
    public var bodyLarge: TextStyle = TypographyTokens.bodyLarge
    public var bodyMedium: TextStyle = TypographyTokens.bodyMedium
    public var bodySmall: TextStyle = TypographyTokens.bodySmall
    public var labelLarge: TextStyle = TypographyTokens.labelLarge
    public var labelMedium: TextStyle = TypographyTokens.labelMedium
    public var labelSmall: TextStyle = TypographyTokens.labelSmall
}

public struct TextStyle {
    public var font: SwiftUI.Font
    public var lineHeight: CGFloat
    public var letterSpacing: CGFloat?
}

public struct TypographyTokens {
    public static let displayLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 57), lineHeight: 64)
    public static let displayMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 45), lineHeight: 52)
    public static let displaySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 36), lineHeight: 44)
    public static let headlineLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 32), lineHeight: 40)
    public static let headlineMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 28), lineHeight: 36)
    public static let headlineSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Medium", size: 24), lineHeight: 32)
    public static let titleLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 22), lineHeight: 28)
    public static let titleMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 16), lineHeight: 24, letterSpacing: 0.15)
    public static let titleSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    public static let labelLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 14), lineHeight: 20, letterSpacing: 0.1)
    public static let labelMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 12), lineHeight: 16, letterSpacing: 0.5)
    public static let labelSmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-SemiBold", size: 11), lineHeight: 16, letterSpacing: 0.5)
    public static let bodyLarge = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 16), lineHeight: 24, letterSpacing: 0.15)
    public static let bodyMedium = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 14), lineHeight: 20, letterSpacing: 0.25)
    public static let bodySmall = TextStyle(font: SwiftUI.Font.custom("Montserrat-Regular", size: 12), lineHeight: 16, letterSpacing: 0.4)
}
