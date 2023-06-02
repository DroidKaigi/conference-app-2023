import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController = ComposeUIViewController { MainView() }

@Composable
fun MainView() {
    Text("2023/06/02")
}