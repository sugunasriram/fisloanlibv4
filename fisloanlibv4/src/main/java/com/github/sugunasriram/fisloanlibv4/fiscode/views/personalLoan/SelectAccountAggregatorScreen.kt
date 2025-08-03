package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ImageTextWithRadioButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.BankItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.GetLenderStatusModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightishGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val bankList = arrayListOf<BankItem?>(
    BankItem(
        bankName = "FinVu",
        imageBank = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBaGPXz45LQV0sjCCqrERZUTWGDT7c3j9adcVGIhTfzw&s",
        id = "15678"
    )
)

@SuppressLint("SuspiciousIndentation")
@Composable
fun SelectAccountAggregatorScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    id: String,
    transactionId: String,
    url: String
) {
    val webViewModel: WebViewModel = viewModel()
    val webInProgress by webViewModel.webInProgress.collectAsState()
    val showInternetScreen by webViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by webViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by webViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by webViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by webViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val errorMessage by webViewModel.errorMessage.collectAsState()
    val navigationToSignIn by webViewModel.navigationToSignIn.collectAsState()
    BackHandler {
    navController.popBackStack()
//        navigateToAccountAggregatorScreen(navController, loanPurpose, fromFlow, id = id, transactionId = transactionId, url = url)
    }
    val context = LocalContext.current
    val (selectedOption, setSelectedOption) = remember { mutableStateOf<String?>(if (bankList.size == 1) bankList[0]?.bankName else null) }
    var showError by remember { mutableStateOf(false) }
    var showNoLoanOffersScreen by remember { mutableStateOf(false) }

    if (showNoLoanOffersScreen) {
        NoLoanOffersAvailableScreen(navController, titleText = stringResource(R.string.no_lenders_available))
        return
    }
    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
        else -> {
            if (webInProgress) {
                CenterProgress()
            } else {
                FixedTopBottomScreen(
                    navController = navController,
                    topBarBackgroundColor = appOrange,
                    topBarText = stringResource(R.string.select_your_account_aggregator),
                    showBackButton = true,
                    onBackClick = {
                        navController.popBackStack()
//                        navigateToAccountAggregatorScreen(
//                            navController = navController,
//                            loanPurpose = loanPurpose,
//                            fromFlow = fromFlow,
//                            id = id,
//                            transactionId = transactionId,
//                            url = url
//                        )
                    },
                    showBottom = true,
                    showErrorMsg = showError,
                    errorMsg = stringResource(R.string.please_select_account_aggregator),
                    showSingleButton = true,
                    primaryButtonText = stringResource(R.string.next),
                    onPrimaryButtonClick = {
                        if (selectedOption != null) {
                            CoroutineScope(Dispatchers.Main).launch {
                                webViewModel.setWebInProgress(true)

                                if(fromFlow == "Personal Loan"){
                                    webViewModel.getLenderStatusApi(
                                        context = context,
                                        loanType = "PERSONAL_LOAN",
                                        step = "CONSENT_SELECT"
                                    )
                                }else if(fromFlow == "Purchase Finance"){
                                    webViewModel.getLenderStatusApi(
                                        context = context,
                                        loanType = "PURCHASE_FINANCE",
                                        step = "CONSENT_SELECT"
                                    )
                                }

                                val lenderStatusModel = webViewModel.getLenderStatusResponse
                                    .filterNotNull().first()
                                val lenderStatus = lenderStatusModel.data?.response
                                Log.d("LenderStatus", "Received lenders: $lenderStatus")
                                if (lenderStatus.isNullOrEmpty()) {
                                    webViewModel.setWebInProgress(false)
                                    showNoLoanOffersScreen = true
                                    return@launch
                                }
                                val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

                                val lenderStatusJson = try {
                                    json.encodeToString(
                                        LenderStatusResponse.serializer(),
                                        LenderStatusResponse(response = lenderStatus)
                                    )
                                } catch (e: Exception) {
                                    Log.e("JsonParseError", "Failed to serialize lender status", e)
                                    webViewModel.setWebInProgress(false)
                                    showNoLoanOffersScreen = true
                                    return@launch
                                }

                                webViewModel.setWebInProgress(false)
                                navigateToWebViewFlowOneScreen(
                                    navController,
                                    loanPurpose,
                                    fromFlow,
                                    id = id,
                                    transactionId = transactionId,
                                    url = url,
                                    lenderStatusData = lenderStatusJson
                                )
                            }
                            showError = false
                        } else {
                            showError = true
                        }
                    },
                    backgroundColor = appWhite
                ) {
                    StartingText(
                        text = stringResource(id = R.string.choose_rbi_approved_account),
                        textColor = hintGray,
                        start = 15.dp,
                        end = 15.dp,
                        top = 10.dp,
                        bottom = 10.dp,
                        style = normal14Text400,
                        textAlign = TextAlign.Center,
                        alignment = Alignment.TopCenter
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
        }
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
