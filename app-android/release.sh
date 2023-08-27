#!/bin/bash
cd "$(dirname "$0")"
cp $1/keystore.properties .
cp $1/droidkaigi2023.keystore .
mkdir src/prod/
cp $1/google-services.json src/prod/google-services.json

cd ..
# ./gradlew :benchmark:pixel2Api31BenchmarkAndroidTest
# cp benchmark/build/outputs/managed_device_android_test_additional_output/pixel2Api31/BaselineProfileGenerator_startup-baseline-prof.txt app-android/src/main/baseline-prof.txt

./gradlew app-android:assembleProdRelease
./gradlew app-android:bundleProdRelease
