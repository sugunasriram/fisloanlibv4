package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanOffersListDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstCatalog
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstOfferConfirmResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.UpdateResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.customBlueColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal30Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.slideActiveColor
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CustomSnackBar
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.showSnackError
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.EditLoanRequestViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.EditLoanRequestViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun EditLoanRequestScreen(
    navController: NavHostController,
    id: String,
    amount: String,
    minAmount: String,
    tenure: String,
    offerId: String,
    fromFlow: String
) {

    val context = LocalContext.current

    val editLoanRequestViewModel: EditLoanRequestViewModel = viewModel(
        factory = EditLoanRequestViewModelFactory(amount, minAmount, tenure)
    )
    val navigationToSignIn by editLoanRequestViewModel.navigationToSignIn.collectAsState()

    val generalError by editLoanRequestViewModel.generalError.observeAsState("")


    val initialLoanAmount = amount.toDoubleOrNull() ?: 0.0
    val initialLoanBeginAmount = minAmount.toDoubleOrNull() ?: 0.0
//    val initialLoanEndAmount = initialLoanAmount + 10000.0
    val initialLoanEndAmount = initialLoanAmount

    val trimmedTenure = tenure.replace(" months", "")
        .replace(" Months", "")
    val initialLoanTenure = trimmedTenure.toIntOrNull() ?: 0

    val initialLoanBeginTenure = if ((initialLoanTenure - 10) < 1) 2 else (initialLoanTenure - 10)

    val initialLoanEndTenure = initialLoanTenure + 10


    val loanAmount: Double by editLoanRequestViewModel.loanAmount.collectAsState(
        initialLoanBeginAmount
    )
    val loanTenure by editLoanRequestViewModel.loanTenure.collectAsState(initialLoanBeginTenure)
    val showInternetScreen by editLoanRequestViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by editLoanRequestViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by editLoanRequestViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by editLoanRequestViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by editLoanRequestViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by editLoanRequestViewModel.middleLoan.observeAsState(false)
    val errorMessage by editLoanRequestViewModel.errorMessage.collectAsState()


    val isEdited by editLoanRequestViewModel.isEdited.collectAsState()
    val isEditProcess by editLoanRequestViewModel.isEditProcess.collectAsState()
    val editLoanResponse by editLoanRequestViewModel.editLoanResponse.collectAsState()
    val gstOfferConfirmResponse by editLoanRequestViewModel.gstOfferConfirmResponse.collectAsState()

    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var loanSlider by remember { mutableFloatStateOf(0f) }
    var tenureSlider by remember { mutableFloatStateOf(0f) }


    val stepSize = 1000f
    val numberOfSteps = ((initialLoanEndAmount - initialLoanBeginAmount) / stepSize).toInt() - 1

    val onLoanSliderChange: (Float) -> Unit = { newValue ->

        // Update the loanSlider value
        loanSlider = newValue

        // Calculate the new income based on the slider value, rounding to the nearest 1000
        val newIncome =
            (((newValue / 100) * (initialLoanEndAmount - initialLoanBeginAmount) + initialLoanBeginAmount) / 1000).roundToInt() * 1000

        // Clamp the newIncome value to stay within the range of initialLoanBeginAmount and initialLoanEndAmount
        val clampedIncome =
            newIncome.coerceIn(initialLoanBeginAmount.toInt(), initialLoanEndAmount.toInt())


        // Convert the clampedIncome back to a string and pass it to the ViewModel
        editLoanRequestViewModel.onLoanAmountChanged(clampedIncome.toString())
    }

    val onTenureSliderChange: (Float) -> Unit = { tenureValue ->

        // Update the tenureSlider value
        tenureSlider = tenureValue

        // Calculate the new income based on the slider value
        val newTenure = ((tenureValue / 100) * (initialLoanEndTenure - initialLoanBeginTenure) +
                initialLoanBeginTenure).toInt()

        // Clamp the newIncome value to stay within the range of initialLoanBeginTenure and
        // initialLoanEndTenure
        val clampedTenure = newTenure.coerceIn(initialLoanBeginTenure, initialLoanEndTenure)

        // Convert the clampedIncome back to a string and pass it to the ViewModel
        editLoanRequestViewModel.onLoanTenureChanged(clampedTenure.toString())
    }

    // Function to update slider position when Loan Amount changes
    DisposableEffect(loanAmount) {
        val initialIncome = loanAmount
        // Calculate position based on loan amount and loan range
        val sliderPosition = (((initialIncome - initialLoanBeginAmount.toInt()) /
                (initialLoanEndAmount - initialLoanBeginAmount) * 100))

        loanSlider = sliderPosition.toFloat()

        onDispose { }
    }


    // Function to update slider position when Tenure changes
//    DisposableEffect(loanTenure) {
//        val initialTenure = loanTenure
//        // Calculate position based on loan tenure and loan range
//        val sliderPosition = (((initialTenure - initialLoanBeginTenure.toDouble()) /
//                (initialLoanEndTenure - initialLoanBeginTenure).toDouble() * 100))
//
//
//        tenureSlider = sliderPosition.toFloat()
//
//        onDispose { }
//    }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            loanTenure?.let {
                EditLoanRequestView(
                    isEditProcess = isEditProcess, navController = navController,
                    loanAmount = loanAmount, editLoanRequestViewModel = editLoanRequestViewModel,
                    initialLoanBeginAmount = minAmount.toDouble(), context = context,
                    loanSlider = loanSlider, initialLoanEndAmount = initialLoanEndAmount,
                    onLoanSliderChange = onLoanSliderChange, numberOfSteps = numberOfSteps,
                    initialLoanAmount = initialLoanAmount,
                    loanTenure = it.toInt(), generalError = generalError,
                    coroutineScope = coroutineScope, snackState = snackState, fromFlow = fromFlow,
                    isEdited = isEdited, id = id, editLoanResponse = editLoanResponse,
                    gstOfferConfirmResponse = gstOfferConfirmResponse, offerId = offerId
                )
            }
        }
    }
}

