package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CurvedPrimaryButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CustomModalBottomSheet
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.SpaceBetweenText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TextInputLayout
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.personaLoan.Offer
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold20Text100
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.AnnualIncomeViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.personalLoan.LoanAgreementViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.math.roundToInt

private val json1 = Json { prettyPrint = true }
private var loanAmountVal = ""

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoanOffersListScreen(navController: NavHostController, offerItem: String, fromFlow: String) {
    val tabs = listOf("AA Offers", "Bureau Offers")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var aAOffersSearchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val filterOptionBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    BackHandler { navigateApplyByCategoryScreen(navController) }

    val loanAgreementViewModel: LoanAgreementViewModel = viewModel()
    val offerList by loanAgreementViewModel.offerList.collectAsState()
    val offerListLoaded by loanAgreementViewModel.offerListLoaded.collectAsState()
    val offerListLoading by loanAgreementViewModel.offerListLoading.collectAsState()

    val navigationToSignIn by loanAgreementViewModel.navigationToSignIn.collectAsState()

    if (navigationToSignIn) {
        navigateSignInPage (navController)
    }
    else if (offerListLoading) {
        CenterProgress()
    } else {
        if (offerListLoaded) {
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
                                    .background(color = appWhite, shape = RoundedCornerShape(5.dp))
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
                            SearchBar(
                                searchValue = aAOffersSearchQuery,
                                onSearchQueryChanged = { aAOffersSearchQuery = it },
                                onFilterClick = {
                                    coroutineScope.launch {
                                        filterOptionBottomSheetState.show() // ✅ Show filter bottom sheet
                                    }
                                }
                            )
//                            val filteredOffers = filterOffers(offerList?.data, aAOffersSearchQuery)
                            val filteredOffers = filterOffers(offerList?.data, aAOffersSearchQuery, selectedFilter)
                            filteredOffers.forEachIndexed { index, offer ->
                                OfferCard(navController, offer, fromFlow, index)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        1 -> {
                            RegisterText("---- Coming your way soon!----", style = normal20Text400, top = 30.dp)
                        }
                    }
                }
            }
        } else {
            loanAgreementViewModel.offerList(loanType = "PERSONAL_LOAN", context = context)
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
    return when (sortOption) {
        "Highest Loan Amount" -> filtered.sortedByDescending {
            val rawAmount = it.offer?.quoteBreakUp?.firstOrNull {
                it?.title?.contains("principal", ignoreCase = true) == true
            }?.value.orEmpty()
            rawAmount.replace(",", "").toDoubleOrNull() ?: 0.0
        }
        "Lowest Interest" -> filtered.sortedBy {
            getTagValue(it, "interest_rate")?.replace("%", "")?.toDoubleOrNull() ?: Double.MAX_VALUE
        }
        "Lowest Tenure" -> filtered.sortedBy {
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
fun SearchBar(searchValue: String, placeHolderText:String=stringResource(id = R.string.search_for_amount_and_bank),
              isFilterNeeded:Boolean=true,
              onSearchQueryChanged: (String) -> Unit,
              onFilterClick: () -> Unit = {}
){
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
                        modifier = Modifier .padding(end = 8.dp).clickable { onSearchQueryChanged("") }
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

        if(isFilterNeeded)
        Image(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = stringResource(id = R.string.filters),
            modifier = Modifier.size(40.dp).padding(start = 5.dp).clickable { onFilterClick() }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterModalContent(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    context: Context,
    selectedFilter: String?,
    onFilterSelected: (String?) -> Unit
) {

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

        val options = listOf("Lowest Interest", "Lowest Tenure", "Highest Loan Amount")
        Column(
            modifier = Modifier.fillMaxWidth() .padding(horizontal = 10.dp),
        ) {
            options.forEach { option ->
                CheckBoxText(
                    style = normal14Text500,
                    boxState = selectedFilter == option,
                    contentArrangement = Arrangement.Start,
                    text = option,
                    bottom = 0.dp,
                    start = 0.dp,
                    end = 0.dp,
                    top = 0.dp
                ) { isChecked ->
                    onFilterSelected(if (isChecked) option else null)
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
                start = 35.dp, end = 35.dp, style = normal14Text700
            ) {
                onFilterSelected(null)
            }

            CurvedPrimaryButton(
                text = stringResource(id = R.string.apply),
                start = 35.dp, end = 35.dp, style = normal14Text700
            ) {
                onFilterSelected(selectedFilter)
                coroutineScope.launch { bottomSheetState.hide() }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLoanOfferListScreen() {
    LoanOffersListScreen(rememberNavController(),"","")
}