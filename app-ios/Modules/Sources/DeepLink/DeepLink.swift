import CryptoKit
import Dependencies
import FirebaseDynamicLinks
import KMPContainer
import shared

public class DeepLink {
    @Dependency(\.achievementData) var achievementData

    public init() {}

    public func dynamicLink(fromUniversalLink url: URL) async throws -> DynamicLink? {
        try await DynamicLinks.dynamicLinks().dynamicLink(fromUniversalLink: url)
    }

    public func dynamicLink(customURL url: URL) -> DynamicLink? {
        DynamicLinks.dynamicLinks().dynamicLink(fromCustomSchemeURL: url)
    }

    public func dynamicLink(shortLink: URL) async throws -> DynamicLink? {
        let resolvedLink = try await resolveShortLink(url: shortLink)
        return dynamicLink(customURL: resolvedLink)
    }

    public func resolveShortLink(url: URL) async throws -> URL {
        try await DynamicLinks.dynamicLinks().resolveShortLink(url)
    }

    @discardableResult
    public func handleURL(url: URL) async throws -> Achievement? {
        let hashedString = self.idToSha256(id: url.lastPathComponent)
        let target: Achievement? = switch hashedString {
        case Achievement.arcticfox.sha256:
            Achievement.arcticfox
        case Achievement.bumblebee.sha256:
            Achievement.bumblebee
        case Achievement.chipmunk.sha256:
            Achievement.chipmunk
        case Achievement.dolphin.sha256:
            Achievement.dolphin
        case Achievement.electriceel.sha256:
            Achievement.electriceel
        default:
            nil
        }

        if let achievement = target {
            try await self.achievementData.saveAchievement(achievement)
        }

        return target
    }

    @discardableResult
    public func handleDynamicLink(dynamicLink: DynamicLink) async throws -> Achievement? {
        return try await handleURL(url: dynamicLink.url!)
    }

    private func idToSha256(id: String) -> String? {
        guard let data = id.data(using: .utf8) else {
            return nil
        }
        let digest = SHA256.hash(data: data)
        return digest.compactMap { String(format: "%02x", $0) }.joined()
    }
}
