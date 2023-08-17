package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.ui.graphics.Color
import io.github.droidkaigi.confsched2023.designsystem.theme.arcticfoxBlue
import io.github.droidkaigi.confsched2023.designsystem.theme.bumblebeeYellow
import io.github.droidkaigi.confsched2023.designsystem.theme.chipmunkBrown
import io.github.droidkaigi.confsched2023.designsystem.theme.dolphinGreen
import io.github.droidkaigi.confsched2023.designsystem.theme.electriceelGray
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room1
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room2
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room3
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room4
import io.github.droidkaigi.confsched2023.model.RoomIndex.Room5
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.model.type

internal val TimetableRoom.color: Color
    get() = when (type) {
        Room1 -> chipmunkBrown
        Room2 -> arcticfoxBlue
        Room3 -> bumblebeeYellow
        Room4 -> dolphinGreen
        Room5 -> electriceelGray
        else -> Color.White
    }
