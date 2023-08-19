package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

const val TimetableShimmerListItemTestTag = "TimetableShimmerListItemList"

@Composable
fun TimetableShimmerListItem(modifier: Modifier = Modifier) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    Column(
        modifier = modifier.testTag(TimetableShimmerListItemTestTag),
    ) {
        Spacer(modifier = Modifier.size(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            //  Shimmer effect on top
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .shimmer(shimmerInstance)
                    .background(Color.LightGray),
            ) {}
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row {
            //  Shimmer effect on bottom(left)
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmer(shimmerInstance)
                    .background(Color.LightGray),
            ) {}
            Spacer(modifier = Modifier.size(10.dp))
            //  Shimmer effect on bottom(right)
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp)
                    .shimmer(shimmerInstance)
                    .background(Color.LightGray),
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        Divider()
    }
}

// TODO: Need to resolve CI unit test failures due to TimetableShimmerListItemPreview()
//@MultiThemePreviews
//@MultiLanguagePreviews
//@Composable
//fun TimetableShimmerListItemPreview() {
//    KaigiTheme {
//        Surface {
//            TimetableShimmerListItem()
//        }
//    }
//}
