package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.sidemenu.ShowLoans

@Composable
fun PrePaymentScreen(navController: NavHostController) {
    val context = LocalContext.current
    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val loanListLoading by loanAgreementViewModel.loanListLoading.collectAsState()
    val loanListLoaded by loanAgreementViewModel.loanListLoaded.collectAsState()
    val loanList by loanAgreementViewModel.loanList.collectAsState()

    val showInternetScreen by loanAgreementViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by loanAgreementViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by loanAgreementViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by loanAgreementViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by loanAgreementViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by loanAgreementViewModel.middleLoan.observeAsState(false)
    val errorMessage by loanAgreementViewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        else -> {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.pre_payment),
                showBackButton = true,
                onBackClick = { navigateApplyByCategoryScreen(navController) },
                backgroundColor = appWhite,
                contentStart = 0.dp,
                contentEnd = 0.dp
            ) {
                SearchBar(
                    searchValue = searchQuery,
                    placeHolderText = "Search By Lender",
                    isFilterNeeded = false,
                    onSearchQueryChanged = { searchQuery = it }
                )
                StartingText(
                    text = stringResource(R.string.loan_list),
                    style = normal16Text700,
                    textColor = appBlack,
                    alignment = Alignment.Center,
                    top = 30.dp,
                    bottom = 20.dp
                )
                if (loanListLoading) {
                    CenterProgress()
                } else {
                    if (loanListLoaded) {
                        ShowLoans(
                            loanList = loanList,
                            navController = navController,
                            showActiveLoanScreen = true,
                            searchQuery = searchQuery
                        )
                    } else {
                        loanAgreementViewModel.completeLoanList(context)
                    }
                }
            }
        }
    }
}
