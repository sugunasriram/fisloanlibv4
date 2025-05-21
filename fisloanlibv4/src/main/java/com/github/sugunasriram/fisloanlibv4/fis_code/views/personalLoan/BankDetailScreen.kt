package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.BorderCardWithElevation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HyperText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OnlyCheckBox
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToAccountDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankAccount
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.DataItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.GstBankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.PfBankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.pf.PfOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AccountDetailViewModel

@SuppressLint("ResourceType")
@Composable
fun BankDetailScreen(navController: NavHostController, id: String, fromFlow: String) {
    val context = LocalContext.current
    val accountDetailViewModel: AccountDetailViewModel = viewModel()

    val gettingBank by accountDetailViewModel.gettingBank.collectAsState()
    val gotBank by accountDetailViewModel.gotBank.collectAsState()
    val bankAccount by accountDetailViewModel.bankAccount.collectAsState()
    val bankDetailCollecting by accountDetailViewModel.bankDetailCollecting.collectAsState()
    val bankDetailCollected by accountDetailViewModel.bankDetailCollected.collectAsState()
    val bankDetailResponse by accountDetailViewModel.bankDetailResponse.collectAsState()
    val gstBankDetailResponse by accountDetailViewModel.gstBankDetailResponse.collectAsState()
    val pfBankDetailResponse by accountDetailViewModel.pfBankDetailResponse.collectAsState()
    val showInternetScreen by accountDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by accountDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by accountDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by accountDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by accountDetailViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by accountDetailViewModel.middleLoan.observeAsState(false)
    val errorMessage by accountDetailViewModel.errorMessage.collectAsState()

    val navigationToSignIn by accountDetailViewModel.navigationToSignIn.collectAsState()

    val (selectedBankDetail, setSelectedBankDetail) = remember { mutableStateOf<DataItem?>(null) }
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {navigateApplyByCategoryScreen(navController) }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            if (gettingBank) {
                ProcessingAnimation(text = "", image = R.raw.we_are_currently_processing_hour_glass)
            } else {
                if (gotBank) {
                    if (bankAccount?.data?.isEmpty() != true) {
                        if (bankDetailCollecting) {
                            ProcessingAnimation(text = "",  image = R.raw.we_are_currently_processing_hour_glass)
                        }
                        if (bankDetailCollected) {
                            onBankDetailsCollected(
                                fromFlow = fromFlow, navController = navController,
                                gstBankDetailResponse = gstBankDetailResponse,
                                pfBankDetailResponse = pfBankDetailResponse,
                                bankDetailResponse = bankDetailResponse
                            )
                        }
                        if (!bankDetailCollecting) {
                            BankDetailCollecting(
                                navController = navController,
                                selectedBankDetail = selectedBankDetail,
                                accountDetailViewModel = accountDetailViewModel,
                                fromFlow = fromFlow, context = context, id = id,
                                bankAccount = bankAccount,
                                setSelectedBankDetail = setSelectedBankDetail
                            )
                        }
                    } else {
                        navigateToAccountDetailsScreen(navController, id, fromFlow,"Bank Details")
                    }
                } else {
                    accountDetailViewModel.getBankAccount(context)
                }
            }
        }
    }
}

    @Composable
    fun BankDetailCollecting(
        navController: NavHostController, selectedBankDetail: DataItem?,
        accountDetailViewModel: AccountDetailViewModel, fromFlow: String, context: Context,
        id: String, bankAccount: BankAccount?, setSelectedBankDetail: (DataItem?) -> Unit
    ) {
        var backPressedTime by remember { mutableLongStateOf(0L) }
        FixedTopBottomScreen(
            navController = navController,
            topBarBackgroundColor = appOrange,
            topBarText = stringResource(R.string.disbursement_account_details),
            showBackButton = true,
            onBackClick = {navigateApplyByCategoryScreen(navController) },
            showBottom = true,
            showSingleButton = true,
            primaryButtonText = stringResource(R.string.submit),
            onPrimaryButtonClick = {
                onBankDetailsSubmit(
                    selectedBankDetail = selectedBankDetail,
                    accountDetailViewModel = accountDetailViewModel,
                    fromFlow = fromFlow, context = context,
                    navController = navController, id = id
                )
            },
            backgroundColor = appWhite
        ) {
            RegisterText(
                text = stringResource(id = R.string.adding_your_bank_account),
                style = normal12Text400, textColor = hintGray,
                top = 8.dp, start = 10.dp, end = 10.dp
            )
            LoanStatusTracker(stepId = 5)
            AccountDetailsHeader(context)
            bankAccount?.data?.forEach { bankDetails ->
                val isChecked = selectedBankDetail == bankDetails

                BorderCardWithElevation (
                    bottom = 20.dp){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp)
//                            .clickable {
//                                onBankCardClicked(
//                                    bankDetails = bankDetails,
//                                    onCheckedChange = { selectedDetail ->
//                                        setSelectedBankDetail(selectedDetail)
//                                    },
//                                    fromFlow = fromFlow,
//                                    context = context,
//                                    accountDetailViewModel = accountDetailViewModel,
//                                    navController = navController,
//                                    id = id
//                                )
//                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bank),
                            contentDescription = stringResource(id = R.string.bank_image),
                            modifier = Modifier.size(28.dp)
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.weight(1f)
                        ) {
                            bankDetails?.accountHolderName?.let { holderName ->
                                StartingText(
                                    text = holderName,
                                    bottom = 5.dp,
                                    top = 15.dp,
                                    start = 30.dp,
                                    end = 20.dp,
                                    style = normal20Text700,
                                    textColor = appBlack,
                                    textAlign = TextAlign.Start,
                                )
                            }
                            bankDetails?.bankAccountNumber?.let { accountNumber ->
                                StartingText(
                                    text = accountNumber,
                                    bottom = 5.dp,
                                    start = 30.dp,
                                    end = 20.dp,
                                    style = normal16Text400,
                                    textColor = hintGray,
                                    textAlign = TextAlign.Start,
                                )
                            }
                            bankDetails?.bankIfscCode?.let { ifscCode ->
                                StartingText(
                                    text = ifscCode,
                                    bottom = 15.dp,
                                    start = 30.dp,
                                    end = 20.dp,
                                    style = normal16Text400,
                                    textColor = hintGray,
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }

                        OnlyCheckBox(
                            start = 20.dp,
                            boxState = isChecked,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    setSelectedBankDetail(bankDetails)
                                } else if (selectedBankDetail == bankDetails) {
                                    setSelectedBankDetail(null)
                                }
                            }
                        )
                    }
                }
            }
            HyperText(
                text = stringResource(id = R.string.add_account_details_plus),
                alignment = Alignment.TopCenter, boxTop = 15.dp,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                navigateToAccountDetailsScreen(navController, id, fromFlow,"Add New Bank")
            }
        }
    }

