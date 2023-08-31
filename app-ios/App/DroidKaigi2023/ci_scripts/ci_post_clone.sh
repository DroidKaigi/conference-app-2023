#!/bin/zsh

cd "$CI_WORKSPACE"

brew install openjdk@17

export PATH="/usr/local/opt/openjdk@17/bin:$PATH"

./gradlew :app-ios-shared:assembleSharedReleaseXCFramework
