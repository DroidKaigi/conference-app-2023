package io.github.droidkaigi.confsched2023

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.droidkaigi.confsched2023.contributors.ContributorsScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsViewModel
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.main.MainScreen
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen

@Composable
fun KaigiApp(modifier: Modifier = Modifier) {
    KaigiTheme {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(Color.Transparent, useDarkIcons)
            onDispose {}
        }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
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
