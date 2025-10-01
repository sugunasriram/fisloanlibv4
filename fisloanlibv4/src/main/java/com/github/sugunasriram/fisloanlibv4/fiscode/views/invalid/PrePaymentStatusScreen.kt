package com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableTextWithIcon
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScheduleScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.semiBold24Text700
import kotlinx.coroutines.delay

@Composable
fun PrePaymentStatusScreen(
    navController: NavHostController,
    headerText: String = stringResource(id = R.string.repayment_successful),
    hintText: String = stringResource(id = R.string.repayment_successfully_processed),
    image: Painter = painterResource(id = R.drawable.payment_success),
    showButton: Boolean = false,
    orderId: String,
    fromFlow: String,
    onClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(5000)
        navigateToRepaymentScheduleScreen(
            navController = navController,
            orderId = orderId,
            fromFlow = fromFlow,
            fromScreen = "PrePart Payment Status"
        )
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
                contentScale = ContentScale.Fit
            )
            StartingText(
                text = headerText,
                textColor = appBlack,
                start = 30.dp,
                end = 30.dp,
                top = 10.dp,
                bottom = 5.dp,
                style = semiBold24Text700,
                alignment = Alignment.TopCenter
            )
            StartingText(
                text = if (showButton) {
                    stringResource(id = R.string.unsuccessful)
                } else {
                    stringResource(id = R.string.successful)
                },
                textColor = if (showButton) errorRed else appGreen,
                start = 30.dp,
                end = 30.dp,
                top = 10.dp,
                bottom = 5.dp,
                style = semiBold24Text700,
                alignment = Alignment.TopCenter
            )
            if (!showButton) {
                StartingText(
                    text = hintText,
                    textColor = hintGray,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal14Text500,
                    alignment = Alignment.TopCenter
                )
            }

            if (showButton) {
                ClickableTextWithIcon(
//                    text = stringResource(id = R.string.retry),
                    text = stringResource(id = R.string.go_back),
                    image = R.drawable.refresh_icon
                ) { onClick() }
            }
        }
    }
}

