package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ImageTextWithCheckbox
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToSelectAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankList
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AccountDetailViewModel

@Composable
fun SelectBankScreen(navController: NavHostController, loanPurpose: String, fromFlow: String,
                     id:String, transactionId:String,url:String) {

    BackHandler {
        navigateToSelectAccountAggregatorScreen(
            navController = navController, loanPurpose = loanPurpose, fromFlow = fromFlow,
            id = id, transactionId = transactionId,url = url
        )
//        if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
//            navigateToAccountAggregatorScreen(
//                navController = navController, loanPurpose = loanPurpose, fromFlow = fromFlow
//            )
//        }
    }

    val context = LocalContext.current

    val accountDetailViewModel: AccountDetailViewModel = viewModel()
    val bankList by accountDetailViewModel.getBankList.collectAsState()
    val inProgress by accountDetailViewModel.inProgress.collectAsState()
    val isCompleted by accountDetailViewModel.isCompleted.collectAsState()
    val showInternetScreen by accountDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by accountDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by accountDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by accountDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by accountDetailViewModel.unAuthorizedUser.observeAsState(false)
    val navigationToSignIn by accountDetailViewModel.navigationToSignIn.collectAsState()

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            SelectBankView(
                inProgress = inProgress, isCompleted = isCompleted,
                navController = navController,
                context = context, accountDetailViewModel = accountDetailViewModel,
                fromFlow = fromFlow, loanPurpose = loanPurpose, bankList = bankList,
                id = id, transactionId = transactionId,url = url
            )
        }
    }
}

@Composable
fun SelectBankView(
    inProgress: Boolean,
    isCompleted: Boolean,
    navController: NavHostController,
    context: Context,
    accountDetailViewModel: AccountDetailViewModel,
    fromFlow: String,
    loanPurpose: String,
    bankList: BankList?,
    id:String, transactionId:String,url:String
) {
    val selectedOptions = remember { mutableStateListOf<String>() }
    if (inProgress) {
        CenterProgress()
    } else {
        if (!isCompleted) {
            accountDetailViewModel.getBankList(context, navController)
        } else {
            var showError by remember { mutableStateOf(false) }
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.select_the_lender),
                showBackButton = true,
                onBackClick = {
                    navigateToSelectAccountAggregatorScreen(
                        navController = navController, loanPurpose = loanPurpose, fromFlow = fromFlow,
                        id = id, transactionId = transactionId,url = url)
                },
                showBottom = true,
                showErrorMsg = showError,
                errorMsg = stringResource(R.string.please_select_atleast_one_lender),
                showCheckBox = true,
                checkBoxText = stringResource(R.string.select_all),
                checkboxState = selectedOptions.size == bankList?.data?.size,
                onCheckBoxChange = {
                    val allSelected = selectedOptions.size == bankList?.data?.size
                    val updatedList =
                        if (allSelected) emptyList() else bankList?.data?.mapNotNull { it?.bankName }
                            ?: emptyList()
                    selectedOptions.clear()
                    selectedOptions.addAll(updatedList)
                    showError = false
                },
                showSingleButton = true,
                primaryButtonText = stringResource(R.string.next),
                onPrimaryButtonClick = {
                    if (selectedOptions.isNotEmpty()) {
                        Log.d("Selected Lenders:", selectedOptions.toList().toString())
                        navigateToWebViewFlowOneScreen(navController, loanPurpose,
                            fromFlow,id = id, transactionId = transactionId,url = url)
//                        onSelectBankClick(
//                        selectedOption = selectedOption, navController = navController,
//                        fromFlow = fromFlow, purpose = purpose, context = context
//                    )
                        showError = false
                    } else {
                        showError = true
                    }
                },
                backgroundColor = appWhite
            ) {
                if (bankList != null) {
                    ImageTextWithCheckbox(
                        radioOptions = bankList.data ?: arrayListOf(),
                        selectedOptions = selectedOptions,
                        onOptionSelected = { updatedList ->
                            selectedOptions.clear()
                            selectedOptions.addAll(updatedList)
                            showError = false
                        }
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun SelectBankPreview() {
    SelectBankScreen(rememberNavController(), "", "Personal Loan",
        "id", "tnxId", "url")
}




