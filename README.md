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

## ...

TBD

# iOS

