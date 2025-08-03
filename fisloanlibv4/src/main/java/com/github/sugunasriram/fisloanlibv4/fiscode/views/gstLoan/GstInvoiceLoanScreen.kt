package com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredManAtGstImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cardColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hyperRedColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal11Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700

@Composable
fun GstInvoiceLoanScreen(navController: NavHostController, fromFlow: String) {
    val context = LocalContext.current

    BackHandler {
        navigateToLoanProcessScreen(
            navController = navController,
            transactionId = "Sugu",
            statusId = 9,
            responseItem = "No Need",
            offerId = "1234",
            fromFlow = "Invoice Loan"
        )
    }
    FixedTopBottomScreen(
        navController = navController,
        backgroundColor = appWhite,
        contentStart = 0.dp,
        contentEnd = 0.dp,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.gst_loan),
        showBackButton = true,
        onBackClick = { navigateApplyByCategoryScreen(navController) },
        showBottom = true,
        showCheckBox = false,
        showArrowButton = true,
        onArrowButtonClick = {
            navigateToGstDetailsScreen(navController = navController, fromFlow = fromFlow)
        },
        showSingleButton = false

    ) {
        CenteredManAtGstImage(imageSize = 180.dp, top = 20.dp)

        FullWidthRoundShapedCard(
            start = 45.dp,
            end = 45.dp,
            top = 0.dp,
            bottom = 50.dp,
            onClick = { /*TODO*/ },
            cardColor = cardColor,
            gradientColors = listOf(
                cardColor,
                cardColor
            )
        ) {
            ImageWithDouleText(
                image = R.drawable.enable_gst_icon,
                top = 20.dp,
                showHyperText = true,
                context = context,
                headerText = stringResource(id = R.string.enable_gst_api_access),
                headerValue = stringResource(id = R.string.visit_gst_settings)
            )
            ImageWithDouleText(
                top = 25.dp,
                bottom = 20.dp,
                image = R.drawable.group_368,
                showHyperText = false,
                headerText = stringResource(id = R.string.enter_gst_username),
                context = context,
                headerValue = stringResource(id = R.string.gstin_info)
            )
        }
//        CurvedPrimaryButtonFull(
//            text = stringResource(id = R.string.next),
//            modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 50.dp)
//        ) { navigateToGstDetailsScreen(navController = navController, fromFlow = fromFlow) }
    }
}

@Composable
fun ImageWithDouleText(
    @DrawableRes image: Int,
    headerText: String,
    headerValue: String,
    context: Context,
    showHyperText: Boolean,
    modifier: Modifier = Modifier,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = top, bottom = bottom, start = start, end = end)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.image),
            modifier.size(35.dp)
        )
        Column(modifier.padding(start = 18.dp)) {
            StartingText(text = headerText, style = normal16Text700)
            if (showHyperText) {
                HyperlinkText(
                    onSahamatiClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://tutorial.gst.gov.in/userguide/registration/Apply_for_Registration_Normal_Taxpayer.htm")
                        )
                        context.startActivity(intent)
                    }
                )
            } else {
                StartingText(text = headerValue, style = normal11Text500)
            }
        }
    }
}

@Composable
fun HyperlinkText(
    textColor: Color = hyperRedColor,
    style: TextStyle = normal11Text500,
    onSahamatiClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("visit ")
        pushStringAnnotation(tag = "URL", annotation = "gst-settings")
        withStyle(style = SpanStyle(color = textColor)) { append("GST-Settings") }
        pop()
        append(" to enable GST API access")
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, end = 5.dp)
    ) {
        ClickableText(
            text = annotatedString,
            style = style.copy(color = Color.Black),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        when (annotation.item) {
                            "sahamati" -> onSahamatiClick()
                        }
                    }
            }
        )
    }
}

@Preview
@Composable
fun GstInvoiceLoanScreenPreview() {
    GstInvoiceLoanScreen(navController = NavHostController(LocalContext.current), "Invoice Loan")
}
