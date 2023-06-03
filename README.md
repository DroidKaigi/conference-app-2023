# DroidKaigi 2023 official app

DroidKaigi 2023 official app is an app for DroidKaigi 2023.

# UI

# Build / CI

# Architecture

## Overview of the architecture

![architecture diagram](https://github.com/DroidKaigi/conference-app-2023/assets/1386930/675eb1ba-1d93-4748-ae5a-a8548e8aaef6)

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

## Composable Function Categorization

Composable functions are categorized into three types: Screen, Section, and Component. This categorization does not have a definitive rule, but it serves as a guide for better structure and improved readability.

### Screen

`Screen` refers to an entire screen within your application, typically encapsulating a full user interaction.

### Section

`Section` refers to reusable, dynamic parts of screens, such as lists, which may change significantly based on the app's growth or content variety. An example could be a `Timetable`.

### Component

`Component` refers to the smallest, indivisible units of UI that serve specific roles and are less likely to have dynamic content. Examples include `TimetableItem` and `TimeText`.

Through clear delineation of roles and responsibilities of different composables, this classification assists in enhancing code organization and maintainability.

## Testing

Testing an app involves balancing fidelity, how closely the test resembles actual use, and reliability, the consistency of test results. This year, we're aiming to perfect both using two primary methods.

### Screenshot Testing with Robolectric Native Graphics (RNG) and Roborazzi

Robolectric Native Graphics (RNG) allows us to take app screenshots without needing an emulator or a device. This approach is faster and more reliable than older methods. We use Roborazzi to compare the current app's screenshots to the old ones, allowing us to spot and fix any visual changes.

#### Balancing Screenshot Tests and Assertion Tests
Screenshot tests are extremely effective as they allow us to spot visual changes without writing many assertions. However, there is a risk of mistakenly using incorrect baseline images.  
Hence, for important features, it's essential to supplement these tests with assertion tests. The tests will typically look like this:

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


# Special thanks

 - Contributors of [DroidKaigi 2023 official app](https://github.com/DroidKaigi/conference-app-2023/graphs/contributors)
 - UI Lead: [upon0426](https://github.com/upon0426)
 - Build/CI Lead: [tomoya0x00](https://github.com/tomoya0x00)
 - Designer: TBD
 - iOS Lead: [ry-itto](https://github.com/ry-itto)
 - Server / API Lead: [ryunen344](https://github.com/ryunen344)
 - DroidKaigi Co-Organizer / Architecture Lead: [takahirom](https://github.com/takahirom)
