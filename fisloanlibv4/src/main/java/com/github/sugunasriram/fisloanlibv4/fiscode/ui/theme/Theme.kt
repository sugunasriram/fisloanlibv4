import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.primaryOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.robotTheme


private val LightColorPalette = lightColors(primary = primaryOrange)
@Composable
fun FsTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(appOrange)
    systemUiController.statusBarDarkContentEnabled = true
    MaterialTheme(
        colors = LightColorPalette,
        typography = robotTheme,
        content = content
    )
}