import Assets
import shared
import SwiftUI
import Theme

struct SessionDescriptionView: View {
    @State private var isDescriptionExpanded: Bool = false
    @State private var canBeExpanded: Bool = false
    let content: String

    init(content: String) {
        self.content = content
    }

    var body: some View {
        VStack(alignment: .leading, spacing: SpacingTokens.m) {
            Text(.init(content))
                .textStyle(TypographyTokens.bodyLarge)
                .textSelection(.enabled)
                .lineLimit(isDescriptionExpanded ? nil : 5)
                .background {
                    ViewThatFits(in: .vertical) {
                        Text(content)
                            .textStyle(TypographyTokens.bodyLarge)
                            .hidden()
                        // Just for receiving onAppear event if the description exceeds its line limit
                        Color.clear
                            .onAppear {
                                canBeExpanded = true
                            }
                    }
                }
            if canBeExpanded {
                Button {
                    isDescriptionExpanded = true
                    canBeExpanded = false
                } label: {
                    Text(L10n.Session.readMore)
                        .textStyle(TypographyTokens.labelLarge)
                        .foregroundStyle(AssetColors.Primary.primary.swiftUIColor)
                        .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
                        .overlay {
                            Capsule()
                                .stroke(AssetColors.Outline.outline.swiftUIColor)
                        }
                }
            }
        }
    }
}

#Preview {
    SessionDescriptionView(content: TimetableItem.Session.companion.fake().description_.currentLangTitle)
}
