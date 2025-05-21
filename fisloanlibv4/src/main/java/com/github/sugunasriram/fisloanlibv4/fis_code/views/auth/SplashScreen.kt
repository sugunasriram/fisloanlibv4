package com.github.sugunasriram.fisloanlibv4.fis_code.views.auth

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal24Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.serif28Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.storage.TokenManager
import kotlinx.coroutines.delay

private const val SPLASH_SCREEN_DELAY = 3000L

@Composable
fun SpalashScreen(navController: NavHostController) {
    SplashScreenUi()
    LaunchedEffect(key1 = true) {
        delay(SPLASH_SCREEN_DELAY)
        //Get AccessToken and RefreshToken is null - Login Screen
        //else ApplyBycategoryScreen

        val accessToken = TokenManager.read("accessToken")
        if (accessToken.isNullOrEmpty()) {
            navigateSignInPage(navController)
        } else {
            navigateApplyByCategoryScreen(navController)
        }
    }
}

@Composable
fun SplashScreenUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen_background),
            contentDescription = stringResource(id = R.string.splash_screen_background),
            modifier = Modifier
                .fillMaxSize()
                .size(180.dp),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.splash_subtract),
            contentDescription = stringResource(id = R.string.splash_screen_background),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 400.dp, start = 0.dp, end = 0.dp, bottom = 0.dp)
                .size(180.dp),
            contentScale = ContentScale.Crop
        )
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = serif28Text700, textAlign = TextAlign.Start,
                    color = appBlack,
                    modifier = Modifier
                        .padding(top=40.dp,start = 25.dp, end = 10.dp)
                        .align(Alignment.Start),
                )

                Image(
                    painter = painterResource(id = R.drawable.splash_screen_image),
                    contentDescription = stringResource(id = R.string.splash_screen_image),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(top=60.dp, start = 40.dp, end = 40.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.splash_ondc),
                    contentDescription = stringResource(id = R.string.splash_screen_image),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color.Transparent).size(200.dp)
                        .padding( bottom = 50.dp, start = 40.dp, end = 40.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = stringResource(id = R.string.hassle_free_and_quick_loan),
                    style = normal24Text500, color = appWhite, textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreenUi()
}

@Composable
fun InAppUpdateScreen(context: Context, onUpdateCheckComplete: () -> Unit) {
    val appUpdateManager = remember { AppUpdateManagerFactory.create(context) }
    val updateInfo = remember { mutableStateOf<AppUpdateInfo?>(null) }
    val isUpdateChecked = remember { mutableStateOf(false) }  // Track when check is complete

    // Launch effect to check for updates
    LaunchedEffect(Unit) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            updateInfo.value = info
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    AppUpdateType.IMMEDIATE,
                    (context as Activity),
                    REQUEST_CODE_UPDATE
                )
            } else {
                // No update needed, proceed
                isUpdateChecked.value = true
            }
        }.addOnFailureListener {
            // Error checking updates, proceed with app launch
            isUpdateChecked.value = true
        }
    }

    // Proceed to launch screen once update check is done
    if (isUpdateChecked.value) {
        onUpdateCheckComplete()
    }

    // UI shown while checking for updates
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Checking for updates...", fontSize = 20.sp)
//    }
}


const val REQUEST_CODE_UPDATE = 2001
