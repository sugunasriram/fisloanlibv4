package com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TextValueInARow
import com.github.sugunasriram.fisloanlibv4.fiscode.components.VerticalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.azureBlueColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal35Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.whiteGreenColor
import com.github.sugunasriram.fisloanlibv4.fiscode.views.StatusCard

@Composable
fun LoanStatusDetailScreen(navController: NavHostController) {
    val loanStatus = true
    ScreenWithHamburger(isSelfScrollable = false, navController = navController) {
        RegisterText(
            text = stringResource(id = R.string.loan_status_detail),
            textColor = appBlueTitle,
            bottom = 20.dp,
            top = 20.dp,
            style = normal35Text700
        )
        FullWidthRoundShapedCard(
            onClick = { /*TODO*/ },
            cardColor = azureBlueColor,
            bottomPadding = 0.dp,
            modifier = Modifier.alpha(0.5f)
        ) {
            FullWidthRoundShapedCard(
                start = 8.dp,
                end = 8.dp,
                top = 5.dp,
                bottom = 5.dp,
                cardColor = whiteGreenColor,
                onClick = { }
            ) {
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.loan_amount),
                    textValue = stringResource(id = R.string.loan_amount_value),
                    style = normal20Text400
                )
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.invoice_no),
                    style = normal20Text400,
                    textValue = stringResource(id = R.string.invoice_value)
                )
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.lender),
                    style = normal20Text400,
                    textValue = stringResource(id = R.string.lender_value)
                )
                TextValueInARow(
                    textHeader = stringResource(id = R.string.status),
                    textValue = stringResource(id = R.string.status_value),
                    style = normal20Text400,
                    textColorValue = azureBlueColor
                )
            }
        }
        StatusCard(
            showSubText = false,
            processDone = false,
            cardText = stringResource(id = R.string.request_submitted),
            image = if (!loanStatus) R.drawable.one_image_blue else R.drawable.one_image_black
        )
        VerticalDivider()
        StatusCard(
            processDone = true,
            showSubText = false,
            cardText = stringResource(id = R.string.approved),
            image = if (loanStatus) R.drawable.two_image_blue else R.drawable.two_image_black,
            top = 0.dp
        )
        VerticalDivider()
        StatusCard(
            cardText = stringResource(id = R.string.disbursement),
            image = if (!loanStatus) R.drawable.three_image_blue else R.drawable.three_image_black,
            top = 0.dp
        )
        if (loanStatus) {
            CurvedPrimaryButtonFull(
                text = stringResource(id = R.string.sign_loan_agreement_caps),
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 40.dp)
            ) {}
        }
        CurvedPrimaryButtonFull(
            text = stringResource(id = R.string.cancel_loan_request_caps),
            backgroundColor = appRed,
            modifier = Modifier.padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 20.dp,
                top = if (loanStatus) 40.dp else 20.dp
            )
        ) {}
    }
}

@Preview
@Composable
fun LoanStatusDetailScreenPreview() {
    LoanStatusDetailScreen(navController = NavHostController(LocalContext.current))
}
