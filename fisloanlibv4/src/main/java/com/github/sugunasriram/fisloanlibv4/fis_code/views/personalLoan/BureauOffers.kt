package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ImageTextButtonRow
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoaderAnimation
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToAccountAggregatorScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanOffersListDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.OfferResponseItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchModel
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.SearchResponseModel
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorBlue
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.failureRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.WebViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.MiddleOfTheLoanScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.NoLoanOffersAvailableScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.views.webview.NavigateToWebView
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.Locale
import java.util.logging.Filter

private val json = Json { prettyPrint = true }
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceType")
@Composable
fun BureauOffersScreen(navController: NavHostController, loanPurpose: String,
                       fromFlow: String,withoutAAResponse : String) {

    val context = LocalContext.current
    val webViewModel: WebViewModel = viewModel()
    if(loanPurpose == stringResource(R.string.getUerFlow)) {
        json.decodeFromString(SearchModel.serializer(), withoutAAResponse)
            ?.takeIf { it.data != null }?.let {
                webViewModel.updateSearchResponse(it)
            }
    }

    BackHandler { navigateApplyByCategoryScreen(navController) }
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
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController)
        searchFailed -> NoLoanOffersAvailableScreen(navController)
        errorMessage.isNotEmpty() -> CommonMethods().ShowMiddleLoanErrorScreen(
            navController,
            errorMessage
        )

        webScreenLoading.value -> LoaderAnimation(
            image = R.raw.we_are_currently_processing_clock,
            updatedImage = R.raw.we_are_currently_processing_hour_glass,
            showTimer = true, navController = navController
        )

        else -> {
            val endUse = if (loanPurpose.equals("Other Consumption Purpose", ignoreCase = true))
                "other"
            else if (loanPurpose.equals("Consumer Durable Purchase", ignoreCase = true))
                "consumerDurablePurchase"
            else loanPurpose.lowercase()

            if (!webScreenLoaded.value) {
                loadWebScreen(
                    fromFlow = fromFlow, webViewModel = webViewModel, context = context,
                    endUse = endUse, purpose = loanPurpose
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
                        ), searchModel = null
                    )
                } else {
                    if (searchResponse?.data?.offerResponse.isNullOrEmpty()) {
                        if (searchResponse?.data?.url != null) {
                            navigateToAccountAggregatorScreen(
                                navController, loanPurpose, fromFlow,
                                searchResponse?.data?.id.toString(),
                                searchResponse?.data?.transactionId.toString(),
                                searchResponse?.data?.url.toString()
                            )
                        } else {
                            CommonMethods().toastMessage(
                                context = context, toastMsg = "Unable to Load WebPage"
                            )
                        }
                    } else {
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

                            }) {
                            FixedTopBottomScreen(
                                navController = navController,
                                topBarBackgroundColor = appOrange,
                                topBarText = stringResource(R.string.bureau_offers),
                                showBackButton = true,
                                onBackClick = { navigateApplyByCategoryScreen(navController) },
                                showBottom = true,
                                showDoubleButton = true,
                                primaryButtonText = stringResource(R.string.get_more_offers),
                                onPrimaryButtonClick = {
                                    navigateToAccountAggregatorScreen(
                                        navController, loanPurpose, fromFlow,
                                        searchResponse?.data?.id.toString(),
                                        searchResponse?.data?.transactionId.toString(),
                                        searchResponse?.data?.url.toString()
                                    )
                                },
                                secondaryButtonText = stringResource(R.string.exit),
                                onSecondaryButtonClick = {
                                    navigateApplyByCategoryScreen(
                                        navController
                                    )
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
                                    searchResponse?.data?.offerResponse,
                                    bureauOffersSearchQuery, selectedFilter
                                )

                                filteredOffers.forEachIndexed { index, offer ->
                                    OfferCard(navController, offer, fromFlow, index)
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
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

            offer.quoteBreakUp?.forEach { quote ->
                if (quote?.title?.lowercase(Locale.ROOT)?.contains("principal") == true) {
                    loanAmount = quote.value ?: "-"
                }
            }

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
                    Color(0xFF4CAF50), // green
                    Color(0xFF81C784), // light green
                    Color(0xFFB2FF59), // lime
                )
                1 -> listOf(
                    Color(0xFFFF8A65), // light red-orange
                    Color(0xFFFF7043), // warm red
                    Color(0xFFD84315), // deep red-orange
                )
                2 -> listOf(
                    Color(0xFF64B5F6), // light blue
                    Color(0xFF42A5F5), // blue
                    Color(0xFF1976D2), // deep blue
                )

                3 -> listOf(
                    Color(0xFFFF8A80), // light red
                    Color(0xFFFF5252), // red
                    Color(0xFF800000), // deep red
                )
                else -> listOf(
                    Color(0xFFF3D5B5), // green
                    Color(0xFF8B5E34), // light green
                    Color(0xFF533101), // lime
                )

            }


            FullWidthRoundShapedCard(
                onClick = {
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
                cardColor = appOrange, //Sugu
                gradientColors = gradientColors,
                bottomPadding = 15.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = logoUrl ?: R.drawable.bank_icon)
                        .apply {
                            crossfade(true)
                            placeholder(R.drawable.bank_icon)
                        }.build()
                )

                ImageTextButtonRow(
                    imagePainter = painter,
                    textHeader = bankName,
                    textColor = appBlack,
                    textStyle = normal14Text700,
                    buttonText = stringResource(id = R.string.more_details),
                    buttonTextStyle = normal12Text400,
                    onButtonClick = {
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
                )

                Row {
                    HeaderWithValue(
                        textHeader = stringResource(id = R.string.max_loan_amount),
                        textValue = loanAmount,
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
                    HeaderWithValue(
                        textHeader = stringResource(id = R.string.tenure),
                        textValue = tenure,
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


@Preview(showBackground = true)
@Composable
fun PreviewBureauOffers() {
    val navController = rememberNavController()
//    OfferCard(navController)
//    BureauOffersScreen(navController = navController, loanPurpose = "Education",
//        fromFlow = "Personal Loan")
}