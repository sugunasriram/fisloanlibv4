package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.BulletImageWithText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.AppScreens
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToSelectAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400

@Composable
fun AccountAggregatorScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String
) {
    BackHandler {
        onGoBackClick(navController = navController, fromFlow = fromFlow, loanPurpose = loanPurpose)
    }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.share_bank_statement),
        showBackButton = true,
        onBackClick = {
            onGoBackClick(
                navController = navController,
                fromFlow = fromFlow,
                loanPurpose = loanPurpose
            )
        },
        showBottom = true,
        showHyperText = true,
        showSingleButton = true,
        primaryButtonText = stringResource(R.string.next),
        onPrimaryButtonClick = {
            navigateToSelectAccountAggregatorScreen(
                navController = navController,
                loanPurpose = loanPurpose,
                fromFlow = fromFlow,
                id = id,
                transactionId = transactionId,
                url = url
            )
        },
        backgroundColor = appWhite
    ) {
        LoanStatusTracker(stepId = 2)
        StartingText(
            text = stringResource(id = R.string.share_bank_statements), textColor = appBlack,
            start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp, style = normal14Text400,
            textAlign = TextAlign.Start, alignment = Alignment.TopCenter
        )
        Image(
            painter = painterResource(id = R.drawable.account_aggregator_image),
            contentDescription = stringResource(id = R.string.account_agreegator),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            contentScale = ContentScale.Crop
        )
        BulletImageWithText()
        BulletImageWithText(text = stringResource(id = R.string.no_branch_visits))
        BulletImageWithText(text = stringResource(id = R.string.rbi_licensed_entities))
        BulletImageWithText(
            text = stringResource(id = R.string.revoke_consent_at_any_time),
            bottom = 15.dp
        )
    }
}

fun onGoBackClick(navController: NavHostController, fromFlow: String, loanPurpose: String) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        navController.popBackStack()
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        navigateToLoanProcessScreen(
            navController = navController,
            transactionId = "Sugu",
            statusId = 11,
            responseItem = "No Need",
            offerId = "1234",
            fromFlow = fromFlow
        )
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
          navController.popBackStack()
    }
}

// @Preview(widthDp = 352, heightDp = 737)
@Preview
@Composable
fun AccountAggregatorScreenPreview() {
    AccountAggregatorScreen(
        rememberNavController(),
        "loanPurpose",
        "fromFlow",
        "id",
        "tnxId",
        "url"
    )
}
