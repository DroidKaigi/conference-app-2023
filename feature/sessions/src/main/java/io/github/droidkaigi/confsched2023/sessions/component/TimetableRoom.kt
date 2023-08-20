package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.RoomType.RoomA
import io.github.droidkaigi.confsched2023.model.RoomType.RoomB
import io.github.droidkaigi.confsched2023.model.RoomType.RoomC
import io.github.droidkaigi.confsched2023.model.RoomType.RoomD
import io.github.droidkaigi.confsched2023.model.RoomType.RoomE
import io.github.droidkaigi.confsched2023.model.TimetableRoom

internal val TimetableRoom.color: Color
    @Composable get() {
        val colors = hallColors()

        return when (type) {
            RoomA -> colors.hallA
            RoomB -> colors.hallB
            RoomC -> colors.hallC
            RoomD -> colors.hallD
            RoomE -> colors.hallE
            else -> Color.White
        }
    }
