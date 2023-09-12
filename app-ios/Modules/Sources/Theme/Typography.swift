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
    public static let displayLarge = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 57), lineHeight: 64 - 57)
    public static let displayMedium = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 45), lineHeight: 52 - 45)
    public static let displaySmall = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 36), lineHeight: 44 - 36)
    public static let headlineLarge = TextStyle(font: FontAssets.Montserrat.medium.swiftUIFont(size: 32), lineHeight: 40 - 32)
    public static let headlineMedium = TextStyle(font: FontAssets.Montserrat.medium.swiftUIFont(size: 28), lineHeight: 36 - 28)
    public static let headlineSmall = TextStyle(font: FontAssets.Montserrat.medium.swiftUIFont(size: 24), lineHeight: 32 - 24)
    public static let titleLarge = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 22), lineHeight: 28 - 22)
    public static let titleMedium = TextStyle(font: FontAssets.Montserrat.semiBold.swiftUIFont(size: 16), lineHeight: 24 - 16, letterSpacing: 0.15)
    public static let titleSmall = TextStyle(font: FontAssets.Montserrat.semiBold.swiftUIFont(size: 14), lineHeight: 20 - 14, letterSpacing: 0.1)
    public static let labelLarge = TextStyle(font: FontAssets.Montserrat.semiBold.swiftUIFont(size: 14), lineHeight: 20 - 14, letterSpacing: 0.1)
    public static let labelMedium = TextStyle(font: FontAssets.Montserrat.semiBold.swiftUIFont(size: 12), lineHeight: 16 - 12, letterSpacing: 0.5)
    public static let labelSmall = TextStyle(font: FontAssets.Montserrat.semiBold.swiftUIFont(size: 11), lineHeight: 16 - 11, letterSpacing: 0.5)
    public static let bodyLarge = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 16), lineHeight: 24 - 16, letterSpacing: 0.15)
    public static let bodyMedium = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 14), lineHeight: 20 - 14, letterSpacing: 0.25)
    public static let bodySmall = TextStyle(font: FontAssets.Montserrat.regular.swiftUIFont(size: 12), lineHeight: 16 - 12, letterSpacing: 0.4)
}
