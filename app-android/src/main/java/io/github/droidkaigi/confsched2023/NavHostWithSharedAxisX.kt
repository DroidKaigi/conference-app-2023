package io.github.droidkaigi.confsched2023

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavHostWithSharedAxisX(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit,
) {
    val slideDistance = rememberSlideDistance()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        contentAlignment = contentAlignment,
        route = route,
        enterTransition = {
            materialSharedAxisXIn(
                forward = true,
                slideDistance = slideDistance,
            )
        },
        exitTransition = {
            materialSharedAxisXOut(
                forward = true,
                slideDistance = slideDistance,
            )
        },
        popEnterTransition = {
            materialSharedAxisXIn(
                forward = false,
                slideDistance = slideDistance,
            )
        },
        popExitTransition = {
            materialSharedAxisXOut(
                forward = false,
                slideDistance = slideDistance,
            )
        },
        builder = builder,
    )
}

@Composable
private fun rememberSlideDistance(): Int {
    val slideDistance: Dp = 30.dp
    val density = LocalDensity.current
    return remember(density, slideDistance) {
        with(density) { slideDistance.roundToPx() }
    }
}

private fun materialSharedAxisXIn(
    forward: Boolean,
    slideDistance: Int,
): EnterTransition = slideInHorizontally(
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing,
    ),
    initialOffsetX = {
        if (forward) slideDistance else -slideDistance
    }
) + fadeIn(
    animationSpec = tween(
        durationMillis = 195,
        delayMillis = 105,
        easing = LinearOutSlowInEasing,
    )
)

private fun materialSharedAxisXOut(
    forward: Boolean,
    slideDistance: Int,
): ExitTransition = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing,
    ),
    targetOffsetX = {
        if (forward) -slideDistance else slideDistance
    }
) + fadeOut(
    animationSpec = tween(
        durationMillis = 105,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    )
)
