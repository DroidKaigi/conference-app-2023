#!/bin/zsh

brew install openjdk@17

export PATH="/usr/local/opt/openjdk@17/bin:$PATH"

cd "$CI_WORKSPACE"
./gradlew :app-ios-shared:assembleSharedReleaseXCFramework

# Enalbe skip plugin validation
defaults write com.apple.dt.Xcode IDESkipPackagePluginFingerprintValidatation -bool YES
