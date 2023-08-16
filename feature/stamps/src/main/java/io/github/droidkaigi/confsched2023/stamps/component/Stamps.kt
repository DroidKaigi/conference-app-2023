package io.github.droidkaigi.confsched2023.stamps.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.feature.stamps.R

@Composable
fun Stamps(
    isStampA: Boolean,
    isStampB: Boolean,
    isStampC: Boolean,
    isStampD: Boolean,
    isStampE: Boolean,
    onStampsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(
                    id = if (isStampA) R.drawable.img_stamp_a_on else R.drawable.img_stamp_a_off,
                ),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onStampsClick() }
                    .padding(horizontal = 21.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(
                    id = if (isStampB) R.drawable.img_stamp_b_on else R.drawable.img_stamp_b_off,
                ),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onStampsClick() }
                    .padding(horizontal = 21.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(
                    id = if (isStampC) R.drawable.img_stamp_c_on else R.drawable.img_stamp_c_off,
                ),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onStampsClick() }
                    .padding(horizontal = 21.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(
                    id = if (isStampD) R.drawable.img_stamp_d_on else R.drawable.img_stamp_d_off,
                ),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onStampsClick() }
                    .padding(horizontal = 21.dp),

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(
                id = if (isStampE) R.drawable.img_stamp_e_on else R.drawable.img_stamp_e_off,
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clickable { onStampsClick() },
        )
    }
}
