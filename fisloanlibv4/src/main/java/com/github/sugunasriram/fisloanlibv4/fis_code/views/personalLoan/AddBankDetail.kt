package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DropDownField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.InputField
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetail
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankDetailResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AccountDetailViewModel
import java.util.Locale

@SuppressLint("ResourceType")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddBankDetailScreen(navController: NavHostController, id: String, fromFlow: String, fromScreen:String) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val accountDetailViewModel: AccountDetailViewModel = viewModel()
    val accountHolder: String by accountDetailViewModel.accountHolder.observeAsState("")
    val accountNumber: String by accountDetailViewModel.accountNumber.observeAsState("")
    val ifscCode: String by accountDetailViewModel.ifscCode.observeAsState("")

    val bankDetailCollecting by accountDetailViewModel.bankDetailCollecting.collectAsState()
    val bankDetailCollected by accountDetailViewModel.bankDetailCollected.collectAsState()
    val bankDetailResponse by accountDetailViewModel.bankDetailResponse.collectAsState()
    val gstBankDetailResponse by accountDetailViewModel.gstBankDetailResponse.collectAsState()

    val accountHolderError: String? by accountDetailViewModel.accountHolderError.observeAsState("")
    val accountNumberError: String? by accountDetailViewModel.accountNumberError.observeAsState("")
    val accountTypeError: String? by accountDetailViewModel.accountTypeError.observeAsState("")
    val ifscCodeError: String? by accountDetailViewModel.ifscCodeError.observeAsState("")
    val showInternetScreen by accountDetailViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by accountDetailViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by accountDetailViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by accountDetailViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by accountDetailViewModel.unAuthorizedUser.observeAsState(false)
    val shouldShowKeyboard by accountDetailViewModel.shouldShowKeyboard.observeAsState(false)
    val middleLoan by accountDetailViewModel.middleLoan.observeAsState(false)
    val errorMessage by accountDetailViewModel.errorMessage.collectAsState()
    val navigationToSignIn by accountDetailViewModel.navigationToSignIn.collectAsState()

    LaunchedEffect(shouldShowKeyboard) {
        if (shouldShowKeyboard) {
            keyboardController?.show()
            accountDetailViewModel.resetKeyboardRequest()
        }
    }

    val focusAccountHolder = remember { FocusRequester() }
    val focusAccountNumber = remember { FocusRequester() }
    val focusIfscCode = remember { FocusRequester() }
    val focusAccountType = remember { FocusRequester() }

    var backPressedTime by remember { mutableLongStateOf(0L) }
    BackHandler { navigateApplyByCategoryScreen(navController) }

    when {
        navigationToSignIn -> navigateSignInPage (navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            AddBankDetailView(
                bankDetailCollecting = bankDetailCollecting, fromFlow = fromFlow,
                bankDetailCollected = bankDetailCollected, context = context,
                bankDetailResponse = bankDetailResponse, ifscCodeError = ifscCodeError,
                gstBankDetailResponse = gstBankDetailResponse, navController = navController,
                accountHolder = accountHolder, accountNumber = accountNumber, ifscCode = ifscCode,
                accountHolderError = accountHolderError, accountNumberError = accountNumberError,
                accountTypeError = accountTypeError,
                id = id, accountDetailViewModel = accountDetailViewModel,
                focusAccountHolder = focusAccountHolder, focusAccountNumber = focusAccountNumber,
                focusAccountType = focusAccountType, focusIfscCode = focusIfscCode, fromScreen = fromScreen
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun AddBankDetailView(
    bankDetailCollecting: Boolean, bankDetailCollected: Boolean, fromFlow: String,
    bankDetailResponse: BankDetailResponse?, gstBankDetailResponse: GstOfferConfirmResponse?,
    navController: NavHostController, context: Context, accountHolder: String, id: String,
    accountNumber: String, ifscCode: String, accountHolderError: String?,
    accountTypeError: String?,
    accountNumberError: String?, accountDetailViewModel: AccountDetailViewModel,
    focusAccountHolder: FocusRequester, focusAccountNumber: FocusRequester,
    focusAccountType: FocusRequester, focusIfscCode: FocusRequester, ifscCodeError: String?,
    fromScreen:String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var accountTypeExpand by remember { mutableStateOf(false) }
    val accountTypeList = listOf("Current", "Saving")
    var accountSelectedText by remember { mutableStateOf("") }
    val onAccountDismiss: () -> Unit = { accountTypeExpand = false }
    val onAccountSelected: (String) -> Unit = { selectedText -> accountSelectedText = selectedText
    accountDetailViewModel.onAccountTypeChanged(selectedText.lowercase())}

    if (bankDetailCollecting) {
        ProcessingAnimation(
            text = stringResource(id = R.string.submitting_bank_details),
            image = R.raw.submitting_bank_details
        )
    } else {
        if (bankDetailCollected) {
            onBankDetailCollected(
                navController = navController, gstBankDetailResponse = gstBankDetailResponse,
                bankDetailResponse = bankDetailResponse, fromFlow = fromFlow
            )
        } else {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.enter_account_details),
                showBackButton = true,
                onBackClick = {
                    if(fromScreen == "Add New Bank") navController.popBackStack()
                    else navigateApplyByCategoryScreen(navController)
//
//                    navigateToBankDetailsScreen(
//                        navController = navController, id = id, fromFlow = fromFlow,
//                    )
                              },
                showBottom = true,
                showSingleButton = true,
                primaryButtonText = stringResource(R.string.submit),
                onPrimaryButtonClick = {
                    onBankSubmit(
                        accountDetailViewModel = accountDetailViewModel,
                        accountHolder = accountHolder, accountSelectedText = accountSelectedText,
                        accountNumber = accountNumber, context = context, ifscCode = ifscCode,
                        focusIfscCode = focusIfscCode, focusAccountType = focusAccountType,
                        focusAccountNumber = focusAccountNumber, fromFlow = fromFlow,
                        focusAccountHolder = focusAccountHolder, id = id,
                        navController = navController,
                    )
                },
                backgroundColor = appWhite
            ) {
                RegisterText(
                    text = stringResource(id = R.string.enter_account_number_for_which),
                    style = normal12Text400, textColor = hintGray,
                    top = 8.dp, start = 10.dp, end = 10.dp
                )
                LoanStatusTracker(stepId = 5)
                AddBankField(
                    label = stringResource(id = R.string.account_holder_name),
                    value = accountHolder,
                    onValueChange = {
                        accountDetailViewModel.onAccountHolderChanged(it)
                        accountDetailViewModel.updateAccountHolderError(null)
                    },
                    error = accountHolderError,
                    focusRequester = focusAccountHolder,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
                )

                if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
                    AddBankFieldHeader(
                        label = stringResource(id = R.string.account_type),
                        headerImage =painterResource(id = R.drawable.account_type_icon) ,
                        )
                    DropDownField(
                        hint = stringResource(id = R.string.account_type),
                        expand = accountTypeExpand,
                        top = 0.dp,
                        start = 3.dp,
                        end = 3.dp,
                        bottom = 0.dp,
                        selectedText = accountSelectedText, focus = focusAccountType,
                        onNextFocus = focusAccountNumber, setExpand = { accountTypeExpand = it },
                        itemList = accountTypeList, onDismiss = onAccountDismiss,
                        modifier = Modifier.focusRequester(focusAccountType),
                        onItemSelected = {it ->
                            onAccountSelected(it)
                            accountDetailViewModel.updateAccountTypeError(null)

                        }
                    )
                    if (!accountTypeError.isNullOrEmpty()) {
                        StartingText(
                            text = accountTypeError,style = normal12Text400,
                            textColor = errorRed,
                            modifier = Modifier.padding(start = 15.dp, top = 2.dp, bottom = 5.dp),

                        )
                    }
                }
                AddBankField(
                    label = stringResource(id = R.string.bank_account_number),
                    headerImge =painterResource(id = R.drawable.account_number_icon) ,
                    value = accountNumber,
                    onValueChange = { input ->
                        val filteredInput = input.filter { it.isDigit() } // Only allow digits
                        accountDetailViewModel.onAccountNumberChanged(filteredInput)
                        accountDetailViewModel.updateAccountNumberError(null)
                        if (input.length == 18) {
                            keyboardController?.hide()
                        }
                    },
                    error = accountNumberError,
                    focusRequester = focusAccountNumber,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                AddBankField(
                    label = stringResource(id = R.string.bank_ifsc_code),
                    headerImge =painterResource(id = R.drawable.bank_ifsc_icon) ,
                    value = ifscCode,
                    onValueChange = {
                        accountDetailViewModel.onIfscCodeChanged(it)
                        accountDetailViewModel.updateIfscCodeError(null)
                        if (it.length == 11) {
                            keyboardController?.hide()
                        }
                    },
                    error = ifscCodeError,
                    focusRequester = focusIfscCode,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    )
                )
            }
        }
    }
}
@Composable
fun AddBankFieldHeader(
    label: String,
    headerImage:Painter= painterResource(id = R.drawable.account_holder_icon),
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp,top=25.dp)
    ) {
        Image(
            painter =headerImage,
            contentDescription = null,
            modifier = Modifier.padding(end = 5.dp).size(22.dp)
        )
        Text(
            text =label,
            style = normal16Text500, color = appOrange
        )
    }

}

