package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableDropDownField
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.UpdateBankDetail
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.AccountDetailViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ResourceType")
@Composable
fun EditBankDetailScreen(navController: NavHostController, id: String, fromFlow: String,
                         accountId: String,
                         bankAccountHolderName:String,
                         bankAccountType:String,
                         bankIfsc:String,
                         bankAccountNumber:String
                         ) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val accountDetailViewModel: AccountDetailViewModel = viewModel()
    val accountHolder: String by accountDetailViewModel.accountHolder.observeAsState("")
    val accountNumber: String by accountDetailViewModel.accountNumber.observeAsState("")
    val ifscCode: String by accountDetailViewModel.ifscCode.observeAsState("")

    val bankDetailUpdating by accountDetailViewModel.bankDetailUpdating.collectAsState()
    val bankDetailUpdated by accountDetailViewModel.bankDetailUpdated.collectAsState()
    val bankDetailUpdateResponse by accountDetailViewModel.bankDetailUpdateResponse.collectAsState()

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
    LaunchedEffect (Unit){
        accountDetailViewModel.setAccountHolder(bankAccountHolderName)
        accountDetailViewModel.setAccountType(bankAccountType)
        accountDetailViewModel.setAccountNumber(bankAccountNumber)
        accountDetailViewModel.setIFSCCode(bankIfsc)
    }

    val focusAccountHolder = remember { FocusRequester() }
    val focusAccountNumber = remember { FocusRequester() }
    val focusIfscCode = remember { FocusRequester() }
    val focusAccountType = remember { FocusRequester() }

    var accountTypeExpand by remember { mutableStateOf(false) }
    val accountTypeList = listOf("Current", "Saving")
    var accountSelectedText by remember { mutableStateOf(bankAccountType.replaceFirstChar { it.uppercaseChar() }) }
    val onAccountDismiss: () -> Unit = { accountTypeExpand = false }
    val onAccountSelected: (String) -> Unit = { selectedText -> accountSelectedText = selectedText
        accountDetailViewModel.onAccountTypeChanged(selectedText.lowercase())}
    val bringFocusRequester = remember { BringIntoViewRequester() }
    var backPressedTime by remember { mutableLongStateOf(0L) }
    BackHandler { navigateApplyByCategoryScreen(navController) }

    when {
        navigationToSignIn -> navigateSignInPage (navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        middleLoan ->  MiddleOfTheLoanScreen(navController,errorMessage,)
        else -> {
            if (bankDetailUpdating) {
                ProcessingAnimation(
                    text = stringResource(id = R.string.submitting_bank_details),
                    image = R.raw.submitting_bank_details
                )
            } else {
                if (bankDetailUpdated) {
                    navigateToBankDetailsScreen(
                        navController = navController, id = id, fromFlow = fromFlow,
                    )
                } else {
                    FixedTopBottomScreen(
                        navController = navController,
                        topBarBackgroundColor = appOrange,
                        topBarText = stringResource(R.string.edit_account_details),
                        showBackButton = true,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        showBottom = true,
                        showSingleButton = true,
                        primaryButtonText = stringResource(R.string.submit),
                        onPrimaryButtonClick = {
                            accountDetailViewModel.editAccountDetailValidation(
                                context = context,
                                accountNumber = accountNumber,
                                accountHolder = accountHolder,
                                ifscCode = ifscCode,
                                accountSelectedText = accountSelectedText,
                                focusAccountHolder = focusAccountHolder,
                                focusAccountNumber = focusAccountNumber,
                                focusIfscCode = focusIfscCode,
                                focusAccountType = focusAccountType,
                                bankDetail = UpdateBankDetail(
                                    accountNumber = accountNumber,
                                    accountHolderName = accountHolder,
                                    ifscCode = ifscCode,
                                    accountType = accountSelectedText.lowercase(Locale.ROOT),
                                    id = accountId,
                                ),
                                navController = navController
                            )
                        },
                        backgroundColor = appWhite
                    ) {
                        LoanStatusTracker(stepId = 5, bankItem = "Edit Bank")
                        AddBankField(
                            label = stringResource(id = R.string.account_holder_name),
                            value = accountHolder,
                            onValueChange = {
                                accountDetailViewModel.onAccountHolderChanged(it)
                                accountDetailViewModel.updateAccountHolderError(null)
                            },
                            error = accountHolderError,
                            focusRequester = focusAccountHolder,
                            keyboardActions = KeyboardActions(onNext = {
                                keyboardController?.hide()
                                focusAccountType.requestFocus()
                            }),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Text
                            ),
                            regexPattern = "[^a-zA-Z ]",
                            inputLimit = 35
                        )

                        if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
                            AddBankFieldHeader(
                                label = stringResource(id = R.string.account_type),
                                headerImage = painterResource(id = R.drawable.account_type_icon) ,
                            )
                            ClickableDropDownField(
                                expand = accountTypeExpand,
                                top = 2.dp,
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 2.dp,
                                error = accountTypeError,
                                errorTextStart=15.dp,
                                selectedText = accountSelectedText, focus = focusAccountType,
                                onNextFocus = focusAccountNumber, setExpand = { accountTypeExpand = it },
                                itemList = accountTypeList, onDismiss = onAccountDismiss,
                                modifier = Modifier.focusRequester(focusAccountType)
                                    .bringIntoViewRequester(bringFocusRequester),
                                onItemSelected = {it ->
                                    onAccountSelected(it)
                                    accountDetailViewModel.updateAccountTypeError(null)
                                    focusAccountNumber.requestFocus()
                                }
                            )

                        }
                        AddBankField(
                            label = stringResource(id = R.string.bank_account_number),
                            headerImge = painterResource(id = R.drawable.account_number_icon) ,
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
                            keyboardActions = KeyboardActions(onNext = { focusIfscCode.requestFocus() }),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            regexPattern = "[^0-9]",
                            inputLimit = 18
                        )

                        AddBankField(
                            label = stringResource(id = R.string.bank_ifsc_code),
                            headerImge = painterResource(id = R.drawable.bank_ifsc_icon) ,
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
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Characters
                            ), regexPattern = "[^a-zA-Z0-9]",
                            inputLimit = 11
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EditAccountDetailsScreenPreview() {
    EditBankDetailScreen(
        navController = NavHostController(LocalContext.current), id = "1",
        fromFlow = "Personal Loan","","","","",""
    )
}

