package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2023.contributors.ContributorsScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsViewModel
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.main.MainScreen
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen

@Composable
fun KaigiApp() {
    KaigiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "timetable") {
                composable("timetable") {
                    MainScreen(
                        timetableScreen = {
                            TimetableScreen(
                                onContributorsClick = {
                                    navController.navigate("contributors")
                                },
                            )
                        },
                    )
                }
                composable("contributors") {
                    ContributorsScreen(hiltViewModel<ContributorsViewModel>())
                }
            }
        }
    }
}