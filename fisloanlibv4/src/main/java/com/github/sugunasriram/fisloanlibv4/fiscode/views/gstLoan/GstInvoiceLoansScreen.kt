package com.github.sugunasriram.fisloanlibv4.fiscode.views.gstLoan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.GstTransactionCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToInvoiceDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.gstLoan.GstInvoiceLoanViewModel
import kotlinx.serialization.json.Json

@Composable
fun GstInvoiceLoansScreen(navController: NavHostController, fromFlow: String) {

    val gstInvoiceLoanViewModel: GstInvoiceLoanViewModel = viewModel()
    val selectedItems by gstInvoiceLoanViewModel.selectedItems.observeAsState(emptySet())

    val itemCount = 10 // Number of items
    val context = LocalContext.current
    val isLoading by gstInvoiceLoanViewModel.loading.collectAsState()
    val isLoaded by gstInvoiceLoanViewModel.loaded.collectAsState()
    val invoiceData by gstInvoiceLoanViewModel.invoiceData.collectAsState()
    val navigationToSignIn by gstInvoiceLoanViewModel.navigationToSignIn.collectAsState()

    val showInternetScreen by gstInvoiceLoanViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by gstInvoiceLoanViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by gstInvoiceLoanViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by gstInvoiceLoanViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by gstInvoiceLoanViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by gstInvoiceLoanViewModel.middleLoan.observeAsState(false)
    val errorMessage by gstInvoiceLoanViewModel.errorMessage.collectAsState()

    when {
        navigationToSignIn -> navigateSignInPage (navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
             if(isLoading){
                 CenterProgress()
             } else {
                 if (isLoaded) {
                     invoiceData?.data?.id?.let { gstId ->
                         FixedTopBottomScreen(
                             navController = navController, primaryButtonText = stringResource(id = R.string.next),
                             onBackClick = { navController.popBackStack() },
                             onPrimaryButtonClick = {
                                 navigateToLoanProcessScreen(
                                     navController = navController, transactionId="Sugu",
                                     statusId = 11, responseItem = "No Need",
                                     offerId = gstId, fromFlow = "Invoice Loan"
                                 )
                             }
                         ) {
                             StartingText(
                                 text = stringResource(id = R.string.gst_invoices_for_loan),
                                 textColor = appBlueTitle, start = 30.dp, end = 30.dp, top = 10.dp,
                                 bottom = 5.dp, style = normal32Text700, alignment = Alignment.TopStart
                             )

                             invoiceData?.data?.invoices?.forEach { invoice ->
                                 GstTransactionCard(
                                     showCheckBox = false, end = 8.dp, boxState = selectedItems.contains(1),
                                     onCheckedChange = {
                                         gstInvoiceLoanViewModel.toggleItem(1)
                                     },
                                     onClick = {
                                         val json = Json { prettyPrint = true }
                                         invoiceData?.let { it ->
                                             val responseItem = json.encodeToString(GstInvoice.serializer(),it)
                                             navigateToInvoiceDetailScreen(
                                                 navController = navController, fromFlow = fromFlow,
                                                 invoiceId = responseItem
                                             )
                                         }
                                     },
                                     invoiceData = invoice
                                     )
                             }
                         }
                     }
            } else {
                gstInvoiceLoanViewModel.invoiceData(
                    context = context, gstin = "24AAHFC3011G1Z4"
                )
            }
        }
        }
    }
}


@Preview
@Composable
fun GstInvoiceLoansScreenPreview() {
    GstInvoiceLoansScreen(
        navController = rememberNavController(), fromFlow = "Invoice Loan"
    )
}