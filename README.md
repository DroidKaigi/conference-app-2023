# DroidKaigi 2023 official app

DroidKaigi 2023 official app is an app for DroidKaigi 2023.

# UI

## Composable Function Categorization

Composable functions are categorized into three types: Screen, Section, and Component. This categorization does not have a definitive rule, but it serves as a guide for better structure and improved readability.

### Screen

`Screen` refers to an entire screen within your application, typically encapsulating a full user interaction.
Screen and Section are managed with UiState to handle their individual states, which are created by the ViewModel.

```kotlin
data class TimetableScreenUiState(
    val contentUiState: TimetableContentUiState,
    val isFavoriteFilterChecked: Boolean,
)

@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    ...
)
```

### Section

`Section` refers to reusable, dynamic parts of screens, such as lists, which may change significantly based on the app's growth or content variety. An example could be a `TimetableList`.

### Component

`Component` refers to the more granular units of UI that serve specific roles and are less likely to have dynamic content. Examples include `TimetableListItem` and `TimeText`.

Through clear delineation of roles and responsibilities of different composables, this classification assists in enhancing code organization and maintainability.

A Component should not have its own UiState as it could make things overly complicated.

## Advanced Multilanguage System with Kotlin Multiplatform

Our application leverages Kotlin Multiplatform to create a flexible and type-safe system for handling multiple languages. This system exhibits the following key characteristics:

- **Language separation**: Each language is managed separately within its distinct mapping structure, providing a clean and well-structured layout.

- **Type-safe handling of strings**: We leverage Kotlin's sealed classes and enums to represent strings, which are validated at compile-time.

- **Type-safe arguments**: The system allows adding arguments to strings in a type-safe manner, supporting dynamic data inclusion within strings like `data class Time(val hours: Int, val minutes: Int)`

- **Module-specific management**: The system allows managing translations on a per-module basis, enhancing modularity and ease of maintenance.

- **Gradual translation support**: Translations can be added gradually, which is beneficial for evolving projects where translations are continuously updated.

- **Assurance of translation completion**: Kotlin's `when` helps detect missing translations, ensuring completeness of all language representations.

### Code Example:

```kotlin
sealed class SessionsStrings : Strings<SessionsStrings>(Bindings) {
    object Timetable : SessionsStrings()
    object Hoge : SessionsStrings()
    data class Time(val hours: Int, val minutes: Int) : SessionsStrings()
    
    private object Bindings : StringsBindings<SessionsStrings>(
        Lang.Japanese to { item, _ ->
            when (item) {
                Timetable -> "タイムテーブル"
                Hoge -> "ホゲ"
                is Time -> "${item.hour}時${item.minutes}分"
            }
        },
        Lang.English to { item, bindings ->
            when (item) {
                Timetable -> "Timetable"
                // You can use defaultBinding to use default language's string
                Hoge -> bindings.defaultBinding(item, bindings)
                is Time -> "${item.hour}:${item.minutes}"
            }
        },
        default = Lang.Japanese
    )
}
```
In the above example, `SessionsStrings` is a sealed class that represents different strings. Each string is defined as an object within the sealed class, and the translations are provided in `StringsBindings`.

To fetch a string:

```kotlin
println(SessionsStrings.Timetable.asString())
```

# Build / CI

This project runs on GitHub Actions. This year's workflows contain new challenges!

## Provide the same CI experiences for the both of members and contributors(you!)

This projects is an OSS so we cannot assign write-able tokens to workflow-runs that need the codes of the forked repos. To solve this problem, this project shares artifacts with multiple workflows via artifacts API and use them in *safe* workflows that have more-powerful permission but consist of safe actions.

