package io.github.droidkaigi.confsched2023.staff

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Staff
import io.github.droidkaigi.confsched2023.ui.previewOverride
import io.github.droidkaigi.confsched2023.ui.rememberAsyncImagePainter

private val staffIconShape = RoundedCornerShape(20.dp)

@Composable
fun StaffListItem(
    staff: Staff,
    onStaffClick: (url: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(enabled = staff.profileUrl.isNotEmpty()) {
                onStaffClick(staff.profileUrl)
            }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = previewOverride(previewPainter = { rememberVectorPainter(image = Icons.Default.Person) }) {
                rememberAsyncImagePainter(staff.iconUrl)
            },
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(staffIconShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = staffIconShape
                ),
        )
        Spacer(modifier = Modifier.size(23.dp))
        Text(
            text = staff.username,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

// region Preview

class PreviewStaffProvider : PreviewParameterProvider<Staff> {
    override val values: Sequence<Staff>
        get() = sequenceOf(
            Staff(
                id = 1,
                username = "",
                iconUrl = "",
                profileUrl = ""
            ),
            Staff(
                id = 1,
                username = "UserName",
                iconUrl = "",
                profileUrl = ""
            ),
            Staff(
                id = 1,
                username = "UserNameUserNameUserNameUserNameUserNameUserNameUserName",
                iconUrl = "",
                profileUrl = ""
            ),
        )
}

@Preview(showBackground = true)
@Composable
fun StaffListItemPreview(
    @PreviewParameter(PreviewStaffProvider::class) staff: Staff
) {
    StaffListItem(staff = staff, modifier = Modifier.fillMaxWidth(), onStaffClick = {})
}

// endregion