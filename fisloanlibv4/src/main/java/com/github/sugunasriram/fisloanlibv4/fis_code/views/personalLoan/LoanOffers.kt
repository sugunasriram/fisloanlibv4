package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToWebViewFlowOneScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstSearchResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.AAConsentDetails
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchResponseModel
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.lightBlue
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal24Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal30Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.KycUrlData
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.webview.NavigateToWebView
import kotlinx.serialization.json.Json

private val json = Json { prettyPrint = true }

@SuppressLint("ResourceType")
@Composable
fun LoanOffers(navController: NavHostController, purpose: String, fromFlow: String, withoutAAResponse: String) {
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

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
    val webViewModel: WebViewModel = viewModel()

    // without AA GetUserStatus Response
    if (purpose == stringResource(R.string.getUerFlow)) {
        json.decodeFromString(SearchModel.serializer(), withoutAAResponse)
            ?.takeIf { it.data != null }?.let {
                webViewModel.updateSearchResponse(it)
            }
    }

    val webScreenLoading = webViewModel.webProgress.collectAsState()
    val webScreenLoaded = webViewModel.webViewLoaded.collectAsState()
    val searchResponse by webViewModel.searchResponse.collectAsState()
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val searchFailed by webViewModel.searchFailed.collectAsState()

    val gstSearchResponse by webViewModel.gstSearchResponse.collectAsState()

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val navigationToSignIn by loanAgreementViewModel.navigationToSignIn.collectAsState()
    val errorMessage by webViewModel.errorMessage.collectAsState()

    when {
//        searchFailed -> NoLoanOffersFoundScreen(navController)
        errorMessage.isNotEmpty() || middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController, errorMessage)
        navigationToSignIn -> navigateSignInPage(navController)
        webScreenLoading.value -> SearchLoaderAnimation(navController)
        else -> LoanOffer(
            purpose,
            webScreenLoaded,
            fromFlow,
            webViewModel,
            context,
            gstSearchResponse,
            navController,
            searchResponse,
            backPressedTime
        )
    }
}

