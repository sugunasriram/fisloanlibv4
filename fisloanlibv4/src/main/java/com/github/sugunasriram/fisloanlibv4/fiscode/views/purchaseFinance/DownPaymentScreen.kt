package com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.DownPaymentCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBureauOffersScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFormSubmissionWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.Profile
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.auth.VerifySessionResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.PFSearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.auth.RegisterViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.PersonalLoanViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.purchaseFinance.PurchaseFinanceViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.CompanyConsentContent
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.ConsentDialogBox
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.EditLoanTenureSliderUI
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.HtmlText
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.applyMaxLimit
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.calculateFormattedCursorPosition
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.formatCurrency
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.loadWebScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan.processRawInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.math.floor
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager


var productName : String = "Samsung Galaxy S24 Ultra 5G"
var productPrice : Long = 99000L
var mrpPrice : Long = 110000L
var maxAmount = 99000L
var amount = maxAmount / 3

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
//fun DownPaymentScreen(navController: NavHostController, fromFlow: String) {
fun DownPaymentScreen(navController: NavHostController,
                      verifySessionResponse: VerifySessionResponse
){
val registerViewModel: RegisterViewModel = viewModel()
    val webViewModel: WebViewModel = viewModel()

    val purchaseFinanceViewModel: PurchaseFinanceViewModel = viewModel()
    val searchInProgress by purchaseFinanceViewModel.searchInProgress.collectAsState()
    val searchLoaded by purchaseFinanceViewModel.searchLoaded.collectAsState()

    val checkboxState: Boolean by registerViewModel.checkBoxDetail.observeAsState(false)
    val showInternetScreen by registerViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by registerViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by registerViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by registerViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by registerViewModel.unAuthorizedUser.observeAsState(false)
    val showNoLenderResponse by webViewModel.showNoLenderResponse.collectAsState()
    val lenderStatusProgress by webViewModel.lenderStatusProgress.collectAsState()

    val inProgress by registerViewModel.inProgress.collectAsState()
    val isCompleted by registerViewModel.isCompleted.collectAsState()
    val userDetails by registerViewModel.getUserResponse.collectAsState()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val showDialog : MutableState<Boolean> =  remember {  mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String>("") }
    var showInValidAmountError by remember { mutableStateOf(false) }

//    val productName : String = "Samsung Galaxy S24 Ultra 5G"
//    val productPrice : Long = 99000L
//    val mrpPrice : Long = 110000L
//    val maxAmount = 99000L
//    val amount = remember { androidx.compose.runtime.mutableLongStateOf(maxAmount / 3) }

    val fromFlow = "Purchase Finance"


    LaunchedEffect(Unit) {
        purchaseFinanceViewModel.pFSearch(
            context = context,
            searchBodyModel = PFSearchBodyModel(
                loanType = "PURCHASE_FINANCE",
                bureauConsent = "on"
            )
        )
    }

    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    var showNoLoanOffersScreen by remember { mutableStateOf(false) }

//    if (showNoLoanOffersScreen || showNoLenderResponse) {
//        NoLoanOffersAvailableScreen(navController, titleText = stringResource(R.string.no_lenders_available))
//        return
//    }
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        showNoLenderResponse -> NoLoanOffersAvailableScreen(navController)
//        lenderStatusProgress -> CenterProgress()
        else -> {
            if (searchInProgress || inProgress ) {
                ProcessingAnimation(text = "Processing Please Wait...", image = R.raw.we_are_currently_processing_hour_glass)
            } else {
                if (!isCompleted ) {
                    registerViewModel.getUserDetail(context, navController)
                } else if (searchLoaded){
                    CustomModalBottomSheet(
                        bottomSheetState = bottomSheetState,
                        sheetBackgroundColor = Color.Transparent,
                        sheetContent = {
                            CompanyConsentContent(
                                bottomSheetState = bottomSheetState,
                                coroutineScope = coroutineScope,
                                registerViewModel = registerViewModel,
                                onAcceptConsent = { showError = false },
                                startTimer = bottomSheetState.isVisible,
                                showDialog = showDialog,
                                timer = 5
                            )
                        }
                    ) {
                        FixedTopBottomScreen(
                            navController = navController,
                            backgroundColor = appWhite,
                            contentStart = 0.dp,
                            contentEnd = 0.dp,
                            topBarBackgroundColor = appOrange,
                            topBarText = stringResource(R.string.down_payment_screen),
                            showBackButton = true,
                            onBackClick = { navigateApplyByCategoryScreen(navController) },
                            showBottom = true,
                            showCheckBox = true,
                            checkboxState = checkboxState,
                            onCheckBoxChange = { isChecked ->
                                if (isChecked) {
                                    coroutineScope.launch {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                        bottomSheetState.show()
                                    }
                                } else {
                                    registerViewModel.onCheckBoxDetailChanged(false)
                                    showError = !checkboxState
                                }
//
                            },
                            checkBoxText = stringResource(R.string.i_understand_and_agree_to_buyer_app_terms_and_conditions),
                            showErrorMsg = showError,
                            errorMsg = errorMsg,
                            showSingleButton = true,
                            primaryButtonText = stringResource(R.string.submit),
                            onPrimaryButtonClick = {
                                if (!checkboxState) {
                                    showError = true
                                    errorMsg = context.getString(R.string.please_agree_buyer_App_terms)
                                } else if (showInValidAmountError) {
                                    showError = true
                                    errorMsg = context.getString(R.string.please_enter_valid_downpayment_amount)
                                } else {
                                    showError = false
                                    CoroutineScope(Dispatchers.Main).launch {
                                        webViewModel.setWebInProgress(true)

                                        try {
                                            webViewModel.getLenderStatusApi(
                                                context = context,
                                                loanType = "PURCHASE_FINANCE",
                                                step = "FORM_SUBMISSION_REQUEST"
                                            )

                                            val lenderStatusModel = webViewModel.getLenderStatusResponse
                                                .filterNotNull().first()

                                            val lenderStatus = lenderStatusModel?.data?.response

                                            if (lenderStatus.isNullOrEmpty()) {
                                                showNoLoanOffersScreen = true
                                                return@launch
                                            }

                                            val json = Json {
                                                prettyPrint = true
                                                ignoreUnknownKeys = true
                                            }

                                            val lenderStatusJson = json.encodeToString(
                                                LenderStatusResponse.serializer(),
                                                LenderStatusResponse(response = lenderStatus)
                                            )

                                            navigateToFormSubmissionWebScreen(navController, fromFlow, lenderStatusJson)

                                            TokenManager.save("downpaymentAmount", amount.toString())

                                            loadWebScreen(
                                                fromFlow = fromFlow,
                                                webViewModel = webViewModel,
                                                context = context,
                                                endUse = "PF flow",
                                                purpose = "PF flow",
                                                downPaymentAmount = amount.toString(),
                                                productPrice = productPrice.toString()
                                            )
                                        } catch (e: Exception) {
                                            Log.e("LenderStatusError", "Error processing lender status", e)
                                            showNoLoanOffersScreen = true
                                        } finally {
                                            webViewModel.setWebInProgress(false)
                                        }
                                    }
                                }
                            }

                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(backgroundOrange)
                            ) {
//                                ProductDetailsCard(productName, productPrice, mrpPrice)
                                ProductDetailsCard(verifySessionResponse)
                                userDetails?.data?.let { PFPersonalDetailsCard(profile = it) }
                                DownPaymentDetailsCard(
                                    amount = amount,
                                    maxAmount = maxAmount,
                                    showInValidAmountError = showInValidAmountError,
                                    onAmountChange = { amount = it },
                                    onValidationChanged = { showInValidAmountError = it } // <- sync state here
                                )
//                                PreferredTenureCard()
                            }
                        }
                    }
                }
            }
        }
    }

    if(showDialog.value){
        ConsentDialogBox(showDialog)
    }
}