This achieves to post comments on forked PRs safely. For example, you can see the results of the visual tesing reports even on your PRs! (See [Architecture > Testing](#testing) for the visual testing).

## WIP - Automatic dependency updates

> This workflow is disabled now.

We continue to use Renovate to update dependencies. [./.github/workflows/Renovate.yml](./.github/workflows/Renovate.yml) allows us to update some dependencies whose impacts seem to be low automatically.

# Architecture

## Overview of the architecture

![architecture diagram](https://github.com/DroidKaigi/conference-app-2023/assets/1386930/c9d8ff0f-0f2e-44a5-9631-c785d1565255)


## Single Source of Truth with buildUiState() {}
The buildUiState() {} function promotes the Single Source of Truth (SSoT) principle in our application by combining multiple StateFlow objects into a single UI state. This ensures that data is managed and accessed from a single, consistent, and reliable source.

By working with StateFlow objects, the function can also compute initial values, further enhancing the SSOT principle.

Here's an example of using the buildUiState() function:

```kotlin
private val filterUiState: StateFlow<FilterUiState> = buildUiState(
    sessionsStateFlow,
    filtersStateFlow
) { sessions, filters ->
    FilterUiState(
        enabled = sessions.sessions.isNotEmpty(),
        isChecked = filters.filterFavorites
    )
}
```

The buildUiState() function combines the data from sessionsStateFlow and filtersStateFlow into a single filterUiState instance. This simplifies state management and ensures that the UI always displays consistent and up-to-date information.

## Testing

Testing an app involves balancing fidelity, how closely the test resembles actual use, and reliability, the consistency of test results. This year, our goal is to improve both using several methods.

### Screenshot Testing with Robolectric Native Graphics (RNG) and Roborazzi

Robolectric Native Graphics (RNG) allows us to take app screenshots without needing an emulator or a device. This approach is faster and more reliable than taking device screenshots. While device screenshots may replicate real-world usage slightly more accurately, we believe the benefits of RNG's speed and reliability outweigh this. 
We use Roborazzi to compare the current app's screenshots to the old ones, allowing us to spot and fix any visual changes.

#### Balancing Screenshot Tests and Assertion Tests
Screenshot tests are extremely effective as they allow us to spot visual changes without writing many assertions. However, there is a risk of mistakenly using incorrect baseline images.  
So, for important features, we should add assertion tests to these parts. The tests will typically look like this:

```kotlin
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne
)
class TimetableScreenTest {

    @get:Rule
    val robotTestRule = RobotTestRule(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    // A screenshot test
    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableScreenRobot(robotTestRule) {
            checkCaptureScreen()
        }
    }

    // An assertion test for an important feature
    @Test
    fun checkLaunch() {
        timetableScreenRobot(robotTestRule) {
            checkTimetableItemsDisplayed()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFavoriteToggleShot() {
        timetableScreenRobot(robotTestRule) {
            clickFirstSessionFavorite()
            checkCaptureTimetableContent()
            clickFirstSessionFavorite()
            checkCaptureTimetableContent()
        }
    }
    ...
}
```

#### The Companion Branch Approach

We use the [companion branch approach](https://github.com/DroidKaigi/conference-app-2022/pull/616) to store screenshots of feature branches. This method involves saving screenshots to a companion branch whenever a pull request is made, ensuring that we keep only relevant images and reduce the repository size.

<img src="https://user-images.githubusercontent.com/1386930/236188326-ddd617ae-b216-476c-9d92-e36ad02a2670.png" width="600" />

* Why not GitHub Actions Artifacts, Git LFS, or Feature Branch Commits?

While GitHub Actions Artifacts and Git LFS could be used for storing screenshots, they don't allow for direct image viewing in pull requests. Committing screenshots directly to the feature branch, on the other hand, can lead to an unnecessary increase in the repository size.

### Testing Robot Pattern

The Testing Robot Pattern simplifies writing UI tests. It splits the test code into two parts: 'how to test', handled by the robot class, and 'what to test', managed by the test class. This separation is beneficial for writing screenshot tests and makes the test code more maintainable and easier to read.

# iOS

## Requirements

1. You need to install the following tools.

- JDK 17
  - You can install via [SDKMAN](https://sdkman.io/), 
  - `sdk install $(cat .sdkmanrc | sed -e 's/=/ /')`
- Xcode, [`.xcode-version`](.xcode-version) version
  - You can install via [Xcodes](https://www.xcodes.app/)
- Ruby, [`.ruby-version`](.ruby-version) version
  - bundler (you can install by `gem install bundler` or `sudo gem install bundler`)

2. Setup
   1. `bundle install`
   2. `bundle exec fastlane build-kmm`
   
3. open [`app-ios.xcworkspace`](./app-ios/app-ios.xcworkspace) by Xcode

## Build

- You can filter XCFramework arch by `arch` option at [`local.properties`](./local.properties)
  - e.g. if you need only `x86_64` binary, you can set `arch=x86_64`

## Debug

- You can build and debug on Android Studio with [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
- After install KMM plugin, you can see `app-ios` module on Android Studio's run configurations.
  - ![Run Configuration](https://github.com/DroidKaigi/conference-app-2023/assets/32740480/4f076561-8bf2-4595-a9aa-4fc03ccc09e1)
- Set configs like below
  - ![config](https://github.com/DroidKaigi/conference-app-2023/assets/32740480/afb6b975-db98-49e1-bc2d-ad55222baadd)

# Special thanks

 - Contributors of [DroidKaigi 2023 official app](https://github.com/DroidKaigi/conference-app-2023/graphs/contributors)
 - UI Lead: [upon0426](https://github.com/upon0426)
 - Build/CI Lead: [tomoya0x00](https://github.com/tomoya0x00)
 - Designer: TBD
 - iOS Lead: [ry-itto](https://github.com/ry-itto)
 - Server / API Lead: [ryunen344](https://github.com/ryunen344)
 - DroidKaigi Co-Organizer / Architecture Lead: [takahirom](https://github.com/takahirom)
