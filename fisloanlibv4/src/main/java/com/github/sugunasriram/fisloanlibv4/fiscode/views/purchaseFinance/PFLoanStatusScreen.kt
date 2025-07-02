package com.github.sugunasriram.fisloanlibv4.fiscode.views.purchaseFinance

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayBackground
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanIssueCardGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal28Text700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun PreviewPFLoanStatusScreen() {
    PFLoanStatusScreen(rememberNavController(), "Purchase Finance")
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceType")
@Composable
fun PFLoanStatusScreen(navController: NavHostController, fromFlow: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewEmiBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    BackHandler {
        navigateApplyByCategoryScreen(navController)
    }
    when {
//        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
//        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
//        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
//        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
//        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            CustomModalBottomSheet(
                bottomSheetState = viewEmiBottomSheetState,
                sheetContent = {
                    EmiScheduleModalContent(
                        bottomSheetState = viewEmiBottomSheetState,
                        coroutineScope = coroutineScope,
                        context = context,
                    )

                }) {
                FixedTopBottomScreen(
                    navController = navController,
                    backgroundColor = appWhite,
                    contentStart = 0.dp,
                    contentEnd = 0.dp,
                    showTopBar = false,
                    showBottom = true,
                    showSingleButton = true,
                    primaryButtonText = stringResource(R.string.proceed_with_down_payment),
                    onPrimaryButtonClick = {},
                ) {
//                val loanStatus="Requested"
                    val loanStatus = "Approved"
                    CenteredMoneyImage(
                        image = R.drawable.loan_status_image,
                        contentScale = ContentScale.Fit,
                        imageSize = 150.dp,
                        top = 40.dp
                    )
                    PFLoanHeaderCard(loanStatus = loanStatus)
                    PFLoanViewEMICard(
                        loanStatus = loanStatus,
                        onClickCard = { coroutineScope.launch { viewEmiBottomSheetState.show() } })
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 6.dp, color = backgroundOrange
                    )
                    PFLoanProductDetailsCard()
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 6.dp, color = backgroundOrange
                    )
                    PFLoanStepTracker(
                        isLoanRequested = true,
                        loanRequestedDate = "01 December 2024",
                        loanStatus = loanStatus,
                        downPaymentAmount = "40,000"
                    )

                }
            }

        }
    }
}

@Composable
fun PFLoanHeaderCard(loanStatus: String = "Requested", loanAmount: String = "₹59,000.00") {
    RegisterText(
        text = "Loan $loanStatus!",
        style = normal28Text700,
        textColor = if (loanStatus == "Requested") appOrange else appGreen
    )

    MultiStyleText(
        text1 = stringResource(R.string.loan_amount_), color1 = appBlack,
        text2 = loanAmount, color2 = appGreen,
        style1 = normal20Text700, style2 = normal20Text700,
        arrangement = Arrangement.Center,
        start = 0.dp, top = 16.dp, bottom = 8.dp
    )

    RegisterText(
        text = stringResource(R.string.your_loan_for_the_below_product_has_been_approved),
        style = normal14Text500,
        textColor = hintGray
    )
    if (loanStatus != "Requested")
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 90.dp, vertical = 20.dp)
                .border(1.dp, appOrange, shape = RoundedCornerShape(8.dp))
                .background(appWhite, shape = RoundedCornerShape(8.dp))
        ) {
            MultiStyleText(
                text1 = "Offer valid till - ", color1 = appBlack,
                text2 = "05th Dec 2024", color2 = errorRed,
                style1 = normal14Text500, style2 = normal14Text500,
                arrangement = Arrangement.Center,
                start = 0.dp, top = 8.dp, bottom = 8.dp
            )
        }

}

