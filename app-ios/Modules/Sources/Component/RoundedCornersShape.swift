import SwiftUI

public struct RoundedCornersShape: Shape {
    public var corners: UIRectCorner
    public var cornerSize: CGSize

    public init(corners: UIRectCorner = .allCorners, cornerSize: CGSize) {
        self.corners = corners
        self.cornerSize = cornerSize
    }

    public init(corners: UIRectCorner = .allCorners, cornerRadius: CGFloat) {
        self.corners = corners
        self.cornerSize = CGSize(width: cornerRadius, height: cornerRadius)
    }

    public func path(in rect: CGRect) -> Path {
        Path(UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: cornerSize).cgPath)
    }
}
