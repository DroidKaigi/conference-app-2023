import SwiftUI

struct InfoLabel: View {
    
    private let label: String
    private let labelColor: Color
    private let strokeColor: Color?
    private let backgroundColor: Color
    
    public init(
        _ label: String,
        labelColor: Color,
        strokeColor: Color? = nil,
        backgroundColor: Color = .clear
    ) {
        self.label = label
        self.labelColor = labelColor
        self.strokeColor = strokeColor
        self.backgroundColor = backgroundColor
    }
    
    var body: some View {
        Text(label)
            .font(Font.system(size: 12, weight: .medium))
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .foregroundColor(labelColor)
            .background(backgroundColor)
            .cornerRadius(50)
            .overlay {
                if let strokeColor = strokeColor {
                    RoundedRectangle(cornerRadius: 50)
                        .inset(by: 0.5)
                        .stroke(strokeColor, lineWidth: 1)
                } else {
                    EmptyView()
                }
            }
    }
}
