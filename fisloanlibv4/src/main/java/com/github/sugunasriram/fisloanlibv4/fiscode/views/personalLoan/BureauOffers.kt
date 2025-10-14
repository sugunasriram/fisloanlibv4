package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.WrapBorderButton
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanOffersListDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.finance.FinanceSearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.gst.GstSearchBody
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.RejectedLenders
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchBodyModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.SearchResponseModel
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayBackground
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayD6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.LoadingOfferCard
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoLoanOffersAvailableScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.RejectedOfferCard
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.RequestTimeOutScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.webview.NavigateToWebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.Locale
import androidx.compose.material.Card
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen

private val json = Json { prettyPrint = true }

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BureauOffersScreen(
    navController: NavHostController,
    loanPurpose: String,
    fromFlow: String,
    withoutAAResponse: String
) {
    val context = LocalContext.current
    val webViewModel: WebViewModel = viewModel()
    // decode once, remember
    val decodedWithoutAAResponse = remember(withoutAAResponse) {
        try {
            json.decodeFromString(SearchModel.serializer(), withoutAAResponse)
        } catch (e: Exception) {
            null
        }
    }

    if (loanPurpose == stringResource(R.string.getUerFlow)) {
        decodedWithoutAAResponse?.takeIf { it.data != null }?.let {
            webViewModel.updateSearchResponse(it)
        }
    } else if (loanPurpose == "formSubmission") {
        decodedWithoutAAResponse?.takeIf { it.data != null }?.let {
            webViewModel.updateSearchResponse(it)
        }
    }

//    BackHandler { navigateApplyByCategoryScreen(navController) }
    BackHandler { navigateToFISExitScreen(navController, loanId="1234") }

    val webScreenLoading = webViewModel.webProgress.collectAsState()
    val webScreenLoaded = webViewModel.webViewLoaded.collectAsState()
    val searchResponse by webViewModel.searchResponse.collectAsState()
    val errorMessage by webViewModel.errorMessage.collectAsState()
    val searchFailed by webViewModel.searchFailed.collectAsState()
    val middleLoan by webViewModel.middleLoan.observeAsState(false)
    val showInternetScreen by webViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by webViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by webViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by webViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by webViewModel.unAuthorizedUser.observeAsState(false)
    val gstSearchResponse by webViewModel.gstSearchResponse.collectAsState()

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val navigationToSignIn by loanAgreementViewModel.navigationToSignIn.collectAsState()
    var bureauOffersSearchQuery by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    val filterOptionBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    // use decodedWithoutAAResponse if loanPurpose matches
    val effectiveSearchResponse = if (loanPurpose == stringResource(R.string.getUerFlow)) {
        decodedWithoutAAResponse // <-- changed
    } else if (loanPurpose == "formSubmission") {
        decodedWithoutAAResponse // <-- changed
    } else {
        searchResponse // <-- changed
    }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> RequestTimeOutScreen(navController) {
//            navigateApplyByCategoryScreen(navController)
            navigateToFISExitScreen(navController, loanId="4321")
        }

        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage, false)
        searchFailed -> NoLoanOffersAvailableScreen(navController)
        errorMessage.isNotEmpty() -> CommonMethods().ShowMiddleLoanErrorScreen(
            navController,
            errorMessage
        )

        else -> {
            if (gstSearchResponse?.data?.url != null) {
                NavigateToWebView(
                    context = context,
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
            }
            else {
                if (effectiveSearchResponse?.data?.offerResponse.isNullOrEmpty()) {
                    if (effectiveSearchResponse?.data?.url != null) {
                        navigateToAccountAggregatorScreen(
                            navController,
                            loanPurpose,
                            fromFlow,
                            effectiveSearchResponse?.data?.id.toString(),
                            effectiveSearchResponse?.data?.transactionId.toString(),
                            effectiveSearchResponse?.data?.url.toString()
                        )
                    } else {
                        NoLoanOffersAvailableScreen(navController)
                    }
                }
                else {
                    CustomModalBottomSheet(
                        bottomSheetState = filterOptionBottomSheetState,
                        sheetContent = {
                            FilterModalContent(
                                bottomSheetState = filterOptionBottomSheetState,
                                coroutineScope = coroutineScope,
                                context = context,
                                selectedFilter = selectedFilter,
                                onFilterSelected = { selectedFilter = it }
                            )
                        }
                    ) {
                            FixedTopBottomScreen(
                                navController = navController,
                                topBarBackgroundColor = appOrange,
                                topBarText = stringResource(R.string.bureau_offers),
                                showBackButton = true,
//                                onBackClick = { navigateApplyByCategoryScreen(navController) },
                                onBackClick = { navigateToFISExitScreen(navController, loanId="1234")
                                },
                                showBottom = effectiveSearchResponse?.data?.url != null,
                                showGetMoreOffersButton = true,
                                primaryButtonText = stringResource(R.string.get_more_offers),
                                onPrimaryButtonClick = {
                                    if (effectiveSearchResponse?.data?.url != null) {
                                        navigateToAccountAggregatorScreen(
                                            navController,
                                            loanPurpose,
                                            fromFlow,
                                            effectiveSearchResponse.data.id.toString(),
                                            effectiveSearchResponse.data.transactionId.toString(),
                                            effectiveSearchResponse.data.url.toString()
                                        )
                                    } else {
                                        CommonMethods().toastMessage(
                                            context = context,
                                            toastMsg = "Currently no more Offers available"
                                        )
                                    }
                                },
                                backgroundColor = appWhite
                            ) {
                                SearchBar(
                                    searchValue = bureauOffersSearchQuery,
                                    onSearchQueryChanged = { bureauOffersSearchQuery = it },
                                    onFilterClick = {
                                        coroutineScope.launch {
                                            filterOptionBottomSheetState.show()
                                        }
                                    }
                                )
                                val filteredOffers = filterOffers(
                                    effectiveSearchResponse?.data?.offerResponse,
                                    bureauOffersSearchQuery,
                                    selectedFilter
                                )
                                RegisterText(
                                    text = stringResource(R.string.quick_offers),
                                    textColor = appOrange,
                                    style = normal20Text700, top = 8.dp, bottom = 8.dp
                                )

                                filteredOffers.forEachIndexed { index, offer ->
                                    OfferCard(navController, offer, fromFlow, index)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                effectiveSearchResponse?.data?.rejectedLenders
                                    ?.chunked(2)
                                    ?.forEachIndexed { chunkIndex, lenderPair ->

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 4.dp, vertical = 4.dp),
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            lenderPair.forEachIndexed { indexInPair, lender ->
                                                if (lender != null) {
                                                    RejectedOfferCard(
                                                        navController = navController,
                                                        lender = lender,
                                                        index = chunkIndex * 2 + indexInPair,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                            }

                                            // If this row has only one lender (i.e., odd count), add spacer for alignment
                                            if (lenderPair.size == 1 && effectiveSearchResponse.data?.rejectedLenders?.size ?: 0 > 1) {
                                                Spacer(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                            }
                        }
                }
            }
        }
    }
}

fun loadWebScreen(
    fromFlow: String,
    webViewModel: WebViewModel,
    context: Context,
    endUse: String,
    purpose: String,
    downPaymentAmount: String = "",
    pfloanTenure: String = "",
    productPrice: String = "",
    pfMerchantGst:String? = "",
    pfMerchantPan:String? = "",
    pfMerchantBankAccountNumber:String? = "",
    pfMerchantIfscCode:String? = "",
    pfMerchantBankAccountHolderName:String? = "",
    pfProductCategory:String? = "",
    pfProductIMEI:String? = "",
    pfProductBrand:String? = "",
    pfProductSKUID:String? = ""
) {
    if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
        webViewModel.formSubmissionApi(
            context = context,
            searchBodyModel = SearchBodyModel(
                loanType = "PERSONAL_LOAN",
                endUse = endUse,
                bureauConsent = "on"
            )
        )
    }
    else if (fromFlow.equals("Invoice Loan", ignoreCase = true)) {
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
    }
    else if (fromFlow.equals("Purchase Finance", ignoreCase = true)) {
        webViewModel.pFFormSubmissionApi(
            financeSearchModel = FinanceSearchModel(
                loanType = "PURCHASE_FINANCE",
                bureauConsent = "on",
                tnc = "on",
                endUse = "travel",
                downpayment = downPaymentAmount,
                tenure = pfloanTenure,
                merchantGst = pfMerchantGst,
                merchantPan = pfMerchantPan,
                isFinancing = "on",
                merchantBankAccountNumber = pfMerchantBankAccountNumber,
                merchantIfscCode = pfMerchantIfscCode,
                merchantBankAccountHolderName = pfMerchantBankAccountHolderName,
                productCategory = pfProductCategory,
                productBrand = pfProductBrand,
                productSKUID = pfProductSKUID,
                productIMEI = pfProductIMEI,
                productPrice = productPrice
            ),
            context = context
        )
    }
}

@Composable
fun OfferCard(navController: NavHostController, offerResponseItem: Offer?, fromFlow: String, index: Int) {
    offerResponseItem?.let { offerResponse ->
        offerResponse.offer?.let { offer ->
            val json = Json { prettyPrint = true }
            val responseItem = json.encodeToString(OfferResponseItem.serializer(), offer)
            // Extract values from offerResponse
            val bankName = offer.providerDescriptor?.name ?: "Bank"
            val logoUrl = offer.providerDescriptor?.images?.firstOrNull()?.url

            var loanAmount = "-"
            var interestRate = "-"
            var tenure = "-"
            var installmentAmount = "-"
            if (fromFlow.equals("Personal Loan", ignoreCase = true)) {
            offer.quoteBreakUp?.forEach { quote ->
                if (quote?.title?.lowercase(Locale.ROOT)?.contains("principal") == true) {
                    loanAmount = quote.value ?: "-"
                }
            }}
             if (fromFlow.equals("Purchase Finance", ignoreCase = true)){
           offer.itemTags?.forEach { itemTag ->
                itemTag?.tags?.forEach { tag ->
                    if (tag.key.contains("PRINCIPAL_AMOUNT", ignoreCase = true)) {
                        loanAmount = tag.value
                    }
                }
            }}

            offer.itemTags?.forEach { itemTag ->
                itemTag?.tags?.forEach { tag ->
                    when {
                        tag.key.equals("interest_rate", ignoreCase = true) ||
                            tag.key.equals("interest rate", ignoreCase = true) -> {
                            interestRate = tag.value
                        }
                        tag.key.equals("loan_term", ignoreCase = true) ||
                            tag.key.equals("term", ignoreCase = true) -> {
                            tenure = tag.value
                        }
                        tag.key.contains("INSTALLMENT_AMOUNT", ignoreCase = true) -> {
                            installmentAmount = tag.value
                        }
                    }
                }
            }

            val gradientColors = when (index) {
                0 -> listOf(
                    Color(0xFFB2FF59) ,// lime
                    Color(0xFF81C784), // light green
                    Color(0xFF4CAF50), // green
                )
                1 -> listOf(
                    Color(0xFFFFCC80), // light orange
                    Color(0xFFFFA726), // medium orange
                    Color(0xFFF57C00)  // deep orange
                )
                2 -> listOf(
                    Color(0xFF64B5F6), // light blue
                    Color(0xFF42A5F5), // blue
                    Color(0xFF1976D2) // deep blue
                )

                3 -> listOf(
                    Color(0xFFFF8A80), // light red
                    Color(0xFFFF5252), // red
                    Color(0xFF800000) // deep red
                )
                else -> listOf(
                    Color(0xFFF3D5B5), // green
                    Color(0xFF8B5E34), // light green
                    Color(0xFF533101) // lime
                )
            }
            val gradient = Brush.verticalGradient(colors = gradientColors)
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp), clip = false) // gray shadow
            ) {
                Card(
                    modifier = Modifier.clickable {
                            offerResponse.id?.let { id ->
                                navigateToLoanOffersListDetailScreen(
                                    navController = navController,
                                    responseItem = responseItem,
                                    id = id,
                                    showButtonId = "1",
                                    fromFlow = fromFlow
                                )
                            }
                        },
                    elevation = 8.dp,
                    backgroundColor = Color.White
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier.fillMaxWidth().background(appWhite),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                                ) {
                                    val painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(data = logoUrl ?: R.drawable.bank_icon)
                                            .crossfade(true)
                                            .placeholder(R.drawable.bank_icon)
                                            .decoderFactory(SvgDecoder.Factory())
                                            .build()
                                    )

                                    Image(
                                        painter = painter,
                                        contentDescription = "Bank",
                                        modifier = Modifier.size(32.dp).weight(1f)
                                    )

                                    StartingText(
                                        text = bankName,
                                        start = 8.dp,
                                        style = normal14Text700,
                                        textColor = gradientColors.last(),
                                        modifier = Modifier.weight(3f)
                                    )
                                    WrapBorderButton(
                                        text = stringResource(id = R.string.more_details),
                                        modifier = Modifier
                                            .padding(
                                                start = 0.dp,
                                                end = 0.dp,
                                                top = 0.dp,
                                                bottom = 0.dp
                                            )
                                            .weight(2.5f),
                                        style = normal12Text400,
                                        shape = RoundedCornerShape(10.dp),
                                        backgroundColor = gradientColors.last(),
                                        textColor = appWhite
                                    ) {
                                        offerResponse.id?.let { id ->
                                            navigateToLoanOffersListDetailScreen(
                                                navController = navController,
                                                responseItem = responseItem,
                                                id = id,
                                                showButtonId = "1",
                                                fromFlow = fromFlow
                                            )
                                        }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(brush = gradient)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                                ) {
                                    Row {
                                        HeaderWithValue(
                                            textHeader = stringResource(id = R.string.loan_amount_inr),
                                            textValue =  loanAmount,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                        HeaderWithValue(
                                            textHeader = stringResource(id = R.string.rate_of_interest),
                                            textValue = interestRate,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                    }

                                    Spacer(Modifier.height(15.dp))

                                    Row {
                                        //Sugu ToDo
                                        var loanTenure = "-"
                                        if (tenure?.startsWith("P") == true) {
                                            convertISODurationToReadable(
                                                tenure ?: ""
                                            ).let { readableDuration ->
                                                loanTenure = readableDuration
                                            }
                                        }else{
                                            loanTenure= tenure
                                        }
                                        HeaderWithValue(
                                            textHeader = stringResource(id = R.string.tenure),
                                            textValue = loanTenure,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                        HeaderWithValue(
                                            textHeader = stringResource(id = R.string.installment_amount),
                                            textValue = installmentAmount,
                                            modifier = Modifier.weight(0.5f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

