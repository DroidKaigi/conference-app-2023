package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.model.type

internal val TimetableRoom.color: Color
    @Composable get() {
        val colors = hallColors()

        return when (type) {
            Room1 -> colors.hallC
            Room2 -> colors.hallA
            Room3 -> colors.hallB
            Room4 -> colors.hallD
            Room5 -> colors.hallE
            else -> Color.White
        }
    }
