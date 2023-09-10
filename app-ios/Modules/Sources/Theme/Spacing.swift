import SwiftUI

public struct Spacing {
    var xxxs: CGFloat = SpacingTokens.xxxs
    var xxs: CGFloat = SpacingTokens.xxs
    var xs: CGFloat = SpacingTokens.xs
    var s: CGFloat = SpacingTokens.s
    var m: CGFloat = SpacingTokens.m
    var l: CGFloat = SpacingTokens.l
    var xl: CGFloat = SpacingTokens.xl
}

public struct SpacingTokens {
    static let xxxs: CGFloat = 2
    static let xxs: CGFloat = 4
    static let xs: CGFloat = 8
    static let s: CGFloat = 12
    static let m: CGFloat = 16
    static let l: CGFloat = 20
    static let xl: CGFloat = 24
}
