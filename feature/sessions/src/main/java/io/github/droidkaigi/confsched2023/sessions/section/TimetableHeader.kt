package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.feature.sessions.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun TimetableHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(text = "DroidKaigi\n2023", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "at Bellesalle Shibuya Garden",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Box {
            var atEnd by remember { mutableStateOf(false) }
            Image(
                modifier = Modifier.size(width = 185.dp, height = 169.dp),
                painter = painterResource(id = R.drawable.img_droid_kun_in_bath),
                contentDescription = null,
            )
            Image(
                modifier = Modifier.size(width = 185.dp, height = 169.dp),
                painter = rememberAnimatedVectorPainter(
                    AnimatedImageVector.animatedVectorResource(
                        R.drawable.avd_anim,
                    ),
                    atEnd,
                ),
                contentDescription = null,
            )
            LaunchedEffect(Unit) {
                atEnd = atEnd.not()
                repeat(Int.MAX_VALUE) {
                    delay(10000)
                    atEnd = atEnd.not()
                }
            }
        }
    }
}
