import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.primaryOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.robotTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
