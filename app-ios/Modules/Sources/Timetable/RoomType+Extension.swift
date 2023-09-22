import shared
import SwiftUI
import Theme

extension RoomType {
    func toColor() -> Color {
        let colorAsset = switch self {
        case .rooma: AssetColors.Custom.hallA
        case .roomb: AssetColors.Custom.hallB
        case .roomc: AssetColors.Custom.hallC
        case .roomd: AssetColors.Custom.hallD
        case .roome: AssetColors.Custom.hallE
        case .roomde: AssetColors.Custom.hallD
        default: AssetColors.Custom.white
        }
        return colorAsset.swiftUIColor
    }
}
