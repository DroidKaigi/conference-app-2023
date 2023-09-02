#!/bin/sh

cd "$CI_WORKSPACE"

# Enalbe skip plugin validation
defaults write com.apple.dt.Xcode IDESkipPackagePluginFingerprintValidatation -bool YES

# workaround for xcode cloud
brew install mint

mint install cybozu/LicenseList
mint install SwiftGen/SwiftGen

SOURCE_PACKAGES_PATH="$CI_WORKSPACE/app-ios/SourcePackages"
xcodebuild -resolvePackageDependencies -project $CI_PROJECT_FILE_PATH -scheme DroidKaigi2023 -derivedDataPath $CI_DERIVED_DATA_PATH -clonedSourcePackagesDirPath $SOURCE_PACKAGES_PATH

mint run LicenseList "$CI_WORKSPACE/app-ios/Modules/Sources/About/Resources" $SOURCE_PACKAGES_PATH

export DERIVED_SOURCES_DIR="$CI_WORKSPACE/app-ios/Modules/Sources/Assets/"
mint run SwiftGen --config "$CI_WORKSPACE/app-ios/Modules/Sources/Assets/swiftgen.yml"

export DERIVED_SOURCES_DIR="$CI_WORKSPACE/app-ios/Modules/Sources/Theme/"
mint run SwiftGen --config "$CI_WORKSPACE/app-ios/Modules/Sources/Theme/swiftgen.yml"
