package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableLoanStatusCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ContinueText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HeaderNextRowValue
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanOffersListDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.OfferResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import kotlinx.serialization.json.Json

private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

//@Composable
//fun GstLoanOfferListScreen(
//    navController: NavHostController, transactionId: String, offerResponse: String, fromFlow: String
//) {
//    BackHandler {
//        navigateToLoanProcessScreen(
//            navController = navController, transactionId = transactionId, statusId = 12,
//            responseItem =
//            offerResponse,
//            offerId = "1234", fromFlow = "Invoice Loan"
//        )
//    }
//    val offer = json.decodeFromString(GstData.serializer(), offerResponse)
//
//    FixedTopBottomScreen(
//        navController = navController, onBackClick = { navController.popBackStack() },
//        showBottom = false, onClick = {}
//    ) {
//        StartingText(
//            text = stringResource(id = R.string.select_offer), textColor = appBlueTitle,
//            start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp, style = normal32Text700
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//
//        StartingText(
//            text = stringResource(id = R.string.select_invoice_from_offer_list),
//            start = 30.dp, end = 30.dp, bottom = 5.dp, style = normal14Text400,
//            textAlign = TextAlign.Start,
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        offer.offerResponse?.forEach { offerResponse ->
//            offerResponse.itemTags?.forEach { itemTag ->
//                itemTag.tags?.invoiceNumber?.let { invoiceNumber ->
//                    ContinueText(
//                        startText = stringResource(id = R.string.invoice_number),
//                        endText = invoiceNumber, start = 30.dp, end = 30.dp
//                    )
//                }
//                itemTag.tags?.principal?.let { principalAmount ->
//                    ContinueText(
//                        startText = stringResource(id = R.string.invoice_amount),
//                        endText = "$principalAmount (INR)", start = 30.dp, end = 30.dp, top = 5.dp
//                    )
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        LoadDetailsCardInfo(
//            offer = offer, navController = navController, fromFlow = fromFlow
//        )
//    }
//}

@Composable
fun GstLoanOfferListScreen(
    navController: NavHostController, transactionId: String, offerResponse: String, fromFlow: String
) {
    BackHandler {
        navigateToLoanProcessScreen(
            navController = navController, transactionId = transactionId, statusId = 12,
            responseItem =
            offerResponse,
            offerId = "1234", fromFlow = "Invoice Loan"
        )
    }
//    val offer = json.decodeFromString(GstData.serializer(), offerResponse)
    val offer = json.decodeFromString(OfferResponse.serializer(), offerResponse)

    FixedTopBottomScreen(
        navController = navController, onBackClick = { navController.popBackStack() },
        showBottom = false, onPrimaryButtonClick = {}
    ) {
        StartingText(
            text = stringResource(id = R.string.select_offer), textColor = appBlueTitle,
            start = 30.dp, end = 30.dp, top = 10.dp, bottom = 5.dp, style = normal32Text700
        )

        Spacer(modifier = Modifier.height(10.dp))


        StartingText(
            text = stringResource(id = R.string.select_invoice_from_offer_list),
            start = 30.dp, end = 30.dp, bottom = 5.dp, style = normal14Text400,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(20.dp))

//        offer.offerResponse?.forEach { offerResponse ->
            offer.itemTags?.forEach { itemTag ->
                itemTag.tags?.invoiceNumber?.let { invoiceNumber ->
                    ContinueText(
                        startText = stringResource(id = R.string.invoice_number),
                        endText = invoiceNumber, start = 30.dp, end = 30.dp
                    )
                }
                itemTag.tags?.principal?.let { principalAmount ->
                    ContinueText(
                        startText = stringResource(id = R.string.invoice_amount),
                        endText = "$principalAmount (INR)", start = 30.dp, end = 30.dp, top = 5.dp
                    )
                }
            }
//        }
        Spacer(modifier = Modifier.height(20.dp))
        LoadDetailsCardInfo(
            offer = offer, navController = navController, fromFlow = fromFlow
        )
    }
}


@Composable
//fun LoadDetailsCardInfo(offer: GstData, navController: NavHostController, fromFlow: String) {
fun LoadDetailsCardInfo(offer: OfferResponse, navController: NavHostController, fromFlow: String) {
//    offer.offerResponse?.let {
//        it.forEach { offerResponse ->
    offer.let { offerResponse ->
            ClickableLoanStatusCard(
                cardColor = Color.White, borderColor = appBlueTitle, start = 30.dp, end = 30.dp,
                onClick = {
//                    offer.offerResponse.forEach { offerResponse ->
                        val offerDetail =
                            json.encodeToString(OfferResponse.serializer(), offerResponse)
                        offerResponse.id?.let { id ->
                            navigateToLoanOffersListDetailScreen(
                                navController = navController, responseItem = offerDetail,
                                id = id, showButtonId = "1", fromFlow = fromFlow
                            )
                        }
//                    }
                }) {
                offerResponse.providerDescriptor?.images?.get(0)?.url?.let { imageUrl ->
                    val context = LocalContext.current
                    val isSvg = remember(imageUrl) { imageUrl.contains(".svg", ignoreCase = true) }
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

                    SpaceBetweenText(
                        image = painter, showImage = true, start = 10.dp, end = 10.dp, top = 5.dp
                    )
                }
                LoanCard(offerResponse)

                offerResponse.itemTags?.forEach { itemTag ->
                    itemTag.tags?.principal?.let { principalAmount ->
                        itemTag.tags.term?.let { loanTerm ->
                            Text(
                                text = "Repay Loan (INR) $principalAmount in $loanTerm",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                                    fontWeight = FontWeight(400)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                    .background(color = grayD9, shape = RoundedCornerShape(4.dp))
                                    .padding(5.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
//    }
}

@Composable
fun LoanCard(offer: OfferResponse) {
    Row() {
        offer.itemTags?.forEach { itemTag ->
            /* Loan */
            itemTag.tags?.principal?.let { principalAmount ->
                HeaderNextRowValue(
                    textHeader = stringResource(id = R.string.loan) + " (INR)",
                    textValue = principalAmount, textColorHeader = appBlack,
                    textColorValue = appBlack,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 0.dp, end = 5.dp, bottom = 8.dp)
                        .weight(0.5f)
                )
            }
            /* Interest */
            itemTag.tags?.interest?.let { interest ->
                HeaderNextRowValue(
                    textHeader = stringResource(id = R.string.interest) + " (INR)",
                    textValue = interest, textColorHeader = appBlack,
                    textColorValue = appBlack,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 8.dp)
                        .weight(0.5f)
                )
            }
            /* Duration */
            itemTag.tags?.term?.let { loanTerm ->
                HeaderNextRowValue(
                    textHeader = stringResource(id = R.string.duration), textValue = loanTerm,
                    textColorHeader = appBlack, textColorValue = appBlack,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 8.dp)
                        .weight(0.5f)
                )
            }
        }
    }
}

