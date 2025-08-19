package com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScheduleScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.CustomerLoanList
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.gray4E
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanStatusCardGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanStatusCardRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanStatusGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanStatusRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoExistingLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.SearchBar
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.loanAmountValue
import java.util.Locale
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.ItemTagsItem
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun LoanStatusScreen(navController: NavHostController, loanType:String) {
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
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        else -> {
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
                            navController = navController,
                            context = context,
                            loanList = loanList,
                            loanListLoading = loanListLoading,
                            loanListLoaded = loanListLoaded,
                            loanAgreementViewModel = loanAgreementViewModel,
                            showActiveLoanScreen = true,
                            searchQuery = searchQuery, loanType = loanType
                        )

                        1 -> ActiveLoanScreen(
                            navController = navController,
                            context = context,
                            loanList = loanList,
                            loanListLoading = loanListLoading,
                            loanListLoaded = loanListLoaded,
                            loanAgreementViewModel = loanAgreementViewModel,
                            showActiveLoanScreen = false,
                            searchQuery = searchQuery, loanType=loanType
                        )
                    }
                }
            }
        }
        }
}

@Composable
fun ActiveLoanScreen(
    navController: NavHostController,
    loanListLoading: Boolean,
    loanListLoaded: Boolean,
    loanAgreementViewModel: LoanAgreementViewModel,
    context: Context,
    loanList: CustomerLoanList?,
    showActiveLoanScreen: Boolean,
    searchQuery: String,
    loanType:String
) {
    ActiveLoanScreenView(
        loanListLoading = loanListLoading,
        loanListLoaded = loanListLoaded,
        loanAgreementViewModel = loanAgreementViewModel,
        context = context,
        loanList = loanList,
        navController = navController,
        showActiveLoanScreen = showActiveLoanScreen,
        searchQuery = searchQuery, loanType= loanType
    )
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
    searchQuery: String,
    loanType:String
) {
    if (loanListLoading) {
        CenterProgress()
    } else {
        if (loanListLoaded) {
            ShowLoans(
                loanList = loanList,
                navController = navController,
                showActiveLoanScreen = showActiveLoanScreen,
                searchQuery = searchQuery,
                fromScreen = "Loan Status"
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            loanAgreementViewModel.completeLoanList(context,loanType)
        }
    }
}

@Composable
fun ShowLoans(
    loanList: CustomerLoanList?,
    navController: NavHostController,
    showActiveLoanScreen: Boolean,
    searchQuery: String,
    fromScreen: String
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
            fulfilment?.state?.descriptor?.code?.let { status ->
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
        if (fromScreen == "PrePayment") {
            StartingText(
                text = stringResource(R.string.loan_list),
                style = normal16Text700,
                textColor = appBlack,
                alignment = Alignment.Center,
                top = 15.dp,
                bottom = 10.dp
            )
        }
        finalList.forEach { data ->
            data.fulfillments?.forEach { fulfilment ->
                fulfilment?.state?.descriptor?.code?.let { loanStatus ->
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
                        loanStatus = loanStatus,
                        fromScreen = fromScreen
                    )
                }
            }
        }
    }
}

