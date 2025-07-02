package com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.robotoSerifNormal24Text500

@Composable
fun EMandateESignFailedScreen(navController : NavHostController,title : String) {
    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    FixedTopBottomScreen(
        navController = navController,
        backgroundColor= appWhite,
        showBackButton = true,
        onBackClick = { navController.popBackStack()},
        topBarBackgroundColor = appWhite
    ) {
        Image(
//            painter = painterResource(id = R.drawable.form_submission_failed_image),
            painter = painterResource(id = R.drawable.error_session_time_out),
            contentDescription = "", contentScale = ContentScale.Fit,
            modifier = Modifier.padding(top = 100.dp).size(300.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        RegisterText(
            text = title,
            textColor = appBlack,
            start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp,
            style = robotoSerifNormal24Text500,
        )
    }

}

@Preview
@Composable
private fun KYCFailedPReviewScreen() {
    Surface  {
        EMandateESignFailedScreen(rememberNavController(),"LoanAgreement \nSession timed out due to inactivity")
    }
}