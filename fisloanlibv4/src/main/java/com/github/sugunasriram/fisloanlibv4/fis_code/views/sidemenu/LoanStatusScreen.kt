package com.github.sugunasriram.fisloanlibv4.fis_code.views.sidemenu

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.gray4E
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanStatusCardGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanStatusCardRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanStatusGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanStatusRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.NoExistingLoanScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan.SearchBar
import com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan.loanAmountValue
import java.util.Locale

@Composable
fun LoanStatusScreen(navController: NavHostController) {
    val tabs = listOf("Active Loans", "Inactive Loans")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

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

    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.loan_status),
        showBackButton = true,
        onBackClick = { navigateApplyByCategoryScreen(navController) },
        backgroundColor = appWhite,
        contentStart = 0.dp,
        contentEnd = 0.dp
    ) {
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = appWhite,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp),
                        color = appOrange
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier
                            .background(color = appWhite, shape = RoundedCornerShape(5.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) appOrange else checkBoxGray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            SearchBar(
                searchValue = searchQuery,
                placeHolderText = "Search By Lender",
                isFilterNeeded = false,
                onSearchQueryChanged = { searchQuery = it }
            )
            when (selectedTabIndex) {
                0 -> ActiveLoanScreen(
                    navController = navController, showInternetScreen = showInternetScreen,
                    showTimeOutScreen = showTimeOutScreen, middleLoan = middleLoan,
                    showServerIssueScreen = showServerIssueScreen, context = context,
                    unexpectedErrorScreen = unexpectedErrorScreen, loanList = loanList,
                    unAuthorizedUser = unAuthorizedUser, loanListLoading = loanListLoading,
                    loanListLoaded = loanListLoaded, errorMessage = errorMessage,
                    loanAgreementViewModel = loanAgreementViewModel, showActiveLoanScreen = true,
                    searchQuery = searchQuery
                )

                1 -> ActiveLoanScreen(
                    navController = navController, showInternetScreen = showInternetScreen,
                    showTimeOutScreen = showTimeOutScreen, middleLoan = middleLoan,
                    showServerIssueScreen = showServerIssueScreen, context = context,
                    unexpectedErrorScreen = unexpectedErrorScreen, loanList = loanList,
                    unAuthorizedUser = unAuthorizedUser, loanListLoading = loanListLoading,
                    loanListLoaded = loanListLoaded, errorMessage = errorMessage,
                    loanAgreementViewModel = loanAgreementViewModel, showActiveLoanScreen = false,
                    searchQuery = searchQuery
                )
            }
        }
    }
}

@Composable
fun ActiveLoanScreen(
    navController: NavHostController,
    showInternetScreen: Boolean,
    showTimeOutScreen: Boolean,
    middleLoan: Boolean,
    showServerIssueScreen: Boolean,
    unexpectedErrorScreen: Boolean,
    unAuthorizedUser: Boolean,
    loanListLoading: Boolean,
    loanListLoaded: Boolean,
    errorMessage: String,
    loanAgreementViewModel: LoanAgreementViewModel,
    context: Context,
    loanList: CustomerLoanList?,
    showActiveLoanScreen: Boolean,
    searchQuery: String
) {
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        else -> {
            ActiveLoanScreenView(
                loanListLoading = loanListLoading,
                loanListLoaded = loanListLoaded,
                loanAgreementViewModel = loanAgreementViewModel,
                context = context,
                loanList = loanList,
                navController = navController,
                showActiveLoanScreen = showActiveLoanScreen,
                searchQuery = searchQuery
            )
        }
    }
}

@Composable
fun ActiveLoanScreenView(
    loanListLoading: Boolean,
    loanListLoaded: Boolean,
    loanAgreementViewModel: LoanAgreementViewModel,
    context: Context,
    loanList: CustomerLoanList?,
    navController: NavHostController,
    showActiveLoanScreen: Boolean,
    searchQuery: String
) {
    if (loanListLoading) {
        CenterProgress()
    } else {
        if (loanListLoaded) {
            ShowLoans(
                loanList = loanList,
                navController = navController,
                showActiveLoanScreen = showActiveLoanScreen,
                searchQuery = searchQuery
            )
        } else {
            loanAgreementViewModel.completeLoanList(context)
        }
    }
}

