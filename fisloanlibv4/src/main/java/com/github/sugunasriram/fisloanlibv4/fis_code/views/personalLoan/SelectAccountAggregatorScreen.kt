package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ImageTextWithRadioButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.lightishGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400

val bankList = arrayListOf<BankItem?>(
    BankItem(
        bankName = "FinVu",
        imageBank = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBaGPXz45LQV0sjCCqrERZUTWGDT7c3j9adcVGIhTfzw&s",
        id = "15678"
    )
)

@Composable
fun SelectAccountAggregatorScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String
) {
    BackHandler {
        navigateToAccountAggregatorScreen(navController, loanPurpose, fromFlow, id = id, transactionId = transactionId, url = url)
    }
    val context = LocalContext.current
    val (selectedOption, setSelectedOption) = remember { mutableStateOf<String?>(if (bankList.size == 1) bankList[0]?.bankName else null) }
    var showError by remember { mutableStateOf(false) }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.select_your_account_aggregator),
        showBackButton = true,
        onBackClick = {
            navigateToAccountAggregatorScreen(
                navController = navController,
                loanPurpose = loanPurpose,
                fromFlow = fromFlow,
                id = id,
                transactionId = transactionId,
                url = url
            )
        },
        showBottom = true,
        showErrorMsg = showError,
        errorMsg = stringResource(R.string.please_select_account_aggregator),
        showSingleButton = true,
        primaryButtonText = stringResource(R.string.next),
        onPrimaryButtonClick = {
            if (selectedOption != null) {
                navigateToWebViewFlowOneScreen(
                    navController,
                    loanPurpose,
                    fromFlow,
                    id = id,
                    transactionId = transactionId,
                    url = url
                )
//                navigateToLoanOffersScreen(navController,loanPurpose, fromFlow)
//                navigateToWebViewFlowOneScreen(navController, purpose, fromFlow)
                showError = false
            } else {
                showError = true
            }
        },
        backgroundColor = appWhite
    ) {
        StartingText(
            text = stringResource(id = R.string.choose_rbi_approved_account), textColor = hintGray,
            start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp, style = normal14Text400,
            textAlign = TextAlign.Center, alignment = Alignment.TopCenter
        )
        ImageTextWithRadioButton(
            backGroundColor = lightishGray,
            radioOptions = bankList,
            top = 2.dp,
            selectedOption = selectedOption,
            onOptionSelected = {
                setSelectedOption(it)
                showError = false
            }
        )
    }
}

@Preview
@Composable
fun SelectAAScreenPreview() {
    SelectAccountAggregatorScreen(
        rememberNavController(),
        "loanPurpose",
        "fromFlow",
        "id",
        "tnxId",
        "url"
    )
}
