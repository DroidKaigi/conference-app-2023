package io.github.droidkaigi.confsched2023.testing

import com.airbnb.lottie.LottieTask
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class LottieTestRule : TestWatcher() {

    override fun starting(description: Description) {
        LottieTask.EXECUTOR = Executor(Runnable::run)
    }

    override fun finished(description: Description) {
        LottieTask.EXECUTOR = Executors.newCachedThreadPool()
    }
}
