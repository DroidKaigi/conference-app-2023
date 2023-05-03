// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "Modules",
    products: [
        .library(name: "Session", targets: ["Session"]),
    ],
    dependencies: [
        .package(url: "https://github.com/apple/swift-collections.git", from: "1.0.4"),
        .package(url: "https://github.com/apple/swift-algorithms.git", from: "1.0.0"),
        .package(url: "https://github.com/apple/swift-async-algorithms.git", from: "0.1.0"),
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
            ]
        ),
        .testTarget(
            name: "CoreTests",
            dependencies: [
                "Core"
            ]
        ),
        
        // TODO setup KMM as NativeTarget
    ]
)