@Composable
@SuppressLint("ResourceType")
fun EditLoanRequestView(
    isEditProcess: Boolean, navController: NavHostController,
    loanAmount: Double, editLoanRequestViewModel: EditLoanRequestViewModel,
    initialLoanBeginAmount: Double, context: Context, loanSlider: Float,
    initialLoanEndAmount: Double, onLoanSliderChange: (Float) -> Unit,
    numberOfSteps: Int, initialLoanAmount: Double, loanTenure: Int,
    generalError: String?, coroutineScope: CoroutineScope,
    snackState: SnackbarHostState, fromFlow: String,
    isEdited: Boolean, id: String, editLoanResponse: UpdateResponse?,
    gstOfferConfirmResponse: GstOfferConfirmResponse?, offerId: String,
) {
    var button1Visible = false
    var button2Visible = false
    val image: Int = R.raw.processing_wait

    if (isEditProcess) {
        //Sugu
        LoaderAnimation(
            text = stringResource(id = R.string.please_wait_processing),
            updatedText = "",
            image = image, navController = navController
        )
    } else {
        FixedTopBottomScreen(
            navController = navController, showBottom = false, isSelfScrollable = false,
            onBackClick = { navController.popBackStack() }
        ) {
            CenteredMoneyImage(imageSize = 180.dp, top = 10.dp)
            RegisterText(
                text = stringResource(id = R.string.edit_loan_amount),
                textColor = appBlueTitle,
                bottom = 20.dp, top = 20.dp,
                style = normal30Text700
            )
            val formattedLoanAmount: String = if (loanAmount > 0.0) {
                CommonMethods().formatIndianDoubleCurrency(loanAmount)
            } else {
                CommonMethods().formatIndianCurrency(0)
            }

            TextInputLayout(
                textFieldVal = TextFieldValue(
                    text = formattedLoanAmount,
                    selection = TextRange(formattedLoanAmount.length)
                ),
                readOnly = true,

                /* here when user types . to enter double number, that dot is not shown in TextField,
                 can be fixed by using custom TextField

                 */

                onTextChanged = { newText ->
                    editLoanRequestViewModel.onLoanAmountChanged(
                        newText.text
                    )
                },
                hintText = stringResource(id = R.string.rupee),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                onLostFocusValidation = { newText ->
                    validateInputLoanAmount(
                        newText, initialLoanBeginAmount,
                        initialLoanEndAmount, context,
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            )

            Slider(
                value = loanSlider,
                onValueChange = onLoanSliderChange,
                valueRange = 0f..100f,
                steps = numberOfSteps,

                colors = SliderDefaults.colors(
                    thumbColor = appOrange,
                    activeTrackColor = slideActiveColor,
                    inactiveTrackColor = customBlueColor,
                ),
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 10.dp)
            )
            val formattedBeginLoanAmount =
                CommonMethods().formatIndianCurrency(initialLoanBeginAmount.toInt())

            val formattedEndLoanAmount = CommonMethods().formatIndianCurrency(
                initialLoanAmount.toInt()
            )
            SpaceBetweenText(
                text = formattedBeginLoanAmount,
                value = formattedEndLoanAmount,
                start = 45.dp, end = 45.dp, top = 0.dp
            )

            if (!generalError.isNullOrEmpty()) {
                showSnackError(coroutineScope, snackState, generalError)
                SnackbarHost(hostState = snackState) {
                    CustomSnackBar(message = generalError, containerColor = Color.Red)
                }
            }
            if (isEdited) {
                navigateBasedSuccess(
                    gstOfferConfirmResponse = gstOfferConfirmResponse,
                    editLoanResponse = editLoanResponse,
                    navController = navController,
                    fromFlow = fromFlow,
                    id = id,
                )
            } else {
                button1Visible = true
            }
            button2Visible = true

            Spacer(modifier = Modifier.padding(26.dp))

            TwoButtonsInRow(
                button1Visible = true,
                button2Visible = true,
                editLoanRequestViewModel = editLoanRequestViewModel,
                loanAmount = loanAmount.toString(),
                loanTenure = loanTenure.toString(),
                context = context,
                navController = navController,
                id = id,
                offerId = offerId,
                fromFlow = fromFlow
            )
        }
    }
}

fun navigateBasedSuccess(
    editLoanResponse: UpdateResponse?,
    navController: NavHostController,
    fromFlow: String,
    id: String,
    gstOfferConfirmResponse: GstOfferConfirmResponse?
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        editLoanResponse?.data?.offerResponse?.let { offerResponse ->
            editLoanResponse.data.id?.let {
                val json = Json { prettyPrint = true }
                val responseItem = json.encodeToString(
                    OfferResponseItem.serializer(), offerResponse
                )
                navigateToLoanOffersListDetailScreen(
                    navController = navController,
                    responseItem = responseItem, id = id,
                    showButtonId = "0", fromFlow = fromFlow
                )
            }
        }
    } else {
        gstOfferConfirmResponse?.data?.offerResponse?.let { catalog ->
            gstOfferConfirmResponse.data.offerResponse.id?.let {
                val json = Json { prettyPrint = true }
                val responseItem = json.encodeToString(GstCatalog.serializer(), catalog)
                navigateToLoanOffersListDetailScreen(
                    navController = navController, responseItem = responseItem,
                    id = id, showButtonId = "0", fromFlow = fromFlow
                )
            }
        }
    }
}

