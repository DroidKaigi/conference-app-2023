import SwiftUI

public struct CacheAsyncImage<Content, PlaceHolder>: View where Content: View, PlaceHolder: View {
    
    private let url: URL?
    private let scale: CGFloat
    private let contentImage: (Image) -> Content
    private let placeholder: () -> PlaceHolder
    
    public init(
        url: URL?,
        scale: CGFloat = 1.0,
        contentImage: @escaping (Image) -> Content,
        placeholder: @escaping () -> PlaceHolder
    ) {
        self.url = url
        self.scale = scale
        self.contentImage = contentImage
        self.placeholder = placeholder
    }
    
    public var body: some View {
        if let cachedImage = ImageCache[url] {
            contentImage(cachedImage)
        } else {
            AsyncImage(
                url: url,
                scale: scale,
                content: { image in
                    cacheAndRender(image: image)
                },
                placeholder: placeholder
            )
        }
    }
}

private extension CacheAsyncImage {
    func cacheAndRender(image: Image) -> some View {
        ImageCache[url] = image
        return contentImage(image)
    }
}

private class ImageCache {
    static private var cache: [URL: Image] = [:]
    
    static subscript(url: URL?) -> Image? {
        get {
            guard let url else { return nil }
            return Self.cache[url]
        }
        set {
            guard let url else { return }
            Self.cache[url] = newValue
        }
    }
}

#Preview {
    CacheAsyncImage(
        url: URL(string: "https://avatars.githubusercontent.com/u/10727543?s=48&v=4")!,
        scale: 1.0
    ) { image in
        image.resizable()
            .frame(width: 24, height: 24)
        } placeholder: {
            Color.gray
                .frame(width: 24, height: 24)
                .cornerRadius(12)
        }

}
