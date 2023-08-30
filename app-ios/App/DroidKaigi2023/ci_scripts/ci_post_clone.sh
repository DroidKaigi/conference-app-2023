#!/bin/zsh

brew install openjdk@17

export PATH="/usr/local/opt/openjdk@17/bin:$PATH"

cd "$CI_WORKSPACE"
# echo "arch=arm64" >> local.properties
./gradlew :app-ios-shared:assembleSharedReleaseXCFramework

# Enalbe skip plugin validation
defaults write com.apple.dt.Xcode IDESkipPackagePluginFingerprintValidatation -bool YES

# workaround for xcode cloud
brew install mint

mint install cybozu/LicenseList
mint install SwiftGen/SwiftGen

mint run LicenseList $CI_DERIVED_DATA_PATH $CI_DERIVED_DATA_PATH

export DERIVED_SOURCES_DIR="$CI_WORKSPACE/app-ios/Modules/Sources/Assets/"
mint run SwiftGen --config "$CI_WORKSPACE/app-ios/Modules/Sources/Assets/swiftgen.yml"

export DERIVED_SOURCES_DIR="$CI_WORKSPACE/app-ios/Modules/Sources/Theme/"
mint run SwiftGen --config "$CI_WORKSPACE/app-ios/Modules/Sources/Theme/swiftgen.yml"
