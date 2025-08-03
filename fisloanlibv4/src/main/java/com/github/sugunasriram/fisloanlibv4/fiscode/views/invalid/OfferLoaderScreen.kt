package com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.HeaderWithValue
import com.github.sugunasriram.fisloanlibv4.fiscode.components.IncrementTimer
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderResponseData
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.LenderStatusResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan.RejectedLenders
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.grayA6
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.negativeGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal10Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.rejectedGray
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
fun OfferLoaderScreen(
    showTimer: Boolean = false,
    navController: NavHostController, lenderStatusData:String,
    isBureauOffers: Boolean = true,
) {
    val tabs = listOf("Bureau Offers", "AA Offers")
    var selectedTabIndex by remember { mutableIntStateOf(1) }
    val json = Json { ignoreUnknownKeys = true }
    val lenderList = try {
        json.decodeFromString<LenderStatusResponse>(lenderStatusData).response
    } catch (e: Exception) {
        emptyList()
    }
    BackHandler { navigateApplyByCategoryScreen(navController) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showTimer) {
            IncrementTimer(navController = navController)
        }
        if (isBureauOffers) {
            RegisterText(
                text = stringResource(R.string.quick_offers),
                textColor = appOrange,
                style = normal20Text700, top = 8.dp, bottom = 8.dp
            )
            lenderList?.chunked(2)?.forEachIndexed { chunkIndex, lenderPair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    lenderPair.forEachIndexed { indexInPair, lender ->
                        LoadingOfferCard(
                            navController = navController,
                            lender = lender,
                            index = chunkIndex * 2 + indexInPair,
                            modifier = Modifier.weight(
                                if (lenderPair.size == 1 && indexInPair == 0 && lenderList.size % 2 == 1 && chunkIndex == lenderList.size / 2) {
                                    1f
                                } else if (lenderPair.size == 1) {
                                    1f
                                } else {
                                    1f
                                }
                            )
                        )
                    }

                    if (lenderPair.size == 1 && lenderList.size > 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        if(!isBureauOffers){
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = appWhite,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]).height(3.dp),
                    color = appOrange
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                val isClickable = index == 1
                Tab(
                    selected = isSelected,
                    onClick = { if (isClickable) selectedTabIndex = index },
                    enabled = isClickable,
                    modifier = Modifier
                        .background(appWhite, RoundedCornerShape(5.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = title,
                        color = when {
                            isSelected -> appOrange
                            !isClickable -> checkBoxGray.copy(alpha = 0.4f) // Make Bureau Offers look disabled
                            else -> checkBoxGray
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
        when (selectedTabIndex) {
            0 -> {
                Spacer(modifier = Modifier.height(150.dp))
                NoExistingLoanScreen(displayText = stringResource(R.string.no_existing_lenders))
            }

            1 -> {
                RegisterText(
                    text = stringResource(R.string.generating_best_offers),
                    style = normal20Text700, top = 8.dp
                )
                lenderList?.chunked(2)?.forEachIndexed { chunkIndex, lenderPair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        lenderPair.forEachIndexed { indexInPair, lender ->
                            LoadingOfferCard(
                                navController = navController,
                                lender = lender,
                                index = chunkIndex * 2 + indexInPair,
                                modifier = Modifier.weight(
                                    if (lenderPair.size == 1 && indexInPair == 0 && lenderList.size % 2 == 1 && chunkIndex == lenderList.size / 2) {
                                        1f
                                    } else if (lenderPair.size == 1) {
                                        1f
                                    } else {
                                        1f
                                    }
                                )
                            )
                        }

                        if (lenderPair.size == 1 && lenderList.size > 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
    }
}
@Composable
fun LoadingOfferCard(
    navController: NavHostController,
    lender: LenderResponseData,
    index: Int,
    modifier: Modifier = Modifier
) {
    val gradientColors = when (index % 5) {
        0 -> listOf(Color(0xFF4CAF50), Color(0xFF81C784), Color(0xFFB2FF59))
        1 -> listOf(Color(0xFFFF8A65), Color(0xFFFF7043), Color(0xFFD84315))
        2 -> listOf(Color(0xFF64B5F6), Color(0xFF42A5F5), Color(0xFF1976D2))
        3 -> listOf(Color(0xFFFF8A80), Color(0xFFFF5252), Color(0xFF800000))
        else -> listOf(Color(0xFFF3D5B5), Color(0xFF8B5E34), Color(0xFF533101))
    }
    val gradient = Brush.linearGradient(colors = gradientColors)

    Box(
        modifier = modifier
            .padding(4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp), clip = false) // gray shadow
    ){
        Card(
            elevation = 8.dp,
            backgroundColor = Color.White

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appWhite),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(lender.image)
                                .crossfade(true)
                                .placeholder(R.drawable.bank_icon)
                                .decoderFactory(SvgDecoder.Factory())
                                .build()
                        )

                        Image(
                            painter = painter,
                            contentDescription = lender.name,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        lender.name?.let {
                            StartingText(
                                text = it,
                                style = normal12Text700,
                                textColor =gradientColors.last()
                            )
                        }
                    }

                }

                Box(modifier = Modifier.fillMaxWidth().background(brush = gradient),
                    contentAlignment = Alignment.Center
                ){
                    Column(modifier = Modifier.alpha(0.1f).padding(8.dp)) {
                        Row {
                            lender.maxLoanAmount?.let {
                                HeaderWithValue(
                                    textHeader = stringResource(id = R.string.max_loan_amount),
                                    textValue = it,
                                    modifier = Modifier.weight(0.5f),
                                    headerStyle = normal10Text400,
                                    valueStyle = normal10Text400,
                                    start = 5.dp,
                                    end=5.dp
                                )
                            }
                            lender.minLoanAmount?.let {
                                HeaderWithValue(
                                    textHeader = stringResource(id = R.string.min_loan_amount),
                                    textValue = it,
                                    modifier = Modifier.weight(0.5f),
                                    headerStyle = normal10Text400,
                                    valueStyle = normal10Text400,
                                    start = 5.dp,
                                    end=5.dp
                                )
                            }
                        }

                        Spacer(Modifier.height(2.dp))

                        Row {
                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.interest),
                                textValue = "${lender.minInterestRate} - ${lender.maxInterestRate}",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end=5.dp
                            )

                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.tenure),
                                textValue = "${lender.minTenure}",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end=5.dp
                            )
                        }
                    }
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(28.dp),
                        color = Color.White,
                        strokeWidth = 4.dp
                    )
                }
            }


        }
    }
        }
}
@Composable
fun RejectedOfferCard(
    navController: NavHostController,
    lender: RejectedLenders,
    index: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp), clip = false) // gray shadow
    ){
    Card(
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appWhite),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(lender.image)
                                .crossfade(true)
                                .placeholder(R.drawable.bank_icon)
                                .decoderFactory(SvgDecoder.Factory())
                                .build()
                        )
                        val grayColorFilter =
                            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

                        Image(
                            painter = painter,
                            contentDescription = lender.name,
                            modifier = Modifier.size(24.dp),
                            colorFilter = grayColorFilter
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        lender.name?.let {
                            StartingText(
                                text = it,
                                style = normal12Text700,
                                textColor = hintGray
                            )
                        }
                    }

                }

                Box(
                    modifier = Modifier.fillMaxWidth().background(rejectedGray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.alpha(0.3f).padding(8.dp)) {
                        Row {
                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.max_amount),
                                textValue = lender.maxLoanAmount?: "₹500000.00",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end = 5.dp
                            )
                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.min_amount),
                                textValue =  lender.minLoanAmount?: "₹20000.00",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end = 5.dp
                            )
                        }

                        Spacer(Modifier.height(2.dp))

                        Row {
                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.min_interest),
                                textValue =  lender.minInterestRate?:"7%",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end = 5.dp
                            )
                            HeaderWithValue(
                                textHeader = stringResource(id = R.string.max_interest),
                                textValue =  lender.maxInterestRate?:"14%",
                                modifier = Modifier.weight(0.5f),
                                headerStyle = normal10Text400,
                                valueStyle = normal10Text400,
                                start = 5.dp,
                                end = 5.dp
                            )
                        }
                    }
                    RegisterText(
                        text = stringResource(id = R.string.no_offer_generated),
                        style = normal14Text700,
                    )
                }
            }
        }

        }
    }
}
