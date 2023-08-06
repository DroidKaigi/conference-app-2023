#!/bin/zsh

brew install openjdk@17

export PATH="/usr/local/opt/openjdk@17/bin:$PATH"

cd "$CI_WORKSPACE/app-ios"
./gradlew :app-ios-shared:assembleSharedReleaseXCFramework
