![readme-banner](https://github.com/DroidKaigi/conference-app-2023/assets/136104152/b24bf87d-7af0-4224-9321-bfc101d541fb)


# DroidKaigi 2023 official app

[DroidKaigi 2023](https://2023.droidkaigi.jp/) will be held from September 14 to September 16, 2023. We are developing its application. Let's develop the app together and make it exciting.

# Features

This is a video of an app in development, and it will be updated as needed.

[Screen_recording_20230805_160952.webm](https://github.com/DroidKaigi/conference-app-2023/assets/1386930/b30d8912-387c-48cc-8eb3-a4ea4b8ccb21)

# Try it out!

[<img src="https://dply.me/l3yo7p/button/large" alt="Try it on your device via DeployGate">](https://dply.me/l3yo7p#install)

# Architecture

## Overview of the architecture

![architecture diagram](https://github.com/DroidKaigi/conference-app-2023/assets/1386930/03582926-5ff6-4375-87b1-3ec91efb120d)


# UI

## Composable Function Categorization

Composable functions are categorized into three types: Screen, Section, and Component. This categorization does not have a definitive rule, but it serves as a guide for better structure and improved readability.

<img width="401" alt="image" src="https://github.com/DroidKaigi/conference-app-2023/assets/1386930/d600dc70-e0e8-45ee-afd4-b90bc5b2517b">

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

<img width="351" alt="image" src="https://github.com/DroidKaigi/conference-app-2023/assets/1386930/9d0ebc3b-1e5e-46fd-8a0c-1ad4a0980476">

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

The buildUiState() function combines the data from sessionsStateFlow and filtersStateFlow into a single filterUiState instance. This simplifies state management and ensures that the UI always displays consistent and up-to-date information.

# Build / CI

This project runs on GitHub Actions. This year's workflows contain new challenges!

## Provide the same CI experiences for both members and contributors(you!)

This project is an OSS so we cannot assign write-able tokens to workflow-runs that need the codes of the forked repos. To solve this problem, this project shares artifacts with multiple workflows via artifacts API and use them in *safe* workflows that have more-powerful permission but consist of safe actions.

This achieves to post comments on forked PRs safely. For example, you can see the results of the visual testing reports even on your PRs! (See [Architecture > Testing](#testing) for the visual testing).

## WIP - Automatic dependency updates

> This workflow is disabled now.

We continue to use Renovate to update dependencies. [./.github/workflows/Renovate.yml](./.github/workflows/Renovate.yml) allows us to update some dependencies whose impacts seem to be low automatically.

# Testing

Testing an app involves balancing fidelity, how closely the test resembles actual use, and reliability, the consistency of test results. This year, our goal is to improve both using several methods.

Overview Diagram

![image](https://github.com/DroidKaigi/conference-app-2023/assets/1386930/a79bccbb-7486-4be9-865f-0655280af656)

Detailed Diagram

<img width="812" alt="image" src="https://github.com/DroidKaigi/conference-app-2023/assets/1386930/34d9d0d6-2bea-4311-a20c-64f2c2b3e0bd">


## Screenshot Testing with Robolectric Native Graphics (RNG) and Roborazzi

[Robolectric Native Graphics (RNG)](https://github.com/robolectric/robolectric/releases/tag/robolectric-4.10) allows us to take app screenshots without needing an emulator or a device. This approach is faster and more reliable than taking device screenshots. While device screenshots may replicate real-world usage slightly more accurately, we believe the benefits of RNG's speed and reliability outweigh this. 
We use Roborazzi to compare the current app's screenshots to the old ones, allowing us to spot and fix any visual changes.

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

## The Companion Branch Approach

We use the [companion branch approach](https://github.com/DroidKaigi/conference-app-2022/pull/616) to store screenshots of feature branches. This method involves saving screenshots to a companion branch whenever a pull request is made, ensuring that we keep only relevant images and reduce the repository size.

<img src="https://user-images.githubusercontent.com/1386930/236188326-ddd617ae-b216-476c-9d92-e36ad02a2670.png" width="600" />

* Why not GitHub Actions Artifacts, Git LFS, or Feature Branch Commits?

While GitHub Actions Artifacts and Git LFS could be used for storing screenshots, they don't allow for direct image viewing in pull requests. Committing screenshots directly to the feature branch, on the other hand, can lead to an unnecessary increase in the repository size.

## Testing Robot Pattern

The Testing Robot Pattern simplifies writing UI tests. It splits the test code into two parts: 'how to test', handled by the robot class, and 'what to test', managed by the test class. This separation is beneficial for writing screenshot tests and makes the test code more maintainable and easier to read.

## Fake API Server

To ensure stable and comprehensive testing of our app, we opt to fake our API rather than use actual API. 
We have also designed our API to manage its own state and to allow us to change its behavior as needed. For instance, although we're not using it here, we could place an `AccessCounter` field inside the `Behavior` class to keep track of how many times the API has been hit. By managing our fake API in this way with Kotlin, we can adapt to changes in the response without having to rewrite the entire application.

```kotlin
interface SessionsApi {
    suspend fun timetable(): Timetable
}

class FakeSessionsApi : SessionsApi {

    sealed class Behavior : SessionsApi {
        object Operational : Behavior() {
            override suspend fun timetable(): Timetable {
                return Timetable.fake()
            }
        }

        object Error : Behavior() {
            override suspend fun timetable(): Timetable {
                throw IOException("Fake IO Exception")
            }
        }
    }

    private var behavior: Behavior = Behavior.Operational

    fun setup(behavior: Behavior) {
        this.behavior = behavior
    }

    override suspend fun timetable(): Timetable {
        return behavior.timetable()
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
   2. `bundle exec fastlane shared`
   
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

# Contributing

We always welcome any and all contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

For Japanese speakers, please see [CONTRIBUTING.ja.md](CONTRIBUTING.ja.md).

# Special thanks

 - Contributors of [DroidKaigi 2023 official app](https://github.com/DroidKaigi/conference-app-2023/graphs/contributors)
 - UI Lead: [upon0426](https://github.com/upon0426)
 - Build/CI Lead: [tomoya0x00](https://github.com/tomoya0x00)
 - Designer: [nobonobopurin](https://github.com/nobonobopurin)
 - Material3 Lead: [Nabe](https://twitter.com/NabeCott)
 - iOS Lead: [ry-itto](https://github.com/ry-itto)
 - Server / API Lead: [ryunen344](https://github.com/ryunen344)
 - DroidKaigi Co-Organizer / Architecture Lead: [takahirom](https://github.com/takahirom)
