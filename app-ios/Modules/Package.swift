// swift-tools-version: 5.9
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

var package = Package(
    name: "Modules",
    platforms: [
        .iOS(.v16),
    ],
    products: [
        .library(name: "Component", targets: ["Component"]),
        .library(name: "FloorMap", targets: ["FloorMap"]),
        .library(name: "Session", targets: ["Session"]),
        .library(name: "Sponsor", targets: ["Sponsor"]),
        .library(name: "Timetable", targets: ["Timetable"]),
        .library(name: "Navigation", targets: ["Navigation"]),
    ],
    dependencies: [
        .package(url: "https://github.com/realm/SwiftLint", from: "0.52.4"),
        .package(url: "https://github.com/SwiftGen/SwiftGenPlugin", from: "6.6.2"),
        .package(url: "https://github.com/pointfreeco/swift-dependencies", from: "1.0.0"),
        .package(url: "https://github.com/cybozu/LicenseList", from: "0.2.1"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk", from: "10.14.0"),
        .package(url: "https://github.com/airbnb/lottie-ios", from: "4.2.0"),
    ],
    targets: [
        .target(
            name: "About",
            dependencies: [
                "Assets",
                "Component",
                "Model",
                .product(name: "LicenseList", package: "LicenseList")
            ],
            plugins: [
                .plugin(name: "PrepareLicenseList", package: "LicenseList")
            ]
        ),
        .testTarget(
            name: "AboutTests",
            dependencies: [
                "About",
            ]
        ),

        .target(
            name: "Assets",
            dependencies: [
                .product(name: "Lottie", package: "lottie-ios"),
            ], resources: [
                .process("Resources"),
                .process("swiftgen.yml"),
            ],
            plugins: [
                .plugin(name: "SwiftGenPlugin", package: "SwiftGenPlugin"),
            ]
        ),

        .target(
            name: "Auth",
            dependencies: [
                "shared",
                .product(name: "FirebaseAuth", package: "firebase-ios-sdk"),
            ]
        ),

        .target(
            name: "Component",
            dependencies: [
                "Theme",
            ]
        ),
        .testTarget(
            name: "ComponentTests",
            dependencies: [
                "Component",
            ]
        ),

        .target(
            name: "Contributor",
            dependencies: [
                "Assets",
                "Component",
                "KMPContainer",
                "Model",
                .product(name: "Dependencies", package: "swift-dependencies"),
            ]
        ),
        .testTarget(
            name: "ContributorTests",
            dependencies: [
                "Contributor",
            ]
        ),

        .target(
            name: "FloorMap",
            dependencies: [
                "Assets",
                "Theme",
                "Component",
                "KMPContainer"
            ]
        ),
        .testTarget(
            name: "FloorMapTests",
            dependencies: [
                "FloorMap",
            ]
        ),

        .target(
            name: "KMPContainer",
            dependencies: [
                "Auth",
                "shared",
                "RemoteConfig",
                .product(name: "Dependencies", package: "swift-dependencies"),
            ]
        ),
        .testTarget(
            name: "KMPContainerTests",
            dependencies: [
                "KMPContainer",
            ]
        ),

        .target(
            name: "Session",
            dependencies: [
                "Assets",
                "Component",
                "KMPContainer",
                "Model",
            ]
        ),
        .testTarget(
            name: "SessionTests",
            dependencies: [
                "Session"
            ]
        ),

        .target(
            name: "Sponsor",
            dependencies: [
                "Assets",
                "Component",
                "KMPContainer",
                "Model",
                "shared",
                .product(name: "Dependencies", package: "swift-dependencies"),
            ]
        ),
        .testTarget(
            name: "SponsorTests",
            dependencies: [
                "Sponsor"
            ]
        ),

        .target(
            name: "Staff",
            dependencies: [
                "Assets",
                "Component",
                "KMPContainer",
                "Model",
                "shared",
                .product(name: "Dependencies", package: "swift-dependencies"),
            ]
        ),
        .testTarget(
            name: "StaffTests",
            dependencies: [
                "Staff"
            ]
        ),

        .target(
            name: "Stamps",
            dependencies: [
                "Assets",
                "Theme",
                "KMPContainer",
                .product(name: "Dependencies", package: "swift-dependencies"),
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
                "Assets",
                "Component",
                "KMPContainer",
                "Model",
                .product(name: "Dependencies", package: "swift-dependencies"),
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
                "Assets",
                "Contributor",
                "FloorMap",
                "Session",
                "Sponsor",
                "Staff",
                "Stamps",
                "Theme",
                "Timetable",
            ]
        ),

        .target(
            name: "RemoteConfig",
            dependencies: [
                "shared",
                .product(name: "FirebaseRemoteConfig", package: "firebase-ios-sdk"),
            ]
        ),

        .target(
            name: "Model",
            dependencies: [
                "shared",
            ]
        ),
        .testTarget(
            name: "ModelTests",
            dependencies: [
                "Model",
            ]
        ),

        .target(
            name: "Theme",
            resources: [
                .process("Resources"),
                .process("swiftgen.yml"),
            ],
            plugins: [
                .plugin(name: "SwiftGenPlugin", package: "SwiftGenPlugin"),
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
