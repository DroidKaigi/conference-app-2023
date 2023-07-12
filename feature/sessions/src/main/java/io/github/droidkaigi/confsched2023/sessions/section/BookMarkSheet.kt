package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import io.github.droidkaigi.confsched2023.sessions.BookMarkScreenUiState
import io.github.droidkaigi.confsched2023.sessions.BookMarkScreenUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.BookMarkScreenUiState.ListBookMark

@Composable
fun BookMarkSheet(
    uiState: BookMarkScreenUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is Empty -> {
                EmptyView()
            }

            is ListBookMark -> {
                Text("ListBookMark")
            }
        }
    }
}

@Composable
private fun EmptyView() {
    ConstraintLayout {
        val (emptyText, icon) = createRefs()
        Box(
            modifier = Modifier
                .size(84.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(24.dp),
                )
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(emptyText.top, margin = 24.dp)
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
            )
        }
        Column(
            modifier = Modifier.constrainAs(emptyText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "登録されたセッションがありません",
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                lineHeight = 24.sp,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "気になるセッションをブックマークに追加して\n集めてみましょう！",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
