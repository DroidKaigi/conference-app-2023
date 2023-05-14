# DroidKaigi 2023 official app

DroidKaigi 2023 official app is an app for DroidKaigi 2023.

# UI

# Build / CI

# Architecture

## Overview of the architecture

TODO: Insert architecture diagram

## Single Source of Truth with buildUiState() {}

The buildUiState() {} function promotes the Single Source of Truth (SSOT) principle in our application by combining multiple StateFlow objects into a single UI state. This ensures that data is managed and accessed from a single, consistent, and reliable source.

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

Striking the right balance between fidelity and reliability when testing an app can be challenging. Fidelity refers to the extent to which a test replicates real-world usage of the app, while reliability represents the test's ability to remain free from defects, such as flaky tests. This year, we aim to achieve both by employing the following approach:

### Screenshot testing using Robolectric Native Graphics (RNG) with Roborazzi

Robolectric Native Graphics (RNG) is a new feature introduced in Robolectric 4.10. It allows us to capture screenshots of the app without needing to run the emulator. This marks a significant improvement over the previous method of using the emulator for screenshot capture, which could be slow and unreliable.

We use Roborazzi to compare app screenshots with those of the previous build. This helps us identify any visual changes in the app and address them accordingly.

#### The companion branch approach

[The companion branch approach](https://github.com/DroidKaigi/conference-app-2022/pull/616) is a method for storing feature branch screenshots. When a pull request is created, the feature branch's screenshot is saved in the companion branch. Outdated branches are deleted to reduce the Git repository size and ensure that only relevant screenshots are retained.

This approach allows users to view feature branch screenshot changes in pull requests while minimizing the impact on the Git repository size by deleting outdated branch screenshots.

<img src="https://user-images.githubusercontent.com/1386930/236188326-ddd617ae-b216-476c-9d92-e36ad02a2670.png" width="600" />

* Why not use GitHub Actions artifacts?

Although GitHub Actions artifacts are a viable option for storing screenshots, they are less convenient for viewing changes in pull requests, as artifacts are stored as zip files. It appears that LFS encounters [similar issues](https://github.com/git-lfs/git-lfs/issues/1342).

* Why not commit the screenshot to the feature branch?

Committing the screenshot to the feature branch is another viable option. However, this approach retains the screenshot in the repository indefinitely, as Git preserves commit history.

### Testing Robot Pattern

The Testing Robot Pattern is a method for writing UI tests. It divides the test code into two distinct parts: how to test and what to test. The "how to test" aspect is written in the robot class, while the "what to test" portion is covered in the test class. This separation makes the test code easier to read and maintain. Additionally, the robot class proves beneficial for writing screenshot tests.

# iOS