fun validateInputLoanAmount(
    newText: TextFieldValue, initialLoanBeginAmount: Double,
    initialLoanEndAmount: Double, context: Context
): Boolean {
    val convertedText = newText.text.replace("₹", "")
        .replace(",", "")

    if ((convertedText.toDoubleOrNull() ?: 0.0) < initialLoanBeginAmount ||
        (convertedText.toDoubleOrNull() ?: 0.0) > initialLoanEndAmount
    ) {
        CommonMethods().toastMessage(
            context,
            context.getString(R.string.please_enter_valid_income_within_limits)
        )
        return false
    }
    return true
}


@Composable
fun TwoButtonsInRow(
    button1Visible: Boolean, button2Visible: Boolean,
    editLoanRequestViewModel: EditLoanRequestViewModel,
    loanAmount: String, loanTenure: String, context: Context,
    navController: NavHostController, id: String, offerId: String, fromFlow: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (button1Visible && button2Visible) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.Center
        }
    ) {
        if (button2Visible) {
            CurvedPrimaryButtonFull(
                text = stringResource(id = R.string.go_back_to_offers).uppercase(Locale.ROOT),
                backgroundColor = appRed,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                    fontWeight = FontWeight(500)
                ),
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 2.dp,
                        bottom = 10.dp,
                        top = 10.dp
                    )
                    .weight(1.5f)
            ) { navController.popBackStack() }
        }

        if (button1Visible) {
            CurvedPrimaryButtonFull(
                text = stringResource(id = R.string.submit),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                    fontWeight = FontWeight(500)
                ),
                modifier = Modifier
                    .padding(start = 2.dp, end = 10.dp, top = 10.dp)
                    .weight(1f)
            ) {
                if (fromFlow == "Personal Loan") {
                    val updatedLoanTenure = loanTenure.lowercase().replace(" months", "")
//                    editLoanRequestViewModel.checkValid(
//                        loanAmount, loanTenure, context, UpdateLoanAmountBody(
//                            requestTerm = updatedLoanTenure,
//                            requestAmount = loanAmount,
//                            id = id,
//                            offerId = offerId,
//                            loanType = "PERSONAL_LOAN",
//                        ),
//                        fromFlow = fromFlow
//                    )
                } else {
                    editLoanRequestViewModel.gstInitiateOffer(
                        offerId, "INVOICE_BASED_LOAN", context, loanAmount, id
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun EditLoanRequestScreenPreview() {
    EditLoanRequestScreen(
        rememberNavController(),
        "",
        "662000.90",
        "2000",
        "5",
        "123456789",
        "Personal"
    )
}

