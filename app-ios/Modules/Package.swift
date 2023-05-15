// swift-tools-version: 5.8
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "Modules",
    platforms: [
        .iOS(.v16),
    ],
    products: [
        .library(name: "Session", targets: ["Session"]),
        .library(name: "Core", targets: ["Core"])
    ],
    dependencies: [
        .package(url: "https://github.com/apple/swift-collections.git", branch: "1.0.4"),
        .package(url: "https://github.com/apple/swift-algorithms.git", branch: "1.0.0"),
        .package(url: "https://github.com/apple/swift-async-algorithms.git", branch: "0.1.0"),
    ],
    targets: [
        .target(
            name: "Session",
            dependencies: [
                "Core"
            ]
        ),
        .testTarget(
            name: "SessionTests",
            dependencies: [
                "Session"
            ]
        ),

        .target(
            name: "Core",
            dependencies: [
                "shared"
            ]
        ),
        .testTarget(
            name: "CoreTests",
            dependencies: [
                "Core"
            ]
        ),
        
        .binaryTarget(
            name: "shared",
            path: "../../app-ios-shared/build/XCFrameworks/release/shared.xcframework"
        )
    ]
)
