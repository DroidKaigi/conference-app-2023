package io.github.droidkaigi.confsched2023.sessions.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookMarkTopArea(
    scrollState: LazyListState,
    onClickBackPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bigText = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
    )
    val normalText = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal,
    )

    val fraction by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0) {
                scrollState.firstVisibleItemScrollOffset / 520F
            } else {
                1F
            }
        }
    }

    val rowNum by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset / 520F < 1F) {
                1
            } else {
                2
            }
        }
    }

    val titleTextStyle = lerp(
        bigText,
        normalText,
        fraction,
    )

    val backgroundColor = lerp(
        Color(0xFFF8FAF6),
        Color(0xFFCEE9DB),
        fraction,
    )

    val topBarHeight = lerp(
        156.dp,
        96.dp,
        fraction,
    )

    val titlePaddingTop = lerp(
        16.dp,
        0.dp,
        fraction,
    )

    val titlePaddingStart = lerp(
        0.dp,
        16.dp,
        fraction,
    )

    SideEffect {
        Log.d("test", titlePaddingTop.value.toString())
        Log.d("test", titlePaddingStart.value.toString())
    }

    Box(
        modifier = modifier
            .height(topBarHeight)
            .background(backgroundColor),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            maxItemsInEachRow = rowNum,
            modifier = Modifier
                .padding(start = 16.dp, top = 46.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClickBackPress()
                    },
            )
            Text(
                text = "Bookmark",
                style = titleTextStyle,
                modifier = Modifier.padding(
                    start = if (titlePaddingStart >= 0.dp) titlePaddingStart else 0.dp,
                    top = if (titlePaddingTop >= 0.dp) titlePaddingTop else 0.dp,
                ),
            )
        }
    }
}