@Composable
fun AddBankField(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    error: String?,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    headerImge:Painter= painterResource(id = R.drawable.account_holder_icon),
) {
    AddBankFieldHeader(label,headerImge)
    InputField(
        inputText = value,
        topText = label,
        top = 0.dp,
        start = 0.dp,
        end = 0.dp,
        bottom = 0.dp,
        error = error,
        showOnlyTextField = true,
        modifier = Modifier.focusRequester(focusRequester),
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
    )
}

fun onBankDetailCollected(
    bankDetailResponse: BankDetailResponse?, fromFlow: String, navController: NavHostController,
    gstBankDetailResponse: GstOfferConfirmResponse?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        bankDetailResponse?.let { response ->
            response.data?.eNACHUrlObject?.txnId?.let { transactionId ->
                response.data?.eNACHUrlObject?.formUrl?.let { enachUrl ->
                    response.data.id?.let { id ->
                        navigateToRepaymentScreen(navController, transactionId,  enachUrl, id,
                            fromFlow)
//                        navigateToLoanProcessScreen(
//                            navController, transactionId=transactionId,5, enachUrl, id, fromFlow = fromFlow
//                        )
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
                        navController, transactionId = transactionId,15, eNachUrl, offerId,
                        fromFlow = fromFlow
                    )
                }
            }
            }
        }
    }
}

