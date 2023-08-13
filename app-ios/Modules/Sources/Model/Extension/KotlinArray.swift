import shared

public extension Array where Element: NSObject {
    static func fromKotlinArray(_ kotlinArray: KotlinArray<Element>) -> Self {
        (0..<kotlinArray.size).compactMap { index in
            kotlinArray.get(index: index)
        }
    }
}
