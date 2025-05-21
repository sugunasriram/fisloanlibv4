package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableTextWithIcon
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.azureBlueColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.semiBold24Text700
import kotlinx.coroutines.delay

@Composable
fun PrePaymentStatusScreen(
    navController: NavHostController,
    headerText: String = stringResource(id = R.string.repayment_successful),
    hintText: String = stringResource(id = R.string.repayment_successfully_processed),
    image: Painter = painterResource(id = R.drawable.payment_success),
    showButton: Boolean = false,
    orderId:String,
    fromFlow:String,
    onClick: () -> Unit
) {
    LaunchedEffect (Unit){
        delay(5000)
        navigateToLoanDetailScreen(navController = navController, orderId = orderId, fromFlow = fromFlow,
            fromScreen = "PrePart Payment Status")
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
            StartingText(
                text = headerText,
                textColor = appBlack,
                start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp,
                style = semiBold24Text700,
                alignment = Alignment.TopCenter
            )
            StartingText(
                text = if(showButton) stringResource(id = R.string.unsuccessful)
                       else stringResource(id = R.string.successful),
                textColor = if(showButton) errorRed else appGreen,
                start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp,
                style = semiBold24Text700,
                alignment = Alignment.TopCenter
            )
            if (!showButton){
                StartingText(
                    text = hintText,
                    textColor = hintGray,
                    start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp,
                    style = normal14Text500,
                    alignment = Alignment.TopCenter
                )
            }

            if (showButton){
                ClickableTextWithIcon(
                    text = stringResource(id = R.string.retry), image = R.drawable.refresh_icon
                ) { onClick() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrePaymentStatusScreenPreview() {
    val fakeNavController = rememberNavController()
    PrePaymentStatusScreen(
        navController = fakeNavController,
        orderId = "123456",
        fromFlow = "LOAN_DETAILS",
        headerText = stringResource(id = R.string.repayment_un_successful),
        showButton = true,
        image = painterResource(id = R.drawable.payment_unsuccess),
        onClick = { /* No-op for preview */ }
    )
}