@Composable
fun AccountDetailsHeader(context: Context){
    Row(modifier = Modifier.padding(horizontal = 30.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        StartingText(
            text = stringResource(id = R.string.account_details),
            start =0.dp, end = 0.dp, bottom = 0.dp, top = 0.dp,
            style = normal16Text700, modifier = Modifier.weight(0.8f)
        )
//        Image(
//            painter = painterResource(R.drawable.edit),
//            contentDescription = stringResource(R.string.edit_icon),
//            modifier = Modifier.size(20.dp).weight(0.1f).clickable {
//                CommonMethods().toastMessage(context = context, toastMsg = "Yet to update — backend update pending.")
//            }
//        )
//        Image(
//            painter = painterResource(R.drawable.delete_icon),
//            contentDescription = stringResource(R.string.delete),
//            modifier = Modifier.size(20.dp).weight(0.1f).clickable {
//                CommonMethods().toastMessage(context = context, toastMsg = "Yet to update — backend update pending.")
//            }
//        )
    }

}
fun onBankDetailsCollected(
    fromFlow: String, navController: NavHostController,
    gstBankDetailResponse: GstOfferConfirmResponse?,
    pfBankDetailResponse: PfOfferConfirmResponse?,
    bankDetailResponse: BankDetailResponse?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        bankDetailResponse?.let { response ->
            response.data?.eNACHUrlObject?.txnId?.let { transactionId ->
                response.data?.eNACHUrlObject?.formUrl?.let { url ->
                    Log.d("res_H_Bank",url)
                    response.data.id?.let { id ->
                        navigateToRepaymentScreen(navController, transactionId, url, id,
                            fromFlow)
//                        navigateToLoanProcessScreen(
//                            navController = navController, transactionId = transactionId,
//                            statusId = 5,
//                            responseItem = url, offerId = id, fromFlow = fromFlow
//                        )
                    }
                }
            }
        }
    } else if(fromFlow.equals("Purchase Finance", ignoreCase = true)){
        pfBankDetailResponse?.let { response ->
            response.data?.eNACHUrlObject?.txnID?.let { transactionId ->
                response.data?.eNACHUrlObject?.fromURL?.let { eNachUrl ->
                    response.data.eNACHUrlObject.itemID?.let { offerId ->
                        navigateToLoanProcessScreen(
                            navController = navController,
                            statusId = 15, transactionId=transactionId,
                            responseItem = eNachUrl, offerId = offerId, fromFlow = fromFlow
                        )
                    }
                }
            }
        }
    } else {
        gstBankDetailResponse?.let { response ->
            response.data?.eNACHUrlObject?.txnID?.let { transactionId ->

                response.data?.eNACHUrlObject?.fromURL?.let { eNachUrl ->
                    response.data.eNACHUrlObject.itemID?.let { offerId ->
                        navigateToLoanProcessScreen(
                            navController = navController,
                            statusId = 15, transactionId=transactionId,
                            responseItem = eNachUrl, offerId = offerId, fromFlow = fromFlow
                        )
                    }
                }
            }
        }
    }
}

fun onBankDetailsSubmit(
    selectedBankDetail: DataItem?, accountDetailViewModel: AccountDetailViewModel,
    fromFlow: String, context: Context, navController: NavHostController, id: String
) {
    selectedBankDetail?.let { selectedDetail ->
        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            accountDetailViewModel.addBankDetail(
                context,
                BankDetail(
                    accountNumber = selectedDetail.bankAccountNumber.toString(),
                    accountHolderName = selectedDetail.accountHolderName.toString(),
                    accountType = if (selectedDetail.accountType.equals(
                            "Saving",
                            ignoreCase = true
                        )
                    )
                        "saving" else "saving",
                    id = id,
                    ifscCode = selectedDetail.bankIfscCode.toString(),
                    loanType = "PERSONAL_LOAN"
                ),
                navController
            )
        } else if(fromFlow.equals("Purchase Finance", ignoreCase = true)){
            accountDetailViewModel.pfLoanEntityApproval(
                bankDetail = PfBankDetail(
                    accountNumber = selectedDetail.bankAccountNumber.toString(),
                    ifscCode = selectedDetail.bankIfscCode.toString(),
                    accountHolderName = selectedDetail.accountHolderName.toString(),
                    id = id,
                    loanType = "PURCHASE_FINANCE"
                ),
                context = context
            )
        } else {
            accountDetailViewModel.gstLoanEntityApproval(
                bankDetail = GstBankDetail(
                    accountNumber = selectedDetail.bankAccountNumber.toString(),
                    ifscCode = selectedDetail.bankIfscCode.toString(),
                    accountHolderName = selectedDetail.accountHolderName.toString(),
                    id = id,
                    loanType = "INVOICE_BASED_LOAN"
                ),
                context = context
            )
        }
    } ?: CommonMethods().toastMessage(context = context, toastMsg = "No bank detail selected")
}

fun onBankCardClicked(
    bankDetails: DataItem?,
    onCheckedChange: (DataItem?) -> Unit,
    fromFlow: String,
    accountDetailViewModel: AccountDetailViewModel,
    context: Context,
    navController: NavHostController,
    id: String
) {
    onCheckedChange(bankDetails)
    bankDetails?.accountHolderName?.let { accountHolderName ->
        bankDetails.bankIfscCode?.let { bankIfscCode ->
            bankDetails.bankAccountNumber?.let { bankAccountNumber ->
                bankDetails.accountType?.let { accountType ->
                    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
                        val accountType = if (accountType.equals("Saving", ignoreCase = true))
                            "saving" else "current"
                        accountDetailViewModel.addBankDetail(
                            context = context,
                            bankDetail = BankDetail(
                                accountNumber = bankAccountNumber,
                                accountHolderName = accountHolderName,
                                accountType = accountType,
                                id = id,
                                ifscCode = bankIfscCode,
                                loanType = "PERSONAL_LOAN"
                            ),
                            navController = navController
                        )
                    } else {
                        accountDetailViewModel.gstLoanEntityApproval(
                            bankDetail = GstBankDetail(
                                accountNumber = bankAccountNumber,
                                ifscCode = bankIfscCode,
                                accountHolderName = accountHolderName,
                                id = id,
                                loanType = "INVOICE_BASED_LOAN"
                            ),
                            context = context
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BankDetailCollectingPreview() {
    val mockNavController = rememberNavController()
    val mockContext = LocalContext.current
    val mockViewModel = AccountDetailViewModel()
    val sampleBankAccount = BankAccount(
        data = listOf(
            DataItem(
                accountHolderName = "John Doe",
                bankAccountNumber = "123456789012",
                bankIfscCode = "ABCD0123456",
                accountType = "Savings"
            ),
            DataItem(
                accountHolderName = "Jane Smith",
                bankAccountNumber = "987654321098",
                bankIfscCode = "XYZD0987654",
                accountType = "Current"
            ),

            DataItem(
                accountHolderName = "Jane Smith",
                bankAccountNumber = "987654321098",
                bankIfscCode = "XYZD0987654",
                accountType = "Current"
            ),
            DataItem(
                accountHolderName = "Jane Smith",
                bankAccountNumber = "987654321098",
                bankIfscCode = "XYZD0987654",
                accountType = "Current"
            ),
            DataItem(
                accountHolderName = "Jane Smith",
                bankAccountNumber = "987654321098",
                bankIfscCode = "XYZD0987654",
                accountType = "Current"
            ),

        ),
        status = true,
        statusCode = 200
    )

    var selectedBankDetail by remember { mutableStateOf<DataItem?>(null) }

    BankDetailCollecting(
        navController = mockNavController,
        selectedBankDetail = selectedBankDetail,
        accountDetailViewModel = mockViewModel,
        fromFlow = "Personal Loan",
        context = mockContext,
        id = "12345",
        bankAccount = sampleBankAccount,
        setSelectedBankDetail = { selectedBankDetail = it }
    )
}

