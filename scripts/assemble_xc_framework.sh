#!/bin/zsh

cd ../
./gradlew :app-io-shared:assembleSharedReleaseXCFramework
./gradlew :app-io-shared:assembleSharedDebugXCFramework 
echo -e "\a"