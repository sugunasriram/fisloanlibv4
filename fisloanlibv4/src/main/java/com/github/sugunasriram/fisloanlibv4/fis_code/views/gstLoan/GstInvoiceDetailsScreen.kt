package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstInvoiceLoansScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal24Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.skyBlueColor
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.gstLoan.GstInvoiceDetailViewModel

@Composable
fun GstInvoiceDetailScreen(navController: NavHostController, fromFlow: String, invoiceId: String) {
    BackHandler {
        navigateToGstInvoiceLoansScreen(
            navController = navController,
            fromFlow = fromFlow
        )
    }
    val gstInvoiceDetailViewModel: GstInvoiceDetailViewModel = viewModel()
    val checkboxState: Boolean by gstInvoiceDetailViewModel.checkBoxDetail.observeAsState(false)
    val context = LocalContext.current
    FixedTopBottomScreen(
        navController = navController,
        isSelfScrollable = false,
        primaryButtonText = stringResource(id = R.string.proceed),
//        backGroudColorChange = checkboxState,
        checkBoxText = stringResource(id = R.string.agree_terms),
        showCheckBox = true,
        checkboxState = checkboxState,
        onCheckBoxChange = { isChecked ->
            gstInvoiceDetailViewModel.onCheckBoxDetailChanged(isChecked) // Update state in ViewModel
        },
        onPrimaryButtonClick = {
            if (checkboxState) {
                navigateToGstInvoiceLoansScreen(
                    navController = navController,
                    fromFlow = fromFlow
                )
            } else {
                CommonMethods().toastMessage(
                    context = context,
                    toastMsg = context.getString(R.string.please_accept_terms)
                )
            }
        }
    ) {
        CenteredMoneyImage(imageSize = 65.dp, top = 15.dp)
        RegisterText(
            text = stringResource(id = R.string.share_consent_for_gst_invoice),
            start = 0.dp,
            textColor = appBlueTitle,
            top = 15.dp,
            end = 15.dp,
            style = normal24Text700,
            textAlign = TextAlign.Start
        )
        RegisterText(
            text = stringResource(id = R.string.provide_consent_to_share_your_gst),
            textColor = appBlueTitle,
            start = 60.dp,
            end = 50.dp,
            top = 10.dp,
            style = normal14Text400,
            textAlign = TextAlign.Start
        )
        GstDataConsentCard()
        CompanyConsentCard()
    }
}

@Composable
fun GstDataConsentCard() {
    FullWidthRoundShapedCard(
        onClick = { /*TODO*/ },
        alignment = Alignment.Start,
        start = 15.dp,
        end = 15.dp,
        top = 15.dp,
        cardColor = skyBlueColor.copy(0.1f),
        shapeSize = 3.dp

    ) {
        RegisterText(
            boxAlign = Alignment.TopStart,
            text = stringResource(id = R.string.gst_data_consent),
            textColor = appBlack,
            style = bold16Text400
        )
        RegisterText(
            text = stringResource(id = R.string.gst_data_consent_info),
            textColor = appBlack,
            top = 15.dp,
            end = 5.dp,
            style = normal12Text400,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun CompanyConsentCard() {
    FullWidthRoundShapedCard(
        onClick = { /*TODO*/ },
        alignment = Alignment.Start,
        shapeSize = 3.dp,
        cardColor = skyBlueColor.copy(0.1f),
        start = 15.dp,
        end = 15.dp,
        top = 20.dp
    ) {
        RegisterText(
            boxAlign = Alignment.TopStart,
            textColor = appBlack,
            style = bold16Text400,
            text = stringResource(id = R.string.credit_info_company_consent)
        )
        RegisterText(
            text = stringResource(id = R.string.credit_info_company_consent_info),
            textColor = appBlack,
            top = 15.dp,
            end = 5.dp,
            style = normal12Text400,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview
@Composable
fun GstInvoiceDetailScreenPreview() {
    GstInvoiceDetailScreen(
        navController = rememberNavController(),
        fromFlow = "Invoice Loan",
        invoiceId = "1234570"
    )
}