//@Composable
//fun ProductDetailsCard(productName: String, productPrice: Long, mrpPrice: Long) {
//    DownPaymentCard(
//        cardHeader = stringResource(R.string.product_details)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(10.dp),
//            verticalAlignment = Alignment.Top
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.phone_image),
//                contentDescription = "Product Image",
//                modifier = Modifier
//                    .weight(0.3f)
//                    .height(150.dp)
//            )
//
//            Column(
//                modifier = Modifier
//                    .weight(0.7f)
//                    .padding(top = 10.dp),
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                RegisterText(
//                    text = productName,
//                    style = normal16Text400,
//                    textAlign = TextAlign.Start,
//                    boxAlign = Alignment.TopStart
//                )
//                RegisterText(
//                    text = "QTY: 1",
//                    style = normal14Text400,
//                    textAlign = TextAlign.Start,
//                    boxAlign = Alignment.TopStart
//                )
//                RegisterText(
//                    text = "MRP: ₹${CommonMethods().formatWithCommas(mrpPrice.toInt())}",
//                    style = normal14Text400,
//                    textAlign = TextAlign.Start,
//                    boxAlign = Alignment.TopStart
//                )
//                RegisterText(
//                    text = "Product price: ₹${CommonMethods().formatWithCommas(productPrice.toInt())}",
//                    style = normal14Text700,
//                    textAlign = TextAlign.Start,
//                    boxAlign = Alignment.TopStart
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    ClickableText(
//                        text = stringResource(id = R.string.cancellable),
//                        top = 0.dp,
//                        roundedCornerShape = 18.dp,
//                        textColor = appBlack,
//                        borderColor = appOrange,
//                        backgroundColor = appWhite,
//                        style = normal14Text400,
//                        horizontalPadding = 26.dp,
//                        verticalPadding = 5.dp
//                    ) {
//                        Log.d("DownPayment", "Cancellable")
//                    }
//                    ClickableText(
//                        text = stringResource(id = R.string.returnable),
//                        top = 0.dp,
//                        roundedCornerShape = 18.dp,
//                        textColor = appBlack,
//                        borderColor = appOrange,
//                        backgroundColor = appWhite,
//                        style = normal14Text400,
//                        horizontalPadding = 26.dp,
//                        verticalPadding = 5.dp
//                    ) { Log.d("DownPaymentScreen", "Returnable") }
//                }
////                RegisterText(
////                    text = stringResource(R.string.more_details),
////                    textColor = appOrange,
////                    style = normal14Text700.copy(textDecoration = TextDecoration.Underline),
////                    modifier = Modifier.clickable { Log.d("DownPaymentScreen", "more details") }
////                )
//            }
//        }
//    }
//}

@Composable
fun ProductDetailsCard(verifySessionResponse: VerifySessionResponse) {

    val fullProductName = buildString {
        append("")
        verifySessionResponse.data.sessionData.productBrand?.let {
            append(" $it")
        }
        verifySessionResponse.data.sessionData.productName?.let {
            append(" $it")
        }
        verifySessionResponse.data.sessionData.productModel?.let {
            append(" $it")
        }
    }


    DownPaymentCard(
        cardHeader = stringResource(R.string.product_details)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.phone_image),
//                contentDescription = "Product Image",
//                modifier = Modifier
//                    .weight(0.3f)
//                    .height(150.dp)
//            )


            verifySessionResponse.data.sessionData.productSymbol.let { imageUrl ->
                Log.d("DownPaymentScreen", "Image URL: $imageUrl")
                val imagePainter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build()
                )
                Image(
                    painter = imagePainter,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .weight(0.3f)
                        .height(150.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RegisterText(
//                    text = "Samsung Galaxy S24 Ultra 5G",
                    text = fullProductName,
                    style = normal16Text400,
                    textAlign = TextAlign.Start,
                    boxAlign = Alignment.TopStart
                )
                RegisterText(
//                    text = "QTY: 1",
                    text = verifySessionResponse.data.sessionData.productQuantity?.let { "QTY: $it" } ?: "QTY: 1",
                    style = normal14Text400,
                    textAlign = TextAlign.Start,
                    boxAlign = Alignment.TopStart
                )
                RegisterText(
//                    text = "MRP: ₹1,10000",
                    text = verifySessionResponse.data.sessionData.productMrpPrice?.let {
                        "MRP: ₹$it" } ?: "MRP: NA",
                    style = normal14Text400,
                    textAlign = TextAlign.Start,
                    boxAlign = Alignment.TopStart
                )
                RegisterText(
//                    text = "Product price: ₹99,000",
                    text = verifySessionResponse.data.sessionData.productSellingPrice?.toLongOrNull()?.let {
                        productPrice = it
                        maxAmount = it
                        amount = it / 3
                        "Product Price: ₹$it" } ?: "Product Price: NA",
                    style = normal14Text700,
                    textAlign = TextAlign.Start,
                    boxAlign = Alignment.TopStart
                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    ClickableText(
//                        text = stringResource(id = R.string.cancellable),
//                        top = 0.dp,
//                        roundedCornerShape = 18.dp,
//                        textColor = appBlack,
//                        borderColor = appOrange,
//                        backgroundColor = appWhite,
//                        style = normal14Text400,
//                        horizontalPadding = 26.dp,
//                        verticalPadding = 5.dp,
//                    ) {
//                        Log.d("DownPayment", "Cancellable")
//                    }
//                    ClickableText(
//                        text = stringResource(id = R.string.returnable),
//                        top = 0.dp,
//                        roundedCornerShape = 18.dp,
//                        textColor = appBlack,
//                        borderColor = appOrange,
//                        backgroundColor = appWhite,
//                        style = normal14Text400,
//                        horizontalPadding = 26.dp,
//                        verticalPadding = 5.dp,
//                    ) { Log.d("DownPaymentScreen", "Returnable") }
//                }

                val sessionData = verifySessionResponse.data.sessionData
                val isCancellable = sessionData.productCancellable == true
                val isReturnable = sessionData.productReturnable == true

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    ClickableText(
                        text = if (isCancellable == true)
                            stringResource(id = R.string.cancellable)
                        else
                            stringResource(id = R.string.non_cancellable),
                        top = 0.dp,
                        roundedCornerShape = 18.dp,
                        textColor = appBlack,
                        borderColor = appOrange,
                        backgroundColor = appWhite,
                        style = normal14Text400,
                        horizontalPadding = 26.dp,
                        verticalPadding = 5.dp,
                    ){}


                    ClickableText(
                        text = if (isReturnable == true)
                            stringResource(id = R.string.returnable)
                        else
                            stringResource(id = R.string.non_returnable),
                        top = 0.dp,
                        roundedCornerShape = 18.dp,
                        textColor = appBlack,
                        borderColor = appOrange,
                        backgroundColor = appWhite,
                        style = normal14Text400,
                        horizontalPadding = 26.dp,
                        verticalPadding = 5.dp,
                    ){}


                }
//                RegisterText(
//                    text = stringResource(R.string.more_details),
//                    textColor = appOrange,
//                    style = normal14Text700.copy(textDecoration = TextDecoration.Underline),
//                    modifier = Modifier.clickable { Log.d("DownPaymentScreen", "more details") }
//                )
            }
        }
    }
}

@Composable
fun PFPersonalDetailsCard(profile: Profile) {
    DownPaymentCard(
        cardHeader = stringResource(R.string.personal_details),
        image = painterResource(R.drawable.personal_details)
    ) {
        RegisterText(
            text = stringResource(id = R.string.please_review_details),
            start = 5.dp,
            end = 5.dp,
            bottom = 10.dp,
            top = 10.dp,
            style = normal16Text700,
            textColor = appOrange
        )
        DisplayCard(cardColor = backgroundOrange, start = 10.dp, end = 10.dp) {
            val fullName = listOfNotNull(profile.firstName, profile.lastName).joinToString(" ")
            if (fullName.isNotBlank()) {
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.full_name),
                    textValue = fullName,
                    top = 10.dp,
                    bottom = 5.dp,
                    showImage = true,
                    start = 10.dp
                )
            }

            profile.mobileNumber?.let { mobileNumber ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.contact_number),
                    textValue = mobileNumber,
                    bottom = 5.dp,
                    start = 10.dp,
                    showImage = true,
                    image = painterResource(R.drawable.call_icon)
                )
            }
            profile.address1?.let { address1 ->
                val area = address1
                val town = profile.address2 ?: ""
                val city = profile.city1 ?: ""
                val state = profile.state1 ?: ""
                val pincode = profile.pincode1 ?: ""
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.address),
                    textValue = "$area\n$town\n$city\n$state\n$pincode",
                    start = 10.dp,
                    showImage = true,
                    image = painterResource(R.drawable.location_icon)
                )
            }
            profile.panNumber?.let { panNumber ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.pan_number),
                    textValue = "",
                    bottom = 0.dp,
                    start = 10.dp,
                    showImage = true,
                    image = painterResource(R.drawable.pan_number)
                )
                TextInputLayout(
                    textFieldVal = TextFieldValue(text = panNumber),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    onTextChanged = { },
                    style = normal16Text700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
//                       .focusRequester(incomeFocus),
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun DownPaymentDetailsCard(
    amount: Long,
    maxAmount: Long,
    showInValidAmountError: Boolean,
    onAmountChange: (Long) -> Unit,
    onValidationChanged: (Boolean) -> Unit
) {
    var inputText by remember {
        mutableStateOf(TextFieldValue(CommonMethods().formatIndianCurrency(amount.toInt())))
    }
    var isError by remember { mutableStateOf(showInValidAmountError) }

    LaunchedEffect(amount) {
        val formatted = CommonMethods().formatIndianCurrency(amount.toInt())
        if (formatted != inputText.text) {
            inputText = TextFieldValue(
                text = formatted,
                selection = TextRange(formatted.length) // Place cursor at end
            )
        }
    }
    DownPaymentCard(
        cardHeader = stringResource(R.string.down_payment),
        image = painterResource(R.drawable.down_payment)
    ) {
        RegisterText(
            text = stringResource(id = R.string.enter_amount_you_are_willing_to_pay_as_downpayment),
            start = 5.dp,
            end = 5.dp,
            bottom = 10.dp,
            top = 10.dp,
            style = normal16Text400
        )
        OutlinedTextField(
            value = inputText,
            onValueChange = { newValue ->
                val rawInput = newValue.text
                val cursorPosition = newValue.selection.start
                val (cleanInput, newCursor) = processRawInput(rawInput, cursorPosition)
                val parsedValue = cleanInput.toLongOrNull()

                if (parsedValue != null) {
                    val limitedValue = applyMaxLimit(cleanInput,maxAmount).toLongOrNull() ?: 0L
                    val formatted = formatCurrency(limitedValue.toString())


                    if (limitedValue in 0..maxAmount) {
                        isError = false
                        onAmountChange(limitedValue)
                        onValidationChanged(false)

                        inputText = TextFieldValue(
                            text = formatted,
                            selection = TextRange(
                                calculateFormattedCursorPosition(
                                    cleanInput = cleanInput,
                                    originalCursor = newCursor,
                                    formattedValue = formatted
                                )
                            )
                        )

                    } else {
                        isError = true
                        onValidationChanged(true)
                    }
                } else {
                    inputText = TextFieldValue("₹", TextRange(1))
                    isError = true
                }
            },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = normal20Text700.copy(textAlign = TextAlign.Center),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appOrange,
                unfocusedBorderColor = appOrange,
                cursorColor = appOrange,
                textColor = appBlack
            )
        )
        if (isError) {
            RegisterText(
                text = stringResource(R.string.please_enter_valid_downpayment_amount),
                style = normal12Text400,
                textColor = errorRed
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun PreferredTenureCard() {
    DownPaymentCard(
        cardHeader = stringResource(R.string.preferred_tenure),
        image = painterResource(R.drawable.tenure)
    ) {
        RegisterText(
            text = stringResource(id = R.string.please_provide_your_preferred_tenure),
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp,
            top = 10.dp,
            style = normal16Text400
        )
        EditLoanTenureSliderUI(
            tenure = 12,
            minTenure = 3,
            maxTenure = 36,
            onValueChanged = {
//                editLoanRequestViewModel.onLoanTenureChanged(it.toString())
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

// @Composable
// fun DownPaymentScreen(navController: NavHostController, fromFlow: String) {
//    val amount = remember { mutableStateOf(TextFieldValue("20000")) }
//    var maxAmount = "99000"
//
//    FixedTopBottomScreen(
//        navController,
//        showHyperText = false,
// //        backGroudColorChange = amount.value.text != "",
//        primaryButtonText = stringResource(id = R.string.submit),
//        onBackClick = { navController.popBackStack()},
//        onPrimaryButtonClick = {
//            if (amount.value.text.isNotEmpty()) {
//                navigateToLoanProcessScreen(
//                    navController = navController, transactionId = "Sugu",
//                    statusId = 19, responseItem = amount.value.text,
//                    offerId = "1234", fromFlow = fromFlow
//                )
//            }
//        }
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            RegisterText(
//                text = stringResource(id = R.string.enter_downpayment_details),
//                style = normal32Text700
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            ProductDetails(maxAmount)
//            Spacer(modifier = Modifier.height(16.dp))
//            DownpaymentField(amount.value, maxAmount) { amount.value = it }
//        }
//    }
// }
//
// @Composable
// fun ProductDetails(maxAmount: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(40.dp),
//        border = BorderStroke(color = primaryOrange, width = 1.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier.padding(15.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.retail_power_tiller),
//                contentDescription = "Product Image",
//                modifier = Modifier
//                    .height(150.dp)
//                    .fillMaxWidth()
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Product Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(text = "Brand : Tata")
//            Spacer(modifier = Modifier.height(3.dp))
//            Text(text = "Product : Power Tiller Maestro 65P")
//            Spacer(modifier = Modifier.height(3.dp))
//            Text(
//                text = buildAnnotatedString {
//                    append("Price : ")
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append("₹" + CommonMethods().formatWithCommas(maxAmount.toDouble().toInt()) + " ")
//                    }
//                    withStyle(
//                        style = SpanStyle(
//                            textDecoration = TextDecoration.LineThrough,
//                            color = Color.Gray
//                        )
//                    ) {
//                        append("₹1,10,000 ")
//                    }
//                    withStyle(style = SpanStyle(color = primaryOrange)) {
//                        append("10% OFF")
//                    }
//                },
//                fontSize = 14.sp
//            )
//            Spacer(modifier = Modifier.height(3.dp))
//            Text(text = "Sold by : Tradeline", fontWeight = FontWeight.Light, fontSize = 14.sp)
//        }
//    }
// }
//
// @Composable
// fun DownpaymentField(
//    amount: TextFieldValue, maxAmount: String,
//    onAmountChange: (TextFieldValue) -> Unit
// ) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Enter the amount you are willing to pay as down payment for this product",
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 35.dp, end = 35.dp),
//            fontSize = 16.sp,
//        )
//        Spacer(modifier = Modifier.height(18.dp))
//
//        OutlinedTextField(
//            value = amount,
//            onValueChange = { newValue ->
//                val valueAsLong = newValue.text.toLongOrNull()
//                val maxAmountVal = maxAmount.toLongOrNull()
//
//                if (valueAsLong != null && valueAsLong > 0) {
//                    if (valueAsLong <= maxAmountVal!!) {
//                        onAmountChange(newValue)
//                    } else {
//                        CommonMethods().toastMessage(context, "Amount cannot exceed ₹$maxAmount")
//                    }
//                }
//            },
//            shape = RoundedCornerShape(8.dp),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            label = {
//                Text(
//                    text = stringResource(id = R.string.enter_amount),
//                    color = hintGray,
//                    style = normal18Text400,
//                    textAlign = TextAlign.Center
//                )
//            },
//            visualTransformation = VisualTransformation.None,
//            textStyle = LocalTextStyle.current,
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = appOrange,
//                unfocusedBorderColor = appOrange,
//                cursorColor = appOrange,
//                textColor = appBlack
//            )
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Display amount in words
//        val amountInWords =
//            amount.text.toLongOrNull()?.let { CommonMethods().numberToWords(it) }
//                ?: ""
//        if (amountInWords.isNotEmpty()) {
//            Text(
//                text = "( $amountInWords )",
//                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
//        }
//    }
// }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyConsentContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    registerViewModel: RegisterViewModel,
    onAcceptConsent: () -> Unit,
    showDialog: MutableState<Boolean>,
    startTimer: Boolean = false,
    timer: Int = 7
) {
    var secondsLeft by remember { mutableStateOf(timer) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(startTimer) {
        if (startTimer) {
            secondsLeft = timer
            isButtonEnabled = false
            while (secondsLeft > 0) {
                delay(1000L)
                secondsLeft--
            }
            isButtonEnabled = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 30.dp,
                bottom = 0.dp
            )
            .background(Color.Transparent)
    ) {
        // Bottom Sheet Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 0.dp)
                .background(
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ),
                    color = Color.White
                )
        ) {
//            Text(
//                text = stringResource(id = R.string.bottom_sheet_header),
//                style = bold20Text100,
//                color = appBlack,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 40.dp, bottom = 12.dp)
//            )
//
//            Divider(
//                color = backgroundOrange,
//                modifier = Modifier.padding(top = 5.dp)
//            )
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 25.dp),horizontalArrangement = Arrangement
                .SpaceBetween) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_header),
                    style = bold20Text100,
                    color = appBlack,
                    textAlign = TextAlign.Center,
                )

                Icon(
                    painter = painterResource(id = R.drawable.info_icon),
                    contentDescription = stringResource(id = R.string.bottom_sheet_close),
                    modifier = Modifier
//                    .padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
                        .clickable {
//                        coroutineScope.launch { bottomSheetState.hide() }
                            showDialog.value = true
                            registerViewModel.onCheckBoxDetailReset()
                        }
                )

            }
            HorizontalDivider(top = 5.dp, color = backgroundOrange, thickness=2.dp)

            HtmlText(stringResource(id = R.string.bottom_sheet_body))

            CurvedPrimaryButton(
                text = if (isButtonEnabled) stringResource(id = R.string.accept) else "Accept (${secondsLeft}s)",
                enabled = isButtonEnabled
            ) {
                coroutineScope.launch { bottomSheetState.hide() }
                registerViewModel.onCheckBoxDetailChanged(true)
                onAcceptConsent()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Floating Close Icon - Overlapping the top-right
        Icon(
            painter = painterResource(id = R.drawable.ic_close), // Replace with your actual icon
            contentDescription = "Close",
            modifier = Modifier
                .size(36.dp)
                .absoluteOffset(x = 12.dp, y = (-18).dp) // Move slightly outside top right
                .align(Alignment.TopEnd)
                .background(Color.White, shape = CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
                .clickable {
                    coroutineScope.launch {
                        coroutineScope.launch { bottomSheetState.hide() }
                        registerViewModel.onCheckBoxDetailReset()
                    }

                }
                .padding(6.dp),
            tint = appOrange
        )
    }
}