private val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}
//@Preview
//@Composable
//fun LoanStatusCardPreview(){
//    val offerResponse = "{\n" +
//            "  \"context\": {\n" +
//            "    \"domain\": \"ONDC:FIS12\",\n" +
//            "    \"location\": {\n" +
//            "      \"country\": {\n" +
//            "        \"code\": \"IND\"\n" +
//            "      },\n" +
//            "      \"city\": {\n" +
//            "        \"code\": \"*\"\n" +
//            "      }\n" +
//            "    },\n" +
//            "    \"transaction_id\": \"de326ecb-b1fe-44aa-8f8c-4f0a4024069a\",\n" +
//            "    \"message_id\": \"a864519e-ea12-43fa-b141-1639d226e174\",\n" +
//            "    \"action\": \"on_search\",\n" +
//            "    \"timestamp\": \"2024-05-06T04:08:05.743Z\",\n" +
//            "    \"version\": \"2.2.0\",\n" +
//            "    \"bap_uri\": \"https://bap.purchase-finance.becknprotocol.io/\",\n" +
//            "    \"bap_id\": \"bap.purchase-finance.becknprotocol.io\",\n" +
//            "    \"ttl\": \"PT10M\",\n" +
//            "    \"bpp_id\": \"bpp.purchase-finance.becknprotoco.org\",\n" +
//            "    \"bpp_uri\": \"https://bpp.purchase-finance.becknprotoco.org\"\n" +
//            "  },\n" +
//            "  \"message\": {\n" +
//            "    \"catalog\": {\n" +
//            "      \"descriptor\": {\n" +
//            "        \"name\": \"ICICI Bank\"\n" +
//            "      },\n" +
//            "      \"providers\": [\n" +
//            "        {\n" +
//            "          \"id\": \"PROVIDER_ID\",\n" +
//            "          \"descriptor\": {\n" +
//            "            \"images\": [\n" +
//            "              {\n" +
//            "                \"url\": \"https://www.icicibank.com/content/dam/icicibank/india/assets/images/header/logo.png\",\n" +
//            "                \"size_type\": \"sm\"\n" +
//            "              }\n" +
//            "            ],\n" +
//            "            \"name\": \"ICICI Bank\",\n" +
//            "            \"short_desc\": \"ICICI Bank Ltd\",\n" +
//            "            \"long_desc\": \"ICICI Bank Ltd, India.\"\n" +
//            "          },\n" +
//            "          \"categories\": [\n" +
//            "            {\n" +
//            "              \"id\": \"101123\",\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"PURCHASE_FINANCE\",\n" +
//            "                \"name\": \"Purchase Finance\"\n" +
//            "              }\n" +
//            "            },\n" +
//            "            {\n" +
//            "              \"id\": \"101125\",\n" +
//            "              \"parent_category_id\": \"101123\",\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"ELECTRONICS_PURCHASE_FINANCE\",\n" +
//            "                \"name\": \"Electronics Purchase Finance\"\n" +
//            "              }\n" +
//            "            }\n" +
//            "          ],\n" +
//            "          \"items\": [\n" +
//            "            {\n" +
//            "              \"id\": \"ITEM_ID_ELECTRONICS\",\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"LOAN\",\n" +
//            "                \"name\": \"Loan\"\n" +
//            "              },\n" +
//            "              \"category_ids\": [\n" +
//            "                \"101123\",\n" +
//            "                \"101125\"\n" +
//            "              ],\n" +
//            "              \"xinput\": {\n" +
//            "                \"form\": {\n" +
//            "                  \"id\": \"F02\"\n" +
//            "                },\n" +
//            "                \"form_response\": {\n" +
//            "                  \"status\": \"SUCCESS\",\n" +
//            "                  \"submission_id\": \"F02_SUBMISSION_ID\"\n" +
//            "                }\n" +
//            "              }\n" +
//            "            },\n" +
//            "            {\n" +
//            "              \"id\": \"CHILD_ITEM_ID_I1\",\n" +
//            "              \"parent_item_id\": \"ITEM_ID_ELECTRONICS\",\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"LOAN\",\n" +
//            "                \"name\": \"Loan\"\n" +
//            "              },\n" +
//            "              \"category_ids\": [\n" +
//            "                \"101123\",\n" +
//            "                \"101125\"\n" +
//            "              ],\n" +
//            "              \"price\": {\n" +
//            "                \"currency\": \"INR\",\n" +
//            "                \"value\": \"70000\"\n" +
//            "              },\n" +
//            "              \"tags\": [\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"INFO\",\n" +
//            "                    \"name\": \"Information\"\n" +
//            "                  },\n" +
//            "                  \"list\": [\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE\",\n" +
//            "                        \"name\": \"Interest Rate\",\n" +
//            "                        \"short_desc\": \"Loans starting from 12% (p.a)\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"12 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"TERM\",\n" +
//            "                        \"name\": \"Loan Term\",\n" +
//            "                        \"short_desc\": \"Loans Duration 5 months\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P5M\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE_TYPE\",\n" +
//            "                        \"name\": \"Interest rate type\",\n" +
//            "                        \"short_desc\": \"Fixed Interest type\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"FIXED\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"APPLICATION_FEE\",\n" +
//            "                        \"name\": \"Application Fees\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"FORECLOSURE_FEE\",\n" +
//            "                        \"name\": \"Foreclosure Penalty\",\n" +
//            "                        \"short_desc\": \"Loan Foreclosure Penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0.5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE_CONVERSION_CHARGE\",\n" +
//            "                        \"name\": \"interest rate conversion charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"DELAY_PENALTY_FEE\",\n" +
//            "                        \"name\": \"Delayed payments penalty\",\n" +
//            "                        \"short_desc\": \"Delayed payments penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_PENALTY_FEE\",\n" +
//            "                        \"name\": \"Other Penal Charges\",\n" +
//            "                        \"short_desc\": \"Other Penal Charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"ANNUAL_PERCENTAGE_RATE\",\n" +
//            "                        \"name\": \"Annual Percentage Rate\",\n" +
//            "                        \"short_desc\": \"Effective annualized interest rate in percentage computed on net disbursed amount using IRR approach and reducing balance method\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"REPAYMENT_FREQUENCY\",\n" +
//            "                        \"name\": \"Repayment Frequency\",\n" +
//            "                        \"short_desc\": \"Repayment Frequency by borrower\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P1M\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"NUMBER_OF_INSTALLMENTS\",\n" +
//            "                        \"name\": \"Number of installments of repayment\",\n" +
//            "                        \"short_desc\": \"Number of installments borrower has to make to payback the loan\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"7\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"TNC_LINK\",\n" +
//            "                        \"name\": \"Terms & Conditions\",\n" +
//            "                        \"short_desc\": \"Terms and Conditions\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"https://icicibank.com/loan/tnc.html\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"COOL_OFF_PERIOD\",\n" +
//            "                        \"name\": \"cool off period\",\n" +
//            "                        \"short_desc\": \"Cooling off/look-up period during which borrower shall not be charged any penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P30D\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INSTALLMENT_AMOUNT\",\n" +
//            "                        \"name\": \"Installment amount\",\n" +
//            "                        \"short_desc\": \"Each installment amount of repayment\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"10000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"PRINCIPAL_AMOUNT\",\n" +
//            "                        \"name\": \"Principal\",\n" +
//            "                        \"short_desc\": \"Loan Principal\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"65000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_AMOUNT\",\n" +
//            "                        \"name\": \"Interest\",\n" +
//            "                        \"short_desc\": \"Loan Interest\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"4000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"PROCESSING_FEE\",\n" +
//            "                        \"name\": \"Processing Fee\",\n" +
//            "                        \"short_desc\": \"Processing Fee\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"500 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_UPFRONT_CHARGES\",\n" +
//            "                        \"name\": \"other upfront charges\",\n" +
//            "                        \"short_desc\": \"other upfront charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INSURANCE_CHARGES\",\n" +
//            "                        \"name\": \"Insurance Charges\",\n" +
//            "                        \"short_desc\": \"Insurance Charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"500 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"NET_DISBURSED_AMOUNT\",\n" +
//            "                        \"name\": \"net disbursed amount\",\n" +
//            "                        \"short_desc\": \"net disbursed amount\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"64000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_CHARGES\",\n" +
//            "                        \"name\": \"other charges\",\n" +
//            "                        \"short_desc\": \"other charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OFFER_VALIDITY\",\n" +
//            "                        \"name\": \"Offer validity\",\n" +
//            "                        \"short_desc\": \"Describes the offer validity\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P15D\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"MINIMUM_DOWNPAYMENT\",\n" +
//            "                        \"name\": \"Minimum Downpayment\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SUBVENTION_RATE\",\n" +
//            "                        \"name\": \"Subvention Rate\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SELLER_SUBVENTION_RATE\",\n" +
//            "                        \"name\": \"Subvention Rate\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"2 %\"\n" +
//            "                    }\n" +
//            "                  ],\n" +
//            "                  \"display\": true\n" +
//            "                }\n" +
//            "              ]\n" +
//            "            },\n" +
//            "            {\n" +
//            "              \"id\": \"CHILD_ITEM_ID_I2\",\n" +
//            "              \"parent_item_id\": \"ITEM_ID_ELECTRONICS\",\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"LOAN\",\n" +
//            "                \"name\": \"Loan\"\n" +
//            "              },\n" +
//            "              \"category_ids\": [\n" +
//            "                \"101123\",\n" +
//            "                \"101125\"\n" +
//            "              ],\n" +
//            "              \"price\": {\n" +
//            "                \"currency\": \"INR\",\n" +
//            "                \"value\": \"65000\"\n" +
//            "              },\n" +
//            "              \"tags\": [\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"INFO\",\n" +
//            "                    \"name\": \"Information\"\n" +
//            "                  },\n" +
//            "                  \"list\": [\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE\",\n" +
//            "                        \"name\": \"Interest Rate\",\n" +
//            "                        \"short_desc\": \"Loans starting from 9% (p.a)\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"9 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"TERM\",\n" +
//            "                        \"name\": \"Loan Term\",\n" +
//            "                        \"short_desc\": \"Loans Duration 9 months\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P9M\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE_TYPE\",\n" +
//            "                        \"name\": \"Interest rate type\",\n" +
//            "                        \"short_desc\": \"Fixed Interest type\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"FIXED\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"APPLICATION_FEE\",\n" +
//            "                        \"name\": \"Application Fees\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"FORECLOSURE_FEE\",\n" +
//            "                        \"name\": \"Foreclosure Penalty\",\n" +
//            "                        \"short_desc\": \"Loan Foreclosure Penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0.5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_RATE_CONVERSION_CHARGE\",\n" +
//            "                        \"name\": \"interest rate conversion charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"DELAY_PENALTY_FEE\",\n" +
//            "                        \"name\": \"Delayed payments penalty\",\n" +
//            "                        \"short_desc\": \"Delayed payments penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"7 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_PENALTY_FEE\",\n" +
//            "                        \"name\": \"Other Penal Charges\",\n" +
//            "                        \"short_desc\": \"Other Penal Charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"ANNUAL_PERCENTAGE_RATE\",\n" +
//            "                        \"name\": \"Annual Percentage Rate\",\n" +
//            "                        \"short_desc\": \"Effective annualized interest rate in percentage computed on net disbursed amount using IRR approach and reducing balance method\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"REPAYMENT_FREQUENCY\",\n" +
//            "                        \"name\": \"Repayment Frequency\",\n" +
//            "                        \"short_desc\": \"Repayment Frequency by borrower\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P1M\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"NUMBER_OF_INSTALLMENTS\",\n" +
//            "                        \"name\": \"Number of installments of repayment\",\n" +
//            "                        \"short_desc\": \"Number of installments borrower has to make to payback the loan\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"7\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"TNC_LINK\",\n" +
//            "                        \"name\": \"Terms & Conditions\",\n" +
//            "                        \"short_desc\": \"Terms and Conditions\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"https://icicibank.com/loan/tnc.html\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"COOL_OFF_PERIOD\",\n" +
//            "                        \"name\": \"cool off period\",\n" +
//            "                        \"short_desc\": \"Cooling off/look-up period during which borrower shall not be charged any penalty\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P30D\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INSTALLMENT_AMOUNT\",\n" +
//            "                        \"name\": \"Installment amount\",\n" +
//            "                        \"short_desc\": \"Each installment amount of repayment\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"10000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"PRINCIPAL_AMOUNT\",\n" +
//            "                        \"name\": \"Principal\",\n" +
//            "                        \"short_desc\": \"Loan Principal\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"60000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INTEREST_AMOUNT\",\n" +
//            "                        \"name\": \"Interest\",\n" +
//            "                        \"short_desc\": \"Loan Interest\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"4000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"PROCESSING_FEE\",\n" +
//            "                        \"name\": \"Processing Fee\",\n" +
//            "                        \"short_desc\": \"Processing Fee\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"500 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_UPFRONT_CHARGES\",\n" +
//            "                        \"name\": \"other upfront charges\",\n" +
//            "                        \"short_desc\": \"other upfront charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"INSURANCE_CHARGES\",\n" +
//            "                        \"name\": \"Insurance Charges\",\n" +
//            "                        \"short_desc\": \"Insurance Charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"500 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"NET_DISBURSED_AMOUNT\",\n" +
//            "                        \"name\": \"net disbursed amount\",\n" +
//            "                        \"short_desc\": \"net disbursed amount\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"60000 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OTHER_CHARGES\",\n" +
//            "                        \"name\": \"other charges\",\n" +
//            "                        \"short_desc\": \"other charges\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OFFER_VALIDITY\",\n" +
//            "                        \"name\": \"Offer validity\",\n" +
//            "                        \"short_desc\": \"Describes the offer validity\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P25D\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"MINIMUM_DOWNPAYMENT\",\n" +
//            "                        \"name\": \"Minimum Downpayment\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"0 INR\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SUBVENTION_RATE\",\n" +
//            "                        \"name\": \"Subvention Rate\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"5 %\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SELLER_SUBVENTION_RATE\",\n" +
//            "                        \"name\": \"Subvention Rate\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"2 %\"\n" +
//            "                    }\n" +
//            "                  ],\n" +
//            "                  \"display\": true\n" +
//            "                }\n" +
//            "              ]\n" +
//            "            }\n" +
//            "          ],\n" +
//            "          \"payments\": [\n" +
//            "            {\n" +
//            "              \"collected_by\": \"BPP\",\n" +
//            "              \"tags\": [\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"BPP_TERMS\",\n" +
//            "                    \"name\": \"BPP Terms of Engagement\"\n" +
//            "                  },\n" +
//            "                  \"display\": false,\n" +
//            "                  \"list\": [\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"BUYER_FINDER_FEES_TYPE\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"PERCENT_ANNUALIZED\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"BUYER_FINDER_FEES_PERCENTAGE\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"1\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SETTLEMENT_WINDOW\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"P30D\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"SETTLEMENT_BASIS\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"INVOICE_RECEIPT\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"MANDATORY_ARBITRATION\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"TRUE\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"COURT_JURISDICTION\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"New Delhi\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"STATIC_TERMS\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"https://bpp.credit.becknprotocol.org/personal-banking/loans/personal-loan\"\n" +
//            "                    },\n" +
//            "                    {\n" +
//            "                      \"descriptor\": {\n" +
//            "                        \"code\": \"OFFLINE_CONTRACT\"\n" +
//            "                      },\n" +
//            "                      \"value\": \"true\"\n" +
//            "                    }\n" +
//            "                  ]\n" +
//            "                }\n" +
//            "              ]\n" +
//            "            }\n" +
//            "          ],\n" +
//            "          \"tags\": [\n" +
//            "            {\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"CONTACT_INFO\",\n" +
//            "                \"name\": \"Contact Info\"\n" +
//            "              },\n" +
//            "              \"list\": [\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"GRO_NAME\",\n" +
//            "                    \"name\": \"Gro name\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"ICICI\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"GRO_EMAIL\",\n" +
//            "                    \"name\": \"Gro email\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"lifeline@iciciprulife.com\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"GRO_CONTACT_NUMBER\",\n" +
//            "                    \"name\": \"Gro contact number\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"1860 266 7766\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"CUSTOMER_SUPPORT_LINK\",\n" +
//            "                    \"name\": \"Customer support link\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"https://buy.iciciprulife.com/buy/GrievanceRedStep.htm?execution=e1s1\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"CUSTOMER_SUPPORT_CONTACT_NUMBER\",\n" +
//            "                    \"name\": \"Customer support contact number\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"1800 1080\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"CUSTOMER_SUPPORT_EMAIL\",\n" +
//            "                    \"name\": \"Customer support email\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"customer.care@icicibank.com\"\n" +
//            "                }\n" +
//            "              ]\n" +
//            "            },\n" +
//            "            {\n" +
//            "              \"descriptor\": {\n" +
//            "                \"code\": \"LSP_INFO\",\n" +
//            "                \"name\": \"Lsp Info\"\n" +
//            "              },\n" +
//            "              \"list\": [\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"LSP_NAME\",\n" +
//            "                    \"name\": \"Lsp name\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"ICICI_LSP\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"LSP_EMAIL\",\n" +
//            "                    \"name\": \"Lsp email\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"lsp@iciciprulife.com\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"LSP_CONTACT_NUMBER\",\n" +
//            "                    \"name\": \"Lsp contact number\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"1860 266 7766\"\n" +
//            "                },\n" +
//            "                {\n" +
//            "                  \"descriptor\": {\n" +
//            "                    \"code\": \"LSP_ADDRESS\",\n" +
//            "                    \"name\": \"Lsp Address\"\n" +
//            "                  },\n" +
//            "                  \"value\": \"One Indiabulls centre, Tower 1, 18th Floor Jupiter mill compound 841, Senapati Bapat Marg, Elphinstone Road, Mumbai 400013\"\n" +
//            "                }\n" +
//            "              ]\n" +
//            "            }\n" +
//            "          ]\n" +
//            "        }\n" +
//            "      ]\n" +
//            "    }\n" +
//            "  }\n" +
//            "}"
//
//    var offerResponseItem = json.decodeFromString<OfferResponseItem>(offerResponse)
//    LoanStatusCard(
//        data = offerResponseItem,
//        navController = rememberNavController(),
//        cardColor = Color.LightGray,
//        statusColor = Color.Green,
//        loanStatus = "Disbursed",
//        fromScreen = "Loan Status"
//    )
//}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun LoanStatusCard(
    data: OfferResponseItem,
    navController: NavHostController,
    cardColor: Color,
    statusColor: Color,
    loanStatus: String,
    fromScreen: String
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    var selectedOffers by remember { mutableStateOf<List<ItemTagsItem>>(emptyList()) }

    // ----------------- Wrap entire card in your CustomModalBottomSheet -----------------
    CustomModalBottomSheet(
        bottomSheetState = bottomSheetState,
        sheetContent = {
            if (selectedOffers.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Available Offers",
                        style = normal16Text700,
                        color = appBlack,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    selectedOffers.forEach { offerTag ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    data.itemDescriptor?.let { itemDescriptor ->
                                        itemDescriptor.code?.let { loanType ->
                                            data.id?.let { orderId ->
                                                navigateToRepaymentScheduleScreen(
                                                    navController = navController,
                                                    orderId = orderId,
                                                    fromFlow = loanType,
                                                    fromScreen = fromScreen
                                                )
                                            }
                                        }
                                    }
                                    coroutineScope.launch { bottomSheetState.hide() }
                                },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = "Available Offer",
                                style = normal14Text700,
                                color = appBlack,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {

//    val applicationId = data.itemId
        val applicationId = data.id
        data.itemDescriptor?.let { itemDescriptor ->
            itemDescriptor.code?.let { loanType ->
                Spacer(modifier = Modifier.height(10.dp))
                DisplayCard(
                    cardColor = cardColor,
                    borderColor = Color.Transparent,
                    modifier = Modifier.clickable {
                        data.id?.let { orderId ->
                            if (!data.itemTags.isNullOrEmpty() && data.itemTags.size > 1) {
                                // multiple offers → open bottom sheet
                                selectedOffers = data.itemTags.filterNotNull()
                                coroutineScope.launch { bottomSheetState.show() }
                            } else {
                                // single offer → go to repayment screen
                                navigateToRepaymentScheduleScreen(
                                    navController = navController,
                                    orderId = orderId,
                                    fromFlow = loanType,
                                    fromScreen = fromScreen
//                            fromScreen = "Loan Status"
                                )
                            }
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
                                                if (title.lowercase(Locale.ROOT) == "principal" ||
                                                    title.lowercase(Locale.ROOT) == "principal_amount"
                                                ) {
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
}


@Preview
@Composable
private fun LoanStatusPreview() {
    LoanStatusScreen(rememberNavController(),"")
}
