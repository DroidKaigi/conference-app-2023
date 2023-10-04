#!/bin/zsh

cd ../
./gradlew :app-io-shared:assembleSharedReleaseXCFramework
# Debug does not appear to be in use now.
#./gradlew :app-io-shared:assembleSharedDebugXCFramework
echo -e "\a"
