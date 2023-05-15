// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "Plugins",
    platforms: [
        .macOS(.v13),
    ],
    products: [
        .executable(name: "FastlaneRunner", targets: ["FastlaneRunner"]),
    ],
    dependencies: [
        .package(url: "https://github.com/fastlane/fastlane.git", branch: "2.212.2")
    ],
    targets: [
        .executableTarget(
            name: "FastlaneRunner",
            dependencies: [
                .product(name: "Fastlane", package: "fastlane"),
            ]
        ),
        .testTarget(
            name: "FastlaneRunnerTests",
            dependencies: [
               "FastlaneRunner"
            ]
        ),
    ]
)
