package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.OffersWithRejections
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.LoanAgreementViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.NoExistingLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.RejectedOfferCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json1 = Json { prettyPrint = true }
private var loanAmountVal = ""

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoanOffersListScreen(navController: NavHostController, offerItem: String, fromFlow: String) {
    val tabs = listOf("Bureau Offers", "AA Offers")
    var selectedTabIndex by remember { mutableIntStateOf(1) }

    var aAOffersSearchQuery by remember { mutableStateOf("") }
    var bureauOffersSearchQuery by remember { mutableStateOf("") }

    // Instead of separate filters, use a single list of filters
    val selectedFilters = remember { mutableStateListOf<String?>(null, null) }

    val json = Json { ignoreUnknownKeys = true }
    val offerWithRejected = remember(offerItem) {
        json.decodeFromString<OffersWithRejections>(offerItem)
    }
    val offerList = offerWithRejected.offers
    val rejectedLenders = offerWithRejected.rejectedLenders

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val filterOptionBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    BackHandler { navigateApplyByCategoryScreen(navController) }

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val navigationToSignIn by loanAgreementViewModel.navigationToSignIn.collectAsState()

    if (navigationToSignIn) {
        navigateSignInPage(navController)
    } else {
        val aaOffers = offerList?.filter { it.bureauConsent == false }
        val bureauOffers = offerList?.filter { it.bureauConsent == true }

        val filteredAaOffers = filterOffers(aaOffers, aAOffersSearchQuery, selectedFilters[1])
        val filteredBureauOffers = filterOffers(bureauOffers, bureauOffersSearchQuery, selectedFilters[0])

        CustomModalBottomSheet(
            bottomSheetState = filterOptionBottomSheetState,
            sheetContent = {
                FilterModalContent(
                    bottomSheetState = filterOptionBottomSheetState,
                    coroutineScope = coroutineScope,
                    context = context,
                    selectedFilter = selectedFilters[selectedTabIndex],
                    onFilterSelected = { selectedFilters[selectedTabIndex] = it },
                    selectedTabIndex = selectedTabIndex
                )
            }
        ) {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.loan_offer),
                showBackButton = true,
                backgroundColor = appWhite,
                onBackClick = { navigateApplyByCategoryScreen(navController) }
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = appWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(3.dp),
                            color = appOrange
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
                                .background(appWhite, RoundedCornerShape(5.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = title,
                                color = if (isSelected) appOrange else checkBoxGray,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                when (selectedTabIndex) {
                    0 -> {
                        if (filteredBureauOffers.isEmpty()) {
                            Spacer(modifier = Modifier.height(150.dp))
                            NoExistingLoanScreen(displayText = stringResource(R.string.no_existing_lenders))
                        } else {
                            SearchBar(
                                searchValue = bureauOffersSearchQuery,
                                onSearchQueryChanged = { bureauOffersSearchQuery = it },
                                onFilterClick = {
                                    coroutineScope.launch { filterOptionBottomSheetState.show() }
                                }
                            )
                            filteredBureauOffers.forEachIndexed { index, offer ->
                                OfferCard(navController, offer, fromFlow, index)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                          rejectedLenders?.chunked(2)
                                ?.forEachIndexed { chunkIndex, lenderPair ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 4.dp),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        lenderPair.forEachIndexed { indexInPair, lender ->
                                            RejectedOfferCard(
                                                navController = navController,
                                                lender = lender,
                                                index = chunkIndex * 2 + indexInPair,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }

                                        // If this row has only one lender (i.e., odd count), add spacer for alignment
                                        if (lenderPair.size == 1 && (rejectedLenders.size) > 1
                                        ) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                        }
                    }

                    1 -> {
                        if (filteredAaOffers.isEmpty()) {
                            Spacer(modifier = Modifier.height(150.dp))
                            NoExistingLoanScreen(displayText = stringResource(R.string.no_existing_offers))
                        } else {
                            SearchBar(
                                searchValue = aAOffersSearchQuery,
                                onSearchQueryChanged = { aAOffersSearchQuery = it },
                                onFilterClick = {
                                    coroutineScope.launch { filterOptionBottomSheetState.show() }
                                }
                            )
                            filteredAaOffers.forEachIndexed { index, offer ->
                                OfferCard(navController, offer, fromFlow, index)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            rejectedLenders?.chunked(2)
                            ?.forEachIndexed { chunkIndex, lenderPair ->
                                val isSingleItem = lenderPair.size == 1

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp, vertical = 4.dp),
                                    horizontalArrangement = if (isSingleItem) Arrangement.Center else Arrangement.spacedBy(6.dp)
                                ) {
                                    lenderPair.forEachIndexed { indexInPair, lender ->
                                        RejectedOfferCard(
                                            navController = navController,
                                            lender = lender,
                                            index = chunkIndex * 2 + indexInPair,
                                            modifier = Modifier.weight(if (isSingleItem) 0.8f else 1f) // slightly narrower if centered
                                        )
                                    }
                                    if (!isSingleItem && lenderPair.size == 1) {
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

fun filterOffers(
    offers: List<Offer?>?,
    query: String,
    sortOption: String?
): List<Offer> {
    if (offers == null) return emptyList()

    val trimmedQuery = query.trim().lowercase()
    val queryAsNumber = trimmedQuery.toLongOrNull()

    val filtered = offers.filterNotNull().filter { offer ->
        val bankName = offer.offer?.providerDescriptor?.name.orEmpty().lowercase()

        val loanAmountRaw = offer.offer?.quoteBreakUp?.firstOrNull {
            it?.title?.contains("principal", ignoreCase = true) == true
        }?.value ?: ""

        val offerAmount = loanAmountRaw.replace(",", "").toDoubleOrNull()?.toLong() ?: 0L

        val bankMatch = bankName.contains(trimmedQuery)
        val amountMatch = queryAsNumber?.let {
            offerAmount.toString().startsWith(it.toString())
        } ?: false

        bankMatch || amountMatch
    }
    return when (sortOption?.lowercase()) {
        "highest loan amount" -> filtered.sortedByDescending {
            val rawAmount = it.offer?.quoteBreakUp?.firstOrNull {
                it?.title?.contains("principal", ignoreCase = true) == true
            }?.value.orEmpty()
            rawAmount.replace(",", "").toDoubleOrNull() ?: 0.0
        }
        "lowest interest" -> filtered.sortedBy {
            getTagValue(it, "interest_rate")?.replace("%", "")?.toDoubleOrNull() ?: Double.MAX_VALUE
        }
        "lowest tenure" -> filtered.sortedBy {
            getTagValue(it, "term")
                ?.replace("[^\\d]", "")
                ?.toIntOrNull() ?: Int.MAX_VALUE
        }
        else -> filtered
    }
}

fun getTagValue(offer: Offer, key: String): String? {
    return offer.offer?.itemTags
        ?.flatMap { it?.tags ?: emptyList() }
        ?.firstOrNull {
            it?.key.equals(key, ignoreCase = true) ||
                it?.key.equals(key.replace("_", " "), ignoreCase = true)
        }?.value
        ?.filter { it.isDigit() || it == '.' }
}

@Composable
fun SearchBar(
    searchValue: String,
    placeHolderText: String = stringResource(id = R.string.search_for_amount_and_bank),
    isFilterNeeded: Boolean = true,
    onSearchQueryChanged: (String) -> Unit,
    onFilterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(58.dp)
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 8.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(appWhite, shape = RoundedCornerShape(16.dp)),
            value = searchValue,
            singleLine = true,
            placeholder = { Text(placeHolderText, textAlign = TextAlign.Center, style = normal16Text500) },
            onValueChange = { onSearchQueryChanged(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            },
            trailingIcon = {
                if (searchValue.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.clear),
                        modifier = Modifier.padding(end = 8.dp).clickable { onSearchQueryChanged("") }
                    )
                }
            },
            textStyle = normal16Text500,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = backgroundOrange,
                cursorColor = cursorColor,
                errorBorderColor = errorRed
            )
        )

        if (isFilterNeeded) {
            Image(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = stringResource(id = R.string.filters),
                modifier = Modifier.size(40.dp).padding(start = 5.dp).clickable { onFilterClick() }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterModalContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    context: Context,
    selectedFilter: String?,
    onFilterSelected: (String?) -> Unit,
    selectedTabIndex: Int = 0
) {
    var selectedOption by remember { mutableStateOf(selectedFilter) }
    LaunchedEffect(selectedFilter) {
        selectedOption = selectedFilter
    }
    val options = when (selectedTabIndex) {
        0 -> listOf("Lowest Interest", "Lowest Tenure", "Highest Loan Amount")
        1 -> listOf("Lowest interest", "Lowest tenure", "Highest loan amount")
        else -> emptyList()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(shape = RoundedCornerShape(40.dp), color = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.filters),
                style = bold20Text100,
                color = appBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.9f)
            )
            Icon(
                painter = painterResource(id = R.drawable.close_icon),
                contentDescription = stringResource(id = R.string.bottom_sheet_close),
                modifier = Modifier.weight(0.1f)
                    .padding(end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .clickable {
                        coroutineScope.launch { bottomSheetState.hide() }
                    }
            )
        }

        HorizontalDivider(top = 3.dp, color = backgroundOrange, start = 3.dp, end = 3.dp)
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        ) {
            options.forEach { option ->
                CheckBoxText(
                    style = normal14Text500,
                    boxState = selectedOption == option,
                    contentArrangement = Arrangement.Start,
                    text = option,
                    bottom = 0.dp,
                    start = 0.dp,
                    end = 0.dp,
                    top = 0.dp
                ) { isChecked ->
                    selectedOption = if (isChecked) option else null
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp).background(appOrange))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            CurvedPrimaryButton(
                text = stringResource(id = R.string.reset),
                textColor = appOrange,
                backgroundColor = appWhite,
                start = 35.dp,
                end = 35.dp,
                style = normal14Text700
            ) {
                selectedOption = null
            }

            CurvedPrimaryButton(
                text = stringResource(id = R.string.apply),
                start = 35.dp,
                end = 35.dp,
                style = normal14Text700
            ) {
                onFilterSelected(selectedOption)
                coroutineScope.launch { bottomSheetState.hide() }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoanOfferListScreen() {
    LoanOffersListScreen(rememberNavController(), "", "")
}