fun onBankSubmit(
    fromFlow: String, navController: NavHostController, accountSelectedText: String,
    accountDetailViewModel: AccountDetailViewModel, accountHolder: String, accountNumber: String,
    focusIfscCode: FocusRequester, focusAccountType: FocusRequester, context: Context,
    ifscCode: String, focusAccountNumber: FocusRequester, id: String,
    focusAccountHolder: FocusRequester
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        val accountType = accountSelectedText.lowercase(Locale.ROOT)
        accountDetailViewModel.bankAccountDetailValidation(
            context = context, accountNumber = accountNumber, accountHolder = accountHolder,
            ifscCode = ifscCode,
            accountSelectedText = accountSelectedText,
            bankDetail = BankDetail(
                accountNumber = accountNumber, accountHolderName = accountHolder,
                ifscCode = ifscCode, accountType = accountType, id = id,
                loanType = "PERSONAL_LOAN"
            ),
            navController
        )
    } else {
        accountDetailViewModel.accountDetailValidation(
            context = context, accountNumber = accountNumber, accountHolder = accountHolder,
            focusAccountNumber = focusAccountNumber, focusIfscCode = focusIfscCode, id = id,
            ifscCode = ifscCode, focusAccountHolder = focusAccountHolder
        )
    }
}

@Preview
@Composable
fun AccountDetailsScreenPreview() {
    AddBankDetailScreen(
        navController = NavHostController(LocalContext.current), id = "1",
        fromFlow = "Personal Loan",""
    )
}
