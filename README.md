<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://user-images.githubusercontent.com/136104152/264391682-ae2b1067-966f-47a2-aa10-4e1ee4d09418.png">
  <source media="(prefers-color-scheme: light)" srcset="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/22efb1a7-5a43-469c-a493-fa619034863e">
  <img alt="readme-banner" src="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/22efb1a7-5a43-469c-a493-fa619034863e">
</picture>

# DroidKaigi 2023 official app

[DroidKaigi 2023](https://2023.droidkaigi.jp/) will be held from September 14 to September 16, 2023. We are developing its application. Let's develop the app together and make it exciting.

# Features

This is a video of an app in development, and it will be updated as needed.

<video src="https://github.com/DroidKaigi/conference-app-2023/assets/13657682/ec91d1b8-639d-4622-8371-1387be4c6bac" width="320" controls>
  https://github.com/DroidKaigi/conference-app-2023/assets/13657682/ec91d1b8-639d-4622-8371-1387be4c6bac
</video>

# Try it out!

## Android

The app is currently in preparation for release on Google Play and the App Store. In the meantime, you can try the app on DeployGate. Stay tuned for updates!

[<img src="https://dply.me/fim4ik/button/large" alt="Try it on your device via DeployGate">](https://dply.me/fim4ik#install)

## iOS

Beta version of this app is available on TestFlight.
You can try and test this app with following public link.

https://testflight.apple.com/join/1dxiwjX0

In this project, distribute beta version with [Xcode Cloud](https://developer.apple.com/jp/xcode-cloud/).
# Contributing

We always welcome any and all contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

For Japanese speakers, please see [CONTRIBUTING.ja.md](CONTRIBUTING.ja.md).

# Requirements
Stable Android Studio Giraffe or higher. You can download it from [this page](https://developer.android.com/studio).

# Design

You can check out the design on Figma.

https://www.figma.com/file/MbElhCEnjqnuodmvwabh9K/DroidKaigi-2023-App-UI

<img src="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/bf32d1ad-774b-4a5d-88b3-dab8c9f0ef15" alt="Design thumbnail" width="480" />

# Architecture

## Overview of the architecture

In addition to general Android practices, we are exploring and implementing various concepts. Details for each are discussed further in this README.

![architecture diagram](https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/ba0a9faa-e35d-4aa7-88fa-c8b1016e58a2)


## Module structure

We are adopting the module separation approach used in [Now in Android](https://github.com/android/nowinandroid), such as splitting into 'feature' and 'core' modules.
We've added experimental support for Compose Multiplatform on certain screens, making the features accessible from the iOS app module as well."

<img width="798" alt="image" src="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/257e1dc6-da07-405b-8263-3e90ccc3c606">


# UI

## Composable Function Categorization

Composable functions are categorized into three types: Screen, Section, and Component. This categorization does not have a definitive rule, but it serves as a guide for better structure and improved readability.

<img width="200" alt="image" src="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/0b54af75-2556-4634-9479-588c516203eb">

```
sessions
├── TimetableScreen.kt
│    ├── TimetableScreenUiState
│    └── TimetableScreen
├── TimetableScreenViewModel.kt
├── component
│   └── TimetableListItem.kt
└── section
    ├── TimetableContent.kt
    │   ├── TimetableContentUiState
    │   └── TimetableContent
    └── TimetableList.kt
        ├── TimetableListUiState
        └── TimetableList
```

### Dependency rule

The basic dependency rule is as follows:

```
Screen -> Section -> Component
```

For example, `TimetableScreen` depends on `TimetableContent` and `TimetableListItem`. 
Also, a Section can depend on other Sections, and components can depend on other components.

### Screen

`Screen` refers to an entire screen within your application. 
Both Screen and Section are managed with UiState to handle their individual states, which are created by the ViewModel. 
Typically, each ViewModel is directly linked with a single Screen.

```kotlin
data class TimetableScreenUiState(
    val contentUiState: TimetableContentUiState,
    val isFavoriteFilterChecked: Boolean,
)

@Composable
private fun TimetableScreen(
    uiState: TimetableScreenUiState,
    ...
) {
...
```

### Section

`Section` refers to groups of components within screens, like containers including lists, which can dynamically adjust in size or complexity as the needs of the application change. An example could be a TimetableList.
Both Screen and Section are managed with UiState to handle their individual states, which are created by the ViewModel. 

```kotlin
data class TimetableListUiState(
    val timetableItemMap: PersistentMap<String, List<TimetableItem>>,
    val timetable: Timetable,
)

@Composable
fun TimetableList(
    uiState: TimetableListUiState,
    onBookmarkClick: (TimetableItem) -> Unit,
    onTimetableItemClick: (TimetableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
...
```

### Component

'Component' refers to the finer units of UI, designed to serve specific roles within the application. While they may not be as dynamic as Sections, they can still vary in their content or appearance to fit the specific needs of the app. Examples include TimetableListItem and TimeText.

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

## Single Source of Truth with buildUiState() {}

<img width="351" alt="image" src="https://github.com/DroidKaigi/conference-app-2023-images/assets/1386930/61257c45-0b1d-4f7d-b31b-249a9d482297">

The buildUiState() {} function promotes the Single Source of Truth (SSoT) principle in our application by combining multiple StateFlow objects into a single UI state. This ensures that data is managed and accessed from a single, consistent, and reliable source.

By working with StateFlow objects, the function can also compute initial values, further enhancing the SSOT principle.

Here's an example of using the buildUiState() function:

```kotlin
private val timetableContentUiState: StateFlow<TimetableContentUiState> = buildUiState(
    sessionsStateFlow,
    filtersStateFlow,
) { sessionTimetable, filters ->
    if (sessionTimetable.timetableItems.isEmpty()) {
        return@buildUiState TimetableContentUiState.Empty
    }
    TimetableContentUiState.ListTimetable(
        TimetableListUiState(
            timetable = sessionTimetable.filtered(filters),
        ),
    )
}
```

The buildUiState() function combines the data from sessionsStateFlow and filtersStateFlow into a single timetableContentUiState instance. This simplifies state management and ensures that the UI always displays consistent and up-to-date information.

# Build / CI

This project runs on GitHub Actions. This year's workflows contain new challenges!

## Provide the same CI experiences for both members and contributors(you!)

This project is an OSS so we cannot assign write-able tokens to workflow-runs that need the codes of the forked repos. To solve this problem, this project shares artifacts with multiple workflows via artifacts API and use them in *safe* workflows that have more-powerful permission but consist of safe actions.

This achieves to post comments on forked PRs safely. For example, you can see the results of the visual testing reports even on your PRs! (See [Architecture > Testing](#testing) for the visual testing).

# Testing

Testing an app involves balancing fidelity, how closely the test resembles actual use, and reliability, the consistency of test results. This year, our goal is to improve both using several methods.

Overview Diagram

![image](https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/4b2fc66c-1e6d-40b7-84cc-bc6f742ffc6d)

Detailed Diagram

<img width="812" alt="image" src="https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/b00857c2-c22e-4f6c-9a48-416312b75636">


## Screenshot Testing with Robolectric Native Graphics (RNG) and Roborazzi

[Robolectric Native Graphics (RNG)](https://github.com/robolectric/robolectric/releases/tag/robolectric-4.10) allows us to take app screenshots without needing an emulator or a device. This approach is faster and more reliable than taking device screenshots. While device screenshots may replicate real-world usage slightly more accurately, we believe the benefits of RNG's speed and reliability outweigh this. 
We use [Roborazzi](https://github.com/takahirom/roborazzi) to compare the current app's screenshots to the old ones, allowing us to spot and fix any visual changes.

### What to test: Balancing Screenshot Tests and Assertion Tests
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
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<MainActivity>(this)

    @Inject lateinit var timetableScreenRobot: TimetableScreenRobot

    // A screenshot test
    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            checkScreenCapture()
        }
    }

    // An assertion test for an important feature
    @Test
    fun checkLaunch() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            checkTimetableItemsDisplayed()
        }
    }
    ...
}
```

## The Companion Branch Approach

We use the [companion branch approach](https://github.com/DroidKaigi/conference-app-2022/pull/616) to store screenshots of feature branches. This method involves saving screenshots to a companion branch whenever a pull request is made, ensuring that we keep only relevant images and reduce the repository size.

<img src="https://user-images.githubusercontent.com/1386930/236188326-ddd617ae-b216-476c-9d92-e36ad02a2670.png" width="600" />

<p>
  <img src="https://github.com/DroidKaigi/conference-app-2023/assets/13657682/ee9e2246-fbd7-483b-9028-0a75571fdd97" width="25%" style="float: left; margin-right: 10px;" alt="Image" />
  <span>

> [!NOTE]
> Why not GitHub Actions Artifacts, Git LFS, or Feature Branch Commits?

  </span>
</p>

<div style="clear:both"></div>


While GitHub Actions Artifacts and Git LFS could be used for storing screenshots, they don't allow for direct image viewing in pull requests. Committing screenshots directly to the feature branch, on the other hand, can lead to an unnecessary increase in the repository size.

## Testing Robot Pattern

The Testing Robot Pattern simplifies writing UI tests. It splits the test code into two main parts: the 'how to test' portion, handled by the robot class, and the 'what to test' portion, managed by the test class. This separation provides benefits when writing screenshot tests, making the test code more maintainable and easier to understand.

### Testing Section: 'What to Test'

File: `TimetableScreenTest.kt`

```kotlin
    @Test
    @Category(ScreenshotTests::class)
    fun checkScrollShot() {
        timetableScreenRobot {
            // Define what functionalities of the screen to test
            setupTimetableScreenContent() // Setup the screen with the content
            scrollTimetable()             // Perform a scrolling action
            checkTimetableListCapture()   // Validate the visual state by capturing a screenshot
        }
    }
```

### Robot Section: 'How to Test'

File: `TimetableScreenRobot.kt`

```kotlin
    // Sets up the content for the Timetable screen
    fun setupTimetableScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                TimetableScreen(
                    onSearchClick = { },
                    onTimetableItemClick = { },
                    onBookmarkIconClick = { },
                )
            }
        }
        waitUntilIdle()
    }

    // Performs a scrolling action on the Timetable screen
    fun scrollTimetable() {
        composeTestRule
            .onNode(hasTestTag(TimetableScreenTestTag))
            .performTouchInput {
                swipeUp(
                    startY = visibleSize.height * 3F / 4,
                    endY = visibleSize.height / 2F,
                )
            }
    }

    // Validates the Timetable screen by capturing a screenshot
    fun checkTimetableListCapture() {
        composeTestRule
            .onNode(hasTestTag(TimetableScreenTestTag))
            .captureRoboImage()
    }
```

And now, you can check the scrolled screenshot!

![TimetableScreenTest checkScrollShot](https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/e283bc64-0b12-44cf-80ac-96d180d0a627)

This screenshot testing has been useful in that we can find bugs. For example, we found a bug where the tab was hidden when scrolling.

![screenshot-diff](https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/70ae4a4f-9098-40ad-8e33-a32f4e715266)

## Fake API Server

To ensure stable and comprehensive testing of our app, we opt to fake our API rather than use actual API. 
We have also designed our API to manage its own state and to allow us to change its status as needed. For instance, although we're not using it here, we could place an `AccessCounter` field inside the `Status` class to keep track of how many times the API has been hit. By managing our fake API in this way with Kotlin, we can adapt to changes in the response without having to rewrite the entire application.

```kotlin
interface SessionsApi {
    suspend fun timetable(): Timetable
}

class FakeSessionsApi : SessionsApi {

    sealed class Status : SessionsApi {
        object Operational : Status() {
            override suspend fun timetable(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Status() {
            override suspend fun timetable(): Timetable {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var status: Status = Status.Operational

    fun setup(status: Status) {
        this.status = status
    }

    override suspend fun timetable(): Timetable {
        return status.timetable()
    }
}
```

We use the `FakeSessionsApi` throughout our tests. It's provided by the `FakeSessionsApiModule`, which replaces the original `SessionsApiModule` during testing.

```kotlin
@Module
@TestInstallIn(
    components = [SingletonComponent::class], 
    replaces = [SessionsApiModule::class]
)
class FakeSessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApi {
        return FakeSessionsApi()
    }
}
```

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
   2. `cd app-ios && bundle exec fastlane shared`
   
3. open [`DroidKaigi2023.xcodeproj`](./app-ios/App/DroidKaigi2023/DroidKaigi2023.xcodeproj) by Xcode

## Build

- You can filter XCFramework arch by `arch` option at [`local.properties`](./local.properties)
  - e.g. if you need only `x86_64` binary, you can set `arch=x86_64`

## Debug

- You can build and debug on Android Studio with [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
- After install KMM plugin, you can see `app-ios` module on Android Studio's run configurations.
  - ![Run Configuration](https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/68ad1899-021c-4475-992a-13f3596a4973)
- Set configs like below
  - ![config](https://github.com/DroidKaigi/conference-app-2023-images/assets/45708639/449e362c-4999-4c93-8ae6-3b9583519ddb)


# Special thanks

 - Contributors of [DroidKaigi 2023 official app](https://github.com/DroidKaigi/conference-app-2023/graphs/contributors)
 - UI Lead: [upon0426](https://github.com/upon0426)
 - Build/CI Lead: [tomoya0x00](https://github.com/tomoya0x00)
 - Designer: [nobonobopurin](https://github.com/nobonobopurin)
 - Material3 Lead: [Nabe](https://twitter.com/NabeCott)
 - iOS Lead: [ry-itto](https://github.com/ry-itto)
 - Server / API Lead: [ryunen344](https://github.com/ryunen344)
 - DroidKaigi Co-Organizer / Architecture Lead: [takahirom](https://github.com/takahirom)
