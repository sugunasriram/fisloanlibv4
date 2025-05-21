package com.github.sugunasriram.fisloanlibv4.fis_code.views.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TextWithRadioButton
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods

@Composable
fun LanguageSelectionScreen(navController: NavHostController) {

    val languagesList = listOf(
        stringResource(id = R.string.english),
        stringResource(id = R.string.kannada),
        stringResource(id = R.string.hindi),
        stringResource(id = R.string.tamil),
        stringResource(id = R.string.telegu),
        stringResource(id = R.string.marati)
    )
    var selectedLanguage by rememberSaveable { mutableStateOf(languagesList.first()) }
    val context = LocalContext.current

    FixedTopBottomScreen(
        navController = navController,
        showBackButton = true,
        onBackClick = {navController.popBackStack()},
        topBarText = stringResource(R.string.choose_language),
        backgroundColor= appWhite
    ) {
        CenteredMoneyImage(
            image = R.drawable.language_screen_image,
            imageSize = 200.dp,
            top = 40.dp, bottom = 20.dp
        )
        languagesList.forEach {language ->
            TextWithRadioButton (
                text = language,
                selected = selectedLanguage == language,
                onCheckedChange = { isSelected ->
                    if (isSelected) {
                        selectedLanguage = language
                        CommonMethods().toastMessage(
                            context = context, toastMsg = context.getString(R.string.feature_supported_in_future)
                        )
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLanguageSelectionScreen() {
    val mockNavController = rememberNavController()
    LanguageSelectionScreen(navController = mockNavController)
}

