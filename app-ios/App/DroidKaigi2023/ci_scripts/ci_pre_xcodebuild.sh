#!/bin/zsh

export PATH="/usr/local/opt/openjdk@17/bin:$PATH"

cd "$CI_WORKSPACE/app-ios"
./gradlew :app-ios-shared:assembleSharedReleaseXCFramework