@Composable
fun ShowLoans(
    loanList: CustomerLoanList?,
    navController: NavHostController,
    showActiveLoanScreen: Boolean,
    searchQuery: String
) {
    val filteredBySearch = loanList?.data?.filter { item ->
        val lenderName = item.providerDescriptor?.name.orEmpty().lowercase()
        val amount = item.quoteBreakUp?.firstOrNull {
            it?.title?.contains("principal", ignoreCase = true) == true
        }?.value.orEmpty().lowercase()

        val query = searchQuery.trim().lowercase()
        query.isBlank() || lenderName.contains(query) || amount.contains(query)
    }.orEmpty()

    // Now filter by loan status
    val finalList = filteredBySearch.filter { item ->
        item.fulfillments?.any { fulfilment ->
            fulfilment?.state?.descriptor?.name?.let { status ->
                val isInactive = status.contains("closed", ignoreCase = true) ||
                    status.contains("completed", ignoreCase = true) ||
                    status.contains("cancelled", ignoreCase = true) ||
                    status.contains("rejected", ignoreCase = true)
                return@let if (showActiveLoanScreen) !isInactive else isInactive
            } ?: false
        } ?: false
    }

    if (finalList.isEmpty()) {
        Spacer(modifier = Modifier.height(120.dp))
        NoExistingLoanScreen()
    } else {
        finalList.forEach { data ->
            data.fulfillments?.forEach { fulfilment ->
                fulfilment?.state?.descriptor?.name?.let { loanStatus ->
                    val cardColor = when {
                        loanStatus.contains("Disbursed", ignoreCase = true) -> loanStatusCardGreen
                        loanStatus.contains("completed", ignoreCase = true) -> loanStatusCardRed
                        loanStatus.equals("closed", ignoreCase = true) -> loanStatusCardRed
                        else -> backgroundOrange
                    }
                    val statusColor = when {
                        loanStatus.contains("Disbursed", ignoreCase = true) -> loanStatusGreen
                        loanStatus.contains("completed", ignoreCase = true) -> loanStatusRed
                        loanStatus.equals("closed", ignoreCase = true) -> loanStatusRed
                        else -> appOrange
                    }

                    LoanStatusCard(
                        data = data,
                        navController = navController,
                        cardColor = cardColor,
                        statusColor = statusColor,
                        loanStatus = loanStatus
                    )
                }
            }
        }
    }
}
// @Composable
// fun ShowLoans(
//    loanList: CustomerLoanList?, navController: NavHostController, showActiveLoanScreen: Boolean,
//    searchQuery: String
// ) {
//    val filteredList = loanList?.data?.filter { item ->
//        val lenderName = item.providerDescriptor?.name.orEmpty().lowercase()
//        val amount = item.quoteBreakUp?.firstOrNull {
//            it?.title?.contains("principal", ignoreCase = true) == true
//        }?.value.orEmpty().lowercase()
//
//        val query = searchQuery.trim().lowercase()
//        query.isBlank() || lenderName.contains(query) || amount.contains(query)
//    }
//    if (filteredList.isNullOrEmpty()) {
//        Spacer(modifier = Modifier.height(120.dp))
//        NoExistingLoanScreen()
//    } else {
//        filteredList.forEach { data ->
//            data.fulfillments?.forEach { fulfilment ->
//                fulfilment?.state?.descriptor?.name?.let { loanStatus ->
//                    val cardColor = when {
//                        loanStatus.contains("Disbursed", ignoreCase = true) -> loanStatusCardGreen
//                        loanStatus.contains("completed", ignoreCase = true) -> loanStatusCardRed
//                        loanStatus.equals("closed", ignoreCase = true) -> loanStatusCardRed
//                        else -> backgroundOrange
//                    }
//                    val statusColor = when {
//                        loanStatus.contains("Disbursed", ignoreCase = true) -> loanStatusGreen
//                        loanStatus.contains("completed", ignoreCase = true) -> loanStatusRed
//                        loanStatus.equals("closed", ignoreCase = true) -> loanStatusRed
//                        else -> appOrange
//                    }
//
//                    val isInactive = loanStatus.contains("closed", ignoreCase = true) ||
//                            loanStatus.contains("completed", ignoreCase = true) ||
//                            loanStatus.contains("cancelled", ignoreCase = true) ||
//                            loanStatus.contains("rejected", ignoreCase = true)
//
//                    if (isInactive && !showActiveLoanScreen) {
//                        LoanStatusCard(
//                            data = data,
//                            navController = navController,
//                            cardColor = cardColor,
//                            statusColor = statusColor,
//                            loanStatus = loanStatus
//                        )
//                    } else if (!isInactive && showActiveLoanScreen) {
//                        LoanStatusCard(
//                            data = data,
//                            navController = navController,
//                            cardColor = cardColor,
//                            statusColor = statusColor,
//                            loanStatus = loanStatus
//                        )
//                    }
//
//
//                }
//            }
//        }
//    }
// }

