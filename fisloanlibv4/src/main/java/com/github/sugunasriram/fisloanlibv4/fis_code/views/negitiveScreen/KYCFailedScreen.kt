package com.github.sugunasriram.fisloanlibv4.fis_code.views.negitiveScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.failureRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normalSerif32Text500

@Composable
fun KYCFailedScreen(navController: NavHostController) {
    BackHandler { navigateApplyByCategoryScreen(navController) }
    FixedTopBottomScreen(
        navController = navController,
        backgroundColor = appWhite,
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        topBarBackgroundColor = appWhite
    ) {
        StartingText(
            text = stringResource(R.string.kyc_failed),
            textColor = failureRed,
            start = 30.dp,
            end = 30.dp,
            top = 150.dp,
            bottom = 50.dp,
            style = normalSerif32Text500,
            alignment = Alignment.Center
        )
        Image(
            painter = painterResource(id = R.drawable.kyc_failed_image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun KYCFailedPReviewScreen() {
    Surface {
        KYCFailedScreen(rememberNavController())
    }
}
