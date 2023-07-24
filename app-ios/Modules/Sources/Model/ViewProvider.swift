import SwiftUI

public typealias ViewProvider<each Params, V: View> = (_ params: repeat each Params) -> V
