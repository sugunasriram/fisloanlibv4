package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButtonFull
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SignUpText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.gray4E
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal24Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.semiBold24Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.skyBlueColor
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan.GstInvoiceLoanViewModel

@Composable
fun GstInformationScreen(navController: NavHostController, fromFlow: String, invoiceId: String) {
    val gstInvoiceLoanViewModel: GstInvoiceLoanViewModel = viewModel()
    val context = LocalContext.current
    val isLoading by gstInvoiceLoanViewModel.loading.collectAsState()
    val isLoaded by gstInvoiceLoanViewModel.loaded.collectAsState()
    val invoiceData by gstInvoiceLoanViewModel.invoiceData.collectAsState()

    val showInternetScreen by gstInvoiceLoanViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by gstInvoiceLoanViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by gstInvoiceLoanViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by gstInvoiceLoanViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by gstInvoiceLoanViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by gstInvoiceLoanViewModel.middleLoan.observeAsState(false)
    val errorMessage by gstInvoiceLoanViewModel.errorMessage.collectAsState()
    BackHandler {
        navigateToGstDetailsScreen(navController = navController, fromFlow = fromFlow)
    }
    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            if (isLoading) {
                CenterProgress()
            } else {
                if (isLoaded) {
                    ScreenWithHamburger(isSelfScrollable = false, navController = navController) {
                        GstInformationHeader()
                        GstBusinessDetails(invoiceData = invoiceData)
                        GstAdditionalInfo(
                            show = true,
                            navController = navController,
                            fromFlow = fromFlow
                        )
                        ConfirmButton(navController, invoiceId = invoiceId)
                    }
                } else {
                    gstInvoiceLoanViewModel.invoiceData(
                        context = context,
                        gstin = "24AAHFC3011G1Z4"
                    )
                }
            }
        }
    }
}

@Composable
fun GstInformationHeader() {
    CenteredMoneyImage(imageSize = 120.dp, top = 20.dp)
    RegisterText(
        text = stringResource(id = R.string.gst_information),
        textColor = appBlueTitle,
        style = normal32Text700
    )
    RegisterText(
        text = stringResource(id = R.string.business_details_confirm),
        textColor = gray4E,
        start = 40.dp,
        end = 40.dp,
        style = normal16Text400
    )
    RegisterText(
        text = stringResource(id = R.string.confirm_details),
        textColor = appBlueTitle,
        top = 40.dp,
        style = normal24Text500
    )
}

@Composable
fun GstBusinessDetails(invoiceData: GstInvoice?) {
    FullWidthRoundShapedCard(
        onClick = { /*TODO*/ },
        cardColor = skyBlueColor.copy(0.1f),
        shapeSize = 3.dp,
        start = 25.dp,
        end = 25.dp
    ) {
        invoiceData?.data?.gstinProfile?.forEach { profile ->
            profile?.tradeNam?.let { tradeName ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.legal_business_name),
                    bottom = 8.dp,
                    top = 10.dp,
                    textValue = tradeName,
                    style = normal12Text400
                )
            }
            profile?.adadr?.forEach { address ->
                address?.addr?.stcd?.let { state ->
                    OnlyReadAbleText(
                        textHeader = stringResource(id = R.string.place_of_business),
                        bottom = 8.dp,
                        top = 5.dp,
                        textValue = state,
                        style = normal12Text400
                    )
                }
            }
            profile?.gstin?.let { gstNumber ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.gstin),
                    bottom = 8.dp,
                    top = 5.dp,
                    textValue = gstNumber,
                    style = normal12Text400
                )
            }
            profile?.sts?.let { status ->
                OnlyReadAbleText(
                    textHeader = stringResource(id = R.string.gst_status),
                    bottom = 10.dp,
                    top = 5.dp,
                    textValue = status,
                    style = normal12Text400
                )
            }
        }
    }
}

@Composable
fun GstAdditionalInfo(
    show: Boolean,
    navController: NavHostController,
    fromFlow: String
) {
    if (show) {
        SignUpText(
            text = stringResource(id = R.string.add_another_gstin).uppercase(),
            style = semiBold24Text700,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            navigateToGstDetailsScreen(
                navController = navController,
                fromFlow = fromFlow
            )
        }
    } else {
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun ConfirmButton(navController: NavHostController, invoiceId: String) {
    CurvedPrimaryButtonFull(
        text = stringResource(id = R.string.confirm),
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 50.dp)
    ) {
        navigateToLoanProcessScreen(
            navController,
            transactionId = "Sugu",
            10,
            "No need",
            offerId = invoiceId,
            fromFlow = "Invoice Loan"
        )
    }
}
