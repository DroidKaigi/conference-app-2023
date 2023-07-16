package io.github.droidkaigi.confsched2023.sessions.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
                scrollState.firstVisibleItemScrollOffset / 500F
            } else {
                1F
            }
        }
    }

    val rowNum by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset / 500F < 1F) {
                1
            } else {
                2
            }
        }
    }

    val titlePadding by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset / 500F < 1F) {
                0.dp
            } else {
                16.dp
            }
        }
    }

    val titleTextStyle = lerp(
        bigText,
        normalText,
        fraction,
    )

    val backgroundColor = lerp(
        Color(0xFFCEE9DB),
        Color(0xFFF8FAF6),
        fraction,
    )

    val topBarHeight = lerp(
        94.dp,
        144.dp,
        fraction,
    )

    Log.d("test", rowNum.toString())

    TopAppBar(
        navigationIcon = {},
        title = {
            FlowRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                maxItemsInEachRow = rowNum,
                modifier = Modifier
                    .height(topBarHeight)
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
                    modifier = Modifier.padding(titlePadding)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = backgroundColor,
        ),
        modifier = Modifier.height(topBarHeight)
    )
}
