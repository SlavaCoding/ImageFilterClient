import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.mainForm
import ui.theme.AppTheme

@Preview
@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    AppTheme {
        mainForm()
    }
}
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}