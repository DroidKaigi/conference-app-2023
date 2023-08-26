package io.github.droidkaigi.confsched2023.stamps

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2023.feature.stamps.R
import io.github.droidkaigi.confsched2023.model.Stamp
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolder
import io.github.droidkaigi.confsched2023.ui.buildUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StampsScreenViewModel @Inject constructor(
    val userMessageStateHolder: UserMessageStateHolder,
) : ViewModel(),
    UserMessageStateHolder by userMessageStateHolder {

    private val stampLottieRawResStateFlow: MutableStateFlow<Int?> =
        MutableStateFlow(null)

    val uiState = buildUiState(
        stampLottieRawResStateFlow,
    ) { rawRes ->
        StampsScreenUiState(
            lottieRawRes = rawRes,
            stamps = persistentListOf(
                Stamp(
                    hasDrawableResId = R.drawable.img_stamp_a_on,
                    lottieRawId = R.raw.stamp_a_lottie,
                    notHasDrawableResId = R.drawable.img_stamp_a_off,
                    contentDescription = "StampA image",
                ),
                Stamp(
                    hasDrawableResId = R.drawable.img_stamp_b_on,
                    lottieRawId = R.raw.stamp_b_lottie,
                    notHasDrawableResId = R.drawable.img_stamp_b_off,
                    contentDescription = "StampB image",
                ),
                Stamp(
                    hasDrawableResId = R.drawable.img_stamp_c_on,
                    lottieRawId = R.raw.stamp_c_lottie,
                    notHasDrawableResId = R.drawable.img_stamp_c_off,
                    contentDescription = "StampC image",
                ),
                Stamp(
                    hasDrawableResId = R.drawable.img_stamp_d_on,
                    lottieRawId = R.raw.stamp_d_lottie,
                    notHasDrawableResId = R.drawable.img_stamp_d_off,
                    contentDescription = "StampD image",
                ),
                Stamp(
                    hasDrawableResId = R.drawable.img_stamp_e_on,
                    lottieRawId = R.raw.stamp_e_lottie,
                    notHasDrawableResId = R.drawable.img_stamp_e_off,
                    contentDescription = "StampE image",
                ),
            ),
        )
    }

    fun onStampClick(
        stamp: Stamp
    ) {
        stampLottieRawResStateFlow.value = stamp.lottieRawId
    }

    fun onReachAnimationEnd() {
        stampLottieRawResStateFlow.value = null
    }
}
