package com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TopBottomBarForNegativeScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal30Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal36Text700

@Composable
fun NoLoanOffersAvailableScreen(navController: NavHostController,
                                onClick: () -> Unit = { navigateApplyByCategoryScreen(navController = navController) }) {
    TopBottomBarForNegativeScreen(showTop = false, showBottom = true, navController = navController) {

        StartingText(
            text = "SORRY!",
            textColor = errorRed,
            start = 30.dp, end = 30.dp, top = 60.dp, bottom = 15.dp,
            style = normal36Text700,
            alignment = Alignment.Center
        )
        StartingText(
            text = "No Loan Offers Available", textColor = appBlack, style = normal30Text700,
            alignment = Alignment.TopCenter, bottom = 40.dp
        )
        Image(
            painter = painterResource(id = R.drawable.error_no_loan_offers_available),
            contentDescription = "loan Status",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().size(250.dp)
        )
        RegisterText(
            text = stringResource(id = R.string.we_regret_no_loan_offers),
            style = normal14Text700,
            textColor = hintGray, top = 10.dp, bottom = 25.dp
        )

        ClickableText(
            text ="Go Back", style = normal20Text700
        ) { onClick() }

    }
}
@Preview
@Composable
private fun NoLoanOffersAvailableScreenPreview() {
    Surface {
        NoLoanOffersAvailableScreen(rememberNavController(),{})
    }

}