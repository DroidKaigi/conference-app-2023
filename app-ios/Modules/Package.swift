// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

var package = Package(
    name: "Modules",
    platforms: [
        .iOS(.v16),
    ],
    products: [
        .library(name: "Session", targets: ["Session"]),
        .library(name: "Timetable", targets: ["Timetable"]),
        .library(name: "Navigation", targets: ["Navigation"]),
    ],
    dependencies: [
        .package(url: "https://github.com/apple/swift-collections.git", from: "1.0.4"),
        .package(url: "https://github.com/apple/swift-algorithms.git", from: "1.0.0"),
        .package(url: "https://github.com/apple/swift-async-algorithms.git", from: "0.1.0"),
        .package(url: "https://github.com/realm/SwiftLint", from: "0.52.2"),
    ],
    targets: [
        .target(
            name: "About",
            dependencies: [
                "shared",
            ]
        ),
        .testTarget(
            name: "AboutTests",
            dependencies: [
                "About",
            ]
        ),

        .target(
            name: "FloorMap",
            dependencies: [
                "shared",
            ]
        ),
        .testTarget(
            name: "FloorMapTests",
            dependencies: [
                "FloorMap",
            ]
        ),

        .target(
            name: "Session",
            dependencies: [
                "shared",
            ]
        ),
        .testTarget(
            name: "SessionTests",
            dependencies: [
                "Session"
            ]
        ),

        .target(
            name: "Stamps",
            dependencies: [
                "shared",
            ]
        ),
        .testTarget(
            name: "StampsTests",
            dependencies: [
                "Stamps"
            ]
        ),

        .target(
            name: "Timetable",
            dependencies: [
                "shared",
                "Model",
            ]
        ),
        .testTarget(
            name: "TimetableTests",
            dependencies: [
                "Timetable"
            ]
        ),

        .target(
            name: "Navigation",
            dependencies: [
                "About",
                "FloorMap",
                "Session",
                "Stamps",
                "Timetable",
            ]
        ),

        .target(
            name: "Model",
            dependencies: [
                "shared",
            ]
        ),

        .binaryTarget(
            name: "shared",
            path: "../../app-ios-shared/build/XCFrameworks/release/shared.xcframework"
        )
    ]
)

// Append common plugins
package.targets = package.targets.map { target -> Target in
    if target.type == .regular || target.type == .test {
        if target.plugins == nil {
            target.plugins = []
        }
        target.plugins?.append(.plugin(name: "SwiftLintPlugin", package: "SwiftLint"))
    }

    return target
}

// Enable experimental features for each targets
package.targets = package.targets.map { target in
    if target.type == .regular || target.type == .test {
        target.swiftSettings = [
            .enableExperimentalFeature("VariadicGenerics")
        ]
    }

    return target
}