@Composable
fun PFLoanViewEMICard(loanStatus: String, onClickCard: (() -> Unit)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .clickable { onClickCard() }
            .padding(horizontal = 30.dp, vertical = if (loanStatus == "Requested") 20.dp else 5.dp)
            .background(loanIssueCardGray, shape = RoundedCornerShape(8.dp))
    ) {

        RegisterText(
            text = stringResource(R.string.view_emi_schedule),
            style = normal16Text500,
            textColor = appBlack,
            modifier = Modifier.weight(0.7f),
            top = 12.dp, bottom = 12.dp
        )
        Image(
            painter = painterResource(id = R.drawable.arrow_forward_orange),
            contentDescription = "arrow",
            modifier = Modifier
                .size(18.dp)
                .weight(0.3f)
        )

    }
}

@Composable
fun PFLoanProductDetailsCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .border(1.dp, grayBackground, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {

        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(top = 10.dp, bottom = 10.dp, start = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            RegisterText(
                text = stringResource(R.string.product_details), bottom = 5.dp,
                style = normal14Text700, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )
            RegisterText(
                text = "ProductName: Samsung Galaxy S24 Ultra 5G",
                style = normal14Text400, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )
            RegisterText(
                text = "QTY: 1",
                style = normal14Text400, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )
            RegisterText(
                text = "Product price: ₹99,000",
                style = normal14Text700, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )
            RegisterText(
                text = "Sold by : Sanitha Mobiles",
                style = normal14Text400, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )
            RegisterText(
                text = "Finance : DMI",
                style = normal14Text700, textAlign = TextAlign.Start, boxAlign = Alignment.TopStart
            )


        }
        Image(
            painter = painterResource(id = R.drawable.phone_image),
            contentDescription = "Product Image",
            modifier = Modifier
                .weight(0.3f)
                .height(120.dp)
        )
    }

}

@Composable
fun PFLoanStepTracker(
    isLoanRequested: Boolean,
    loanRequestedDate: String,
    loanStatus: String,
    downPaymentAmount: String
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Step 1: Loan Requested
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Loan Requested",
                tint = appGreen, // Green
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            MultiStyleText(
                text1 = "Loan $loanStatus - ", color1 = appBlack,
                text2 = loanRequestedDate, color2 = appBlack,
                style1 = normal16Text700, style2 = normal16Text400,
                start = 0.dp, top = 0.dp
            )
        }

        // Connecting line
        Box(
            modifier = Modifier
                .padding(start = 11.dp)
                .width(2.dp)
                .height(24.dp)
                .background(grayD9)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.RadioButtonUnchecked,
                contentDescription = "Downpayment",
                tint = grayD9,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Proceed with downpayment of ₹$downPaymentAmount",
                color = if (loanStatus == "Approved") appBlack else hintGray,
                style = normal16Text500
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmiScheduleModalContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    context: Context,
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(shape = RoundedCornerShape(40.dp), color = Color.Transparent)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.view_emi_schedule),
                style = bold20Text100,
                color = appBlack,
                modifier = Modifier.weight(0.9f)
            )
            Icon(
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = stringResource(id = R.string.bottom_sheet_close),
                modifier = Modifier
                    .weight(0.1f)
                    .padding(end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .clickable {
                        coroutineScope.launch { bottomSheetState.hide() }
                    }
            )
        }

        com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider(
            top = 3.dp,
            color = backgroundOrange,
            start = 3.dp,
            end = 3.dp
        )

        Column(modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PFTableRow(
                emiNumber = "EMI No.",
                dueDate = "EMI Due Date",
                amount = "EMI Amount",
                isTitleRow = true
            )

            PFTableRow(
                emiNumber = "1",
                dueDate = "05/02/2024",
                amount = "2753.33",
            )
            PFTableRow(
                emiNumber = "2",
                dueDate = "05/03/2024",
                amount = "2753.33",
            )
            PFTableRow(
                emiNumber = "3",
                dueDate = "05/04/2024",
                amount = "2753.33",
            )
            PFTableRow(
                emiNumber = "4",
                dueDate = "05/05/2024",
                amount = "2753.33",
            )
        }

    }
}

@Composable
fun PFTableRow(
    emiNumber: String, dueDate: String, amount: String,
    isTitleRow: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, bottom = 8.dp)
    ) {
        Text(
            text = emiNumber,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.Center
        )
        Text(
            text = dueDate,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = amount,
            fontWeight = if (isTitleRow) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

    }
}