package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableLoanStatusCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstLoanOfferListScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.GstOfferListModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.OfferResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

// @Composable
// fun GstInvoiceLoanOfferScreen(
//    navController: NavHostController, offerResponse: String, fromFlow: String
// ) {
//    val offer = json.decodeFromString(GstData.serializer(), offerResponse)
//
//    val transactionId = offer?.offerResponse?.get(0)?.txnID
//
//    BackHandler {
//
//        transactionId?.let {
//            navigateToLoanProcessScreen(
//                navController = navController, transactionId = it, statusId = 12, responseItem =
//                offerResponse,
//                offerId = "1234", fromFlow = "Invoice Loan"
//            )
//        }
//    }
//
//    FixedTopBottomScreen(
//        navController = navController, showBottom = false, onClick = {},
//        onBackClick = { navController.popBackStack() }
//    ) {
//        StartingText(
//            text = stringResource(id = R.string.loan_offer), textColor = appBlueTitle,
//            start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp,
//            style = normal32Text700
//        )
//        StartingText(
//            text = stringResource(id = R.string.select_invoice_from_offer_list),
//            start = 30.dp, end = 30.dp, bottom = 5.dp, style = normal14Text400,
//            textAlign = TextAlign.Start,
//        )
//        val invoice = stringResource(id = R.string.invoices)
//        val totalInvoices = offer.offerResponse?.size
//        StartingText(
//            text = "$invoice ($totalInvoices)", textColor = appBlueTitle,
//            start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp, style = bold14Text500
//        )
//        StartingText(
//            text = stringResource(id = R.string.sharing_invoices),
//            start = 30.dp, end = 30.dp, bottom = 5.dp, style = normal14Text400,
//            textAlign = TextAlign.Start,
//        )
//        repeat(1) {
//            transactionId?.let {
//
//                OfferCard(
//                    offer = offer, transactionId = transactionId, navController = navController,
//                    fromFlow = fromFlow
//                )
//            }
//        }
//    }
// }

@Composable
fun GstInvoiceLoanOfferScreen(
    navController: NavHostController,
    offerResponse: String,
    fromFlow: String
) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current
    // Handle back button press
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            navigateApplyByCategoryScreen(navController)
        } else {
            CommonMethods().toastMessage(
                context = context,
                toastMsg = "Press back again to go to the home page"
            )
            backPressedTime = currentTime
        }
    }
    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()

    val gstOfferList by loanAgreementViewModel.gstOfferList.collectAsState()
    val gstOfferListLoaded by loanAgreementViewModel.gstOfferListLoaded.collectAsState()
    val gstOfferListLoading by loanAgreementViewModel.gstOfferListLoading.collectAsState()

    val navigationToSignIn by loanAgreementViewModel.navigationToSignIn.collectAsState()

    if (navigationToSignIn) {
        navigateSignInPage(navController)
    } else if (gstOfferListLoading) {
        CenterProgress()
    } else {
        if (gstOfferListLoaded) {
            val transactionId = gstOfferList?.data?.get(0)?.offerResponse?.txnID

            FixedTopBottomScreen(
                navController = navController,
                showBottom = false,
                onPrimaryButtonClick = {},
                onBackClick = { navController.popBackStack() }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_offer),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )
                StartingText(
                    text = stringResource(id = R.string.select_invoice_from_offer_list),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                val invoice = stringResource(id = R.string.invoices)
//                val totalInvoices = gstOfferList?.data?.get(0)?.offerResponse?.size
                val totalInvoices = gstOfferList?.data?.size
                StartingText(
                    text = "$invoice ($totalInvoices)",
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = bold14Text500
                )
                StartingText(
                    text = stringResource(id = R.string.sharing_invoices),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                repeat(1) {
                    transactionId?.let {
//                        val firstOffer = gstOfferList?.data?.get(0)
//                        firstOffer?.let { offer ->
                        gstOfferList?.let { gstOfferList ->

                            OfferCard(
                                offer = gstOfferList,
                                transactionId = transactionId,
                                navController = navController,
                                fromFlow = fromFlow
                            )
                        }
                    }
                }
            }
        } else {
            loanAgreementViewModel.gstOfferList(loanType = "INVOICE_BASED_LOAN", context = context)
        }
    }
}

// fun OfferCard(offer: GstOfferData, transactionId:String, navController: NavHostController, fromFlow:
@Composable
fun OfferCard(
    offer: GstOfferListModel,
    transactionId: String,
    navController: NavHostController,
    fromFlow:
        String
) {
    ClickableLoanStatusCard(
        cardColor = Color.White,
        borderColor = appBlueTitle,
        start = 30.dp,
        end = 30.dp,
        top = 15.dp,
        onClick = {
            // Sugu - TODO
            offer?.data?.get(0)?.offerResponse?.let { offerResponse ->
                val offerDetail = json.encodeToString(OfferResponse.serializer(), offerResponse)
                //            val offerDetail = json.encodeToString(GstOfferData.serializer(), offer?.data?.get(0)?.offerResponse)
                navigateToGstLoanOfferListScreen(
                    navController = navController,
                    transactionId = transactionId,
                    offerResponse =
                    offerDetail,
                    fromFlow = fromFlow
                )
            }
        }
    ) {
        offer.data?.forEach { offerResponse ->
            offerResponse?.offerResponse?.providerDescriptor?.name?.let { name ->
                StartingText(
                    text = "Padmavati Steel Corporation Pvt. Ltd.",
                    textAlign = TextAlign.Start,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 5.dp,
                    top = 10.dp,
                    style = normal14Text400

                )
            }
            offerResponse?.offerResponse?.itemTags?.forEach { itemTag ->
                itemTag.tags?.invoiceNumber?.let { invoiceNumber ->
                    itemTag.tags.principal?.let { invoiceAmount ->

                        val today = LocalDate.now()
                        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
                        val formattedDate = today.format(formatter)

                        StartingText(
                            text = "$formattedDate.   $invoiceNumber.     \u20A8 $invoiceAmount",
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 10.dp,
                            top = 10.dp,
                            style = normal14Text400,
                            textAlign = TextAlign.Start
                        )
                        offerResponse?.offerResponse?.providerDescriptor?.images?.get(0)?.url?.let { imageUrl ->
                            val totalInvoices = offer?.data?.size
                            val context = LocalContext.current
                            val isSvg =
                                remember(imageUrl) { imageUrl.contains(".svg", ignoreCase = true) }
                            val imageRequest = remember(imageUrl) {
                                if (isSvg) {
                                    ImageRequest.Builder(context)
                                        .data(imageUrl)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .size(Size.ORIGINAL)
                                        .build()
                                } else {
                                    ImageRequest.Builder(context)
                                        .data(imageUrl)
                                        .crossfade(true)
                                        .placeholder(R.drawable.app_logo)
                                        .build()
                                }
                            }
                            val painter = rememberImagePainter(request = imageRequest)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                                    .background(
                                        color = grayD9,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SpaceBetweenText(
                                    image = painter,
                                    showImage = true,
                                    showText = false,
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 5.dp,
                                    text = "$totalInvoices Offer"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GstInvoiceLoanOfferScreenPreview() {
    GstInvoiceLoanOfferScreen(
        navController = rememberNavController(),
        offerResponse = "",
        fromFlow = "INVOICE_BASED_LOAN"
    )
}
