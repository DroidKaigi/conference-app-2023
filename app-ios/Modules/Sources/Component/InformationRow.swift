import SwiftUI
import Theme

public struct InformationRow: View {
    private let icon: Image
    private let title: String
    private let content: String
    private let action: Action?

    public struct Action {
        let label: String
        let action: () -> Void

        public init(label: String, action: @escaping () -> Void) {
            self.label = label
            self.action = action
        }
    }

    public init(
        icon: Image,
        title: String,
        content: String,
        action: Action? = nil
    ) {
        self.icon = icon
        self.title = title
        self.content = content
        self.action = action
    }

    public var body: some View {
        HStack {
            icon
            HStack(spacing: SpacingTokens.s) {
                Text(title)
                    .textStyle(TypographyTokens.labelLarge)
                HStack {
                    Text(content)
                        .textStyle(TypographyTokens.bodyMedium)
                    if let action = action {
                        Button {
                            action.action()
                        } label: {
                            Text(action.label)
                                .textStyle(TypographyTokens.bodyMedium)
                                .underline()
                                .foregroundStyle(
                                    AssetColors.Primary.primary.swiftUIColor
                                )
                        }
                    }
                }

            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .foregroundStyle(
            AssetColors.Surface.onSurfaceVariant.swiftUIColor
        )
    }
}

#Preview {
    InformationRow(
        icon: Image(systemName: "clock"),
        title: "日付",
        content: "2023.09.14 / 11:20 ~ 12:00 (40分)",
        action: .init(
            label: "地図を見る",
            action: {}
        )
    )
}
