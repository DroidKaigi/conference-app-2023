import SwiftUI

public typealias ViewBuilder<each Params, V: View> = (_ params: repeat each Params) -> V
