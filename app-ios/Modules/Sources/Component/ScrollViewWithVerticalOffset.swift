import SwiftUI

// Quote & Modify:
// https://gist.github.com/Kievkao/0682dc8814a953640ca9b74413424bb8

public struct ScrollViewWithVerticalOffset<Content: View>: View {

    let onOffsetChange: (CGFloat) -> Void
    let content: () -> Content

    public init(
        onOffsetChange: @escaping (CGFloat) -> Void,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.onOffsetChange = onOffsetChange
        self.content = content
    }

    public var body: some View {
        ScrollView(.vertical) {
            offsetReader
            content()
                .padding(.top, 0)
        }
        .coordinateSpace(name: "frameLayer")
        .onPreferenceChange(OffsetPreferenceKey.self, perform: onOffsetChange)
    }

    private var offsetReader: some View {
        GeometryReader { proxy in
            Color.clear
                .preference(
                    key: OffsetPreferenceKey.self,
                    value: proxy.frame(in: .named("frameLayer")).minY
                )
        }
        .frame(height: 0)
    }
}

private struct OffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = .zero
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {}
}
