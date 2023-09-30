package io.github.droidkaigi.confsched2023.testing

import com.airbnb.lottie.LottieTask
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executor

class LottieTestRule : TestWatcher() {
    private lateinit var evacuatedExecutor: Executor

    override fun starting(description: Description) {
        evacuatedExecutor = LottieTask.EXECUTOR
        LottieTask.EXECUTOR = Executor(Runnable::run)
    }

    override fun finished(description: Description) {
        LottieTask.EXECUTOR = evacuatedExecutor
    }
}