@Composable
fun LoanStatusCard(
    data: OfferResponseItem,
    navController: NavHostController,
    cardColor: Color,
    statusColor: Color,
    loanStatus: String
) {
    val applicationId = data.itemId
//    val applicationId = data.id
    data.itemDescriptor?.let { itemDescriptor ->
        itemDescriptor.name?.let { loanType ->
            Spacer(modifier = Modifier.height(10.dp))
            DisplayCard(
                cardColor = cardColor,
                borderColor = Color.Transparent,
                modifier = Modifier.clickable {
                    data.id?.let { orderId ->
                        navigateToLoanDetailScreen(
                            navController = navController,
                            orderId = orderId,
                            fromFlow = loanType,
                            fromScreen = "Loan Status"
                        )
                    }
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)
                    ) {
                        StartingText(
                            text = "Loan ID",
                            bottom = 5.dp,
                            top = 5.dp,
                            style = normal16Text500,
                            textColor = gray4E
                        )
                        if (applicationId != null) {
                            StartingText(
                                text = applicationId,
                                bottom = 20.dp,
                                textOverflow = TextOverflow.Ellipsis,
                                style = normal16Text700,
                                textColor = appBlack
                            )
                        }

                        StartingText(
                            text = "Lender",
                            bottom = 5.dp,
                            style = normal16Text500,
                            textColor = gray4E
                        )
                        data.providerDescriptor?.name?.let { lenderName ->
                            StartingText(
                                text = lenderName,
                                bottom = 10.dp,
                                style = normal16Text700,
                                textColor = appBlack
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        RegisterText(
                            text = loanStatus,
                            bottom = 5.dp,
                            end = 8.dp,
                            style = normal14Text700,
                            textColor = statusColor,
                            textAlign = TextAlign.End,
                            boxAlign = Alignment.CenterEnd
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .background(
                                        color = statusColor,
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            bottomStart = 8.dp
                                        )
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                data.quoteBreakUp?.forEach { quoteBreakUp ->
                                    quoteBreakUp?.let {
                                        it.title?.let { title ->
                                            if (title.lowercase(Locale.ROOT) == "principal"
                                            ) {
                                                // if (title.toLowerCase(Locale.ROOT).equals("principal")) {
                                                it.value?.let { value ->
                                                    loanAmountValue = value
                                                    RegisterText(
                                                        text = loanAmountValue,
                                                        bottom = 0.dp,
                                                        style = normal16Text700,
                                                        textColor = appWhite,
                                                        textAlign = TextAlign.End,
                                                        boxAlign = Alignment.CenterEnd
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoanStatusPreview() {
    LoanStatusScreen(rememberNavController())
}