@Composable
private fun LoanOffer(
    purpose: String,
    webScreenLoaded: State<Boolean>,
    fromFlow: String,
    webViewModel: WebViewModel,
    context: Context,
    gstSearchResponse: GstSearchResponse?,
    navController: NavHostController,
    searchResponse: SearchModel?,
    backPressedTime: Long
) {
    var backPressedTime1 = backPressedTime
    val endUse = if (purpose.equals("Other Consumption Purpose", ignoreCase = true)) {
        "other"
    } else if (purpose.equals("Consumer Durable Purchase", ignoreCase = true)) {
        "consumerDurablePurchase"
    } else {
        purpose.lowercase()
    }

    if (!webScreenLoaded.value) {
        loadWebScreen(
            fromFlow = fromFlow,
            webViewModel = webViewModel,
            context = context,
            endUse = endUse,
            purpose = purpose
        )
    } else {
        if (gstSearchResponse?.data?.url != null) {
            NavigateToWebView(
                gstSearchResponse = gstSearchResponse,
                fromFlow = fromFlow,
                navController = navController,
                searchResponse = SearchModel(
                    status = true,
                    statusCode = 200,
                    data = SearchResponseModel(
                        id = gstSearchResponse?.data?.id,
                        url = gstSearchResponse?.data?.url,
                        transactionId = gstSearchResponse?.data?.transactionId,
                        offerResponse = null,
                        offers = null,
                        consentResponse = null
                    )
                ),
                searchModel = null
            )
        } else {
            if (searchResponse?.data?.offerResponse.isNullOrEmpty()) {
                Log.d("LoanOffers", "From : $fromFlow")
                Log.d("LoanOffers", "searchResponse : $searchResponse")
                Log.d("LoanOffers", "searchResponse Data: ${searchResponse?.data}")
                Log.d("LoanOffers", "searchResponse id: ${searchResponse?.data?.id}")
                if (searchResponse?.data?.url != null) {
                    navigateToWebViewFlowOneScreen(
                        navController,
                        purpose,
                        fromFlow,
                        searchResponse?.data?.id.toString(),
                        searchResponse?.data?.transactionId.toString(),
                        searchResponse?.data?.url.toString()
                    )
                } else {
                    CommonMethods().toastMessage(
                        context = context,
                        toastMsg = "Unable to Load WebPage"
                    )
                }
            } else {
                FixedTopBottomScreen(
                    navController = navController,
                    isSelfScrollable = false,
                    primaryButtonText = stringResource(
                        id = if (!searchResponse?.data?.url.isNullOrEmpty() && !searchResponse?.data?.consentResponse.isNullOrEmpty()) {
                            R.string.give_aa_consent
                        } else {
                            R.string.next
                        }
                    ),
                    onPrimaryButtonClick = {
                        // Sugu2

                        val json = Json { prettyPrint = true; ignoreUnknownKeys = true }
                        val kycUrlData =
                            json.encodeToString(
                                KycUrlData.serializer(),
                                KycUrlData(
                                    searchResponse?.data?.id.toString(),
                                    searchResponse?.data?.url.toString(),
                                    searchResponse?.data?.transactionId.toString()
                                )
                            )
                        navigateToAccountAggregatorScreen(
                            navController,
                            loanPurpose = kycUrlData,
                            fromFlow,
                            searchResponse?.data?.id.toString(),
                            searchResponse?.data?.url.toString(),
                            searchResponse?.data?.transactionId.toString(),
                            false
                        )

//                        if (searchResponse?.data?.url != null && searchResponse?.data?.consentResponse != null) {
//                            navigateToWebViewFlowOneScreen(
//                                navController,
//                                purpose,
//                                fromFlow,
//                                searchResponse?.data?.id.toString(),
//                                searchResponse?.data?.transactionId.toString(),
//                                searchResponse?.data?.url.toString()
//                            )
//                        } else {
//                            navigateToLoanProcessScreen(
//                                navController,
//                                transactionId = searchResponse?.data?.transactionId.toString(),
//                                statusId = 2,
//                                responseItem = "No Need Response Item",
//                                offerId = "1234",
//                                fromFlow = fromFlow
//                            )
//                        }
                    },
                    onBackClick = {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - backPressedTime1 < 2000) {
                            navigateApplyByCategoryScreen(navController)
                        } else {
                            CommonMethods().toastMessage(
                                context = context,
                                toastMsg = "Press back again to go to the Home page"
                            )
                            backPressedTime1 = currentTime
                        }
                    }
                ) {
                    StartingText(
                        text = stringResource(id = R.string.loan_offers),
                        textColor = appBlueTitle,
                        alignment = Alignment.Center,
                        start = 30.dp,
                        end = 30.dp,
                        top = 10.dp,
                        style = normal30Text700
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(440.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        searchResponse?.data?.offerResponse?.forEachIndexed { index, offer ->
                            OfferCard(navController, offer, fromFlow, index)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    if (!searchResponse?.data?.url.isNullOrEmpty() &&
                        !searchResponse?.data?.consentResponse.isNullOrEmpty()
                    ) {
                        ConsentCard(searchResponse?.data?.consentResponse)
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
private fun SearchLoaderAnimation(navController: NavHostController) {
    LoaderAnimation(
        image = R.raw.we_are_currently_processing_hour_glass,
        updatedImage = R.raw.we_are_currently_processing_clock,
        delayInMillis = 60000,
        showTimer = true,
        navController = navController
    )
}

@Composable
fun ConsentCard(consentResponse: List<AAConsentDetails?>?) {
    Card(
        modifier = Modifier
            .height(190.dp)
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp),
        elevation = 15.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegisterText(
                text = stringResource(id = R.string.get_more_offers),
                textColor = appBlack,
                style = normal24Text700,
                top = 8.dp
            )
            StartingText(
                text = stringResource(id = R.string.get_offers_from_following_lenders___),
                style = normal12Text400,
                alignment = Alignment.Center
            )
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                consentResponse?.forEach { details ->
                    if (details != null) {
                        ConsentCardDetails(
                            details.name ?: "Unknown Bank",
                            details.image,
                            details.min_interest_rate,
                            details.max_interest_rate
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConsentCardDetails(
    bankName: String,
    imageUrl: String?,
    minInterest: String?,
    maxInterest: String?
) {
    val painter = if (imageUrl.isNullOrEmpty()) {
        painterResource(id = R.drawable.bank_icon)
    } else {
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build()
        )
    }

    val interestRate = formatInterestRate(minInterest, maxInterest)
    Card(
        modifier = Modifier
            .width(100.dp)
            .padding(10.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = lightBlue
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bankName,
                style = normal12Text400,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(80.dp)
            )
            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.bank_image),
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = interestRate,
                style = normal12Text400,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(80.dp)
            )
        }
    }
}

fun loadWebScreen(
    fromFlow: String,
    webViewModel: WebViewModel,
    context: Context,
    endUse: String,
    purpose: String
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        webViewModel.searchApi(
            context = context,
            searchBodyModel = SearchBodyModel(
                loanType = "PERSONAL_LOAN",
                endUse = endUse,
                bureauConsent = "on"
            )
        )
    } else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
        // Here when it in the Gst Invoice Loan purpose equals to Invoice ID
        webViewModel.searchGst(
            gstSearchBody = GstSearchBody(
                loanType = "INVOICE_BASED_LOAN",
                bureauConsent = "on",
                tnc = "on",
                id = purpose
            ),
            context = context
        )
    } else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        webViewModel.financeSearch(
            financeSearchModel = FinanceSearchModel(
                loanType = "PURCHASE_FINANCE", bureauConsent = "on", tnc = "on", endUse = "travel",
                downpayment = "200", merchantGst = "24AAHFC3011G1Z4", merchantPan = "EGBQA2212D",
                isFinancing = "on", merchantBankAccountNumber = "639695357641006",
                merchantIfscCode = "XRSY0YPV5SW", merchantBankAccountHolderName = "mohan",
                productCategory = "fashion", productBrand = "style", productSKUID = "12345678",
                productPrice = "1000"
            ),
            context = context
        )
    }
}
fun formatInterestRate(minRate: String?, maxRate: String?): String {
    val minInterestRaw = minRate?.replace("%", "")
    val maxInterestRaw = maxRate?.replace("%", "")

    val formattedMinInterest = minInterestRaw?.toDoubleOrNull()?.toBigDecimal()
        ?.stripTrailingZeros()?.toPlainString() ?: minInterestRaw
    val formattedMaxInterest = maxInterestRaw?.toDoubleOrNull()?.toBigDecimal()
        ?.stripTrailingZeros()?.toPlainString() ?: maxInterestRaw

    return "$formattedMinInterest% - $formattedMaxInterest%"
}
