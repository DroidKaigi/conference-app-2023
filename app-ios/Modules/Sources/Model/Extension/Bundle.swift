import Foundation

public extension Bundle {

    var shortVersionString: String {
        infoDictionary?["CFBundleShortVersionString"] as? String ?? ""
    }

    var buildNumber: String {
        infoDictionary?["CFBundleVersion"] as? String ?? ""
    }

    var formattedVersion: String {
        "\(shortVersionString) (\(buildNumber))"
    }

}
