package com.github.sugunasriram.fisloanlibv4.fis_code.views.igm

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.BorderCardWithElevation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToCreateIssueScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToIssueDetailScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanStatusScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueListBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueListResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueObj
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.OrderIssueResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.greenColour
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanIssueCardGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanIssueCardGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanIssueCardLightGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanIssueCardRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanStatusCardRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.negativeGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.slateGrayColor
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.igm.CreateIssueViewModel
import com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid.NoExistingLoanScreen


@Composable
fun IssueListScreen(
    navController: NavHostController, orderId: String, loanState: String, providerId: String,
    fromFlow: String, fromScreen: String
) {
    val context = LocalContext.current
    val createIssueViewModel: CreateIssueViewModel = viewModel()
    val issueListLoading by createIssueViewModel.issueListLoading.collectAsState()
    val issueListLoaded by createIssueViewModel.issueListLoaded.collectAsState()
    val issueList by createIssueViewModel.issueListResponse.collectAsState()
    val orderIssuesLoading by createIssueViewModel.orderIssuesLoading.collectAsState()
    val orderIssuesLoaded by createIssueViewModel.orderIssuesLoaded.collectAsState()
    val orderIssuesResponse by createIssueViewModel.orderIssuesResponse.collectAsState()

    val showInternetScreen by createIssueViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by createIssueViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by createIssueViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by createIssueViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by createIssueViewModel.unAuthorizedUser.observeAsState(false)

    BackHandler { onIssueBackClick(fromScreen = fromScreen, navController = navController) }

    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            IssueListView(
                issueListLoading = issueListLoading, orderIssuesLoading = orderIssuesLoading,
                issueListLoaded = issueListLoaded, orderIssuesLoaded = orderIssuesLoaded,
                navController = navController, fromScreen = fromScreen, fromFlow = fromFlow,
                orderIssuesResponse = orderIssuesResponse, loanState = loanState,
                providerId = providerId, orderId = orderId, issueList = issueList,
                createIssueViewModel = createIssueViewModel, context = context
            )
        }
    }
}

@Composable
fun IssueListView(
    orderIssuesResponse: OrderIssueResponse?, loanState: String, providerId: String,
    issueListLoading: Boolean, orderIssuesLoading: Boolean, context: Context,
    navController: NavHostController, fromScreen: String, orderId: String,
    issueListLoaded: Boolean, orderIssuesLoaded: Boolean, issueList: IssueListResponse?,
    createIssueViewModel: CreateIssueViewModel,fromFlow: String
) {
    if (issueListLoading || orderIssuesLoading) {
        CenterProgress()
    } else {
        if (issueListLoaded || orderIssuesLoaded) {

            val issues =
                if (issueList != null) issueList.data?.pageData?.issues else
                    orderIssuesResponse?.data?.data
            issues?.let {
                FixedTopBottomScreen(
                    navController = navController,
                    topBarBackgroundColor = appOrange,
                    topBarText = stringResource(id = R.string.issue_list),
                    showBackButton = true,
                    onBackClick = { onIssueBackClick(fromScreen = fromScreen, navController = navController) },
                    backgroundColor = appWhite,
                    contentStart = 0.dp, contentEnd = 0.dp
                ) {
                    if (it.isEmpty()) {
                        Spacer(modifier = Modifier.height(150.dp))
                        NoExistingLoanScreen(displayText =stringResource(R.string.no_existing_issues), )
//                        EmptyListScreen()
                    } else {

                        if (fromScreen.equals(stringResource(id = R.string.loan_detail), ignoreCase = true)){
                            MultiStyleText(stringResource(id = R.string.for_loan_id), appBlack,providerId, slateGrayColor,
                                normal16Text700, normal16Text500,top=16.dp, start = 22.dp,bottom=16.dp)
                        }else{
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        IssueCardList(navController, issues, fromFlow = fromFlow)
                        if (fromScreen.equals(stringResource(id = R.string.loan_detail), ignoreCase = true)) {
                            StatusChip(
                                statusText = stringResource(id = R.string.add_issue),
                                backGroundColor = Color.Transparent,
                                borderColor = errorRed,
                                textColor = errorRed,
                                cardWidth = 1.dp,
                                textStyle = normal18Text500,
                                modifier = Modifier
//                                    .padding(top = 10.dp, end = 10.dp)
                                    .clickable {
                                        onRaiseIssueClick(
                                            orderIssuesResponse = orderIssuesResponse,
                                            navController = navController,
                                            orderId = orderId,
                                            providerId = providerId,
                                            loanState = loanState
                                        )
                                    }
                            )
                        }
                    }

                }
            }
        } else {
            if (fromScreen.equals(stringResource(id = R.string.loan_detail), ignoreCase = true)) {
                createIssueViewModel.orderIssues(orderId = orderId, context = context)
            } else {
                createIssueViewModel.getIssueListForUser(context, IssueListBody(1, 10))
            }
        }
    }
}

@Composable
fun IssueCardList(navController: NavHostController, issueList: List<IssueObj>?,fromFlow: String) {
        issueList?.forEach { issue ->
            IssueCardD(issue, onClick = {
                issue.id?.let {
                    navigateToIssueDetailScreen(
                        navController = navController, issueId = it, fromFlow = fromFlow
                    )
                }
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
}

@Composable
fun IssueCardD(issue: IssueObj, onClick: () -> Unit){
    BorderCardWithElevation(
        backgroundColor =loanIssueCardGray, borderColor =loanIssueCardGray,
        top=5.dp, bottom = 5.dp,
        modifier = Modifier.clickable {  onClick() }
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 5.dp, end = 8.dp)){
            Column (modifier = Modifier.weight(0.7f)){
                issue.summary?.loanType?.let { loanType ->
                    val loanName =  if (loanType == "INVOICE_BASED_LOAN"){
                        "Invoice Based Loan"
                    } else {
                        "Personal Loan"
                    }
                    MultiStyleText(stringResource(id = R.string.item_name) +": ", appOrange,loanName, appBlack,
                        normal16Text500, normal16Text500,top=0.dp, start = 0.dp)
                }

                MultiStyleText(stringResource(id = R.string.category_name) +": ", appOrange,"${issue.summary?.category}", appBlack,
                    normal16Text500, normal16Text500,top=8.dp, start = 0.dp)

            }
            val preferredColor = when (issue.summary?.status?.lowercase()) {
                "open" -> loanIssueCardRed       // Red color for Open
                "processing" -> appOrange// Blue color for Processing
                "resolved" -> loanIssueCardGreen  // Green color for Resolved
                "closed" ->loanIssueCardGreen
                else -> Color.Gray         // Default color for other statuses
            }
            val backGroundColorColor = when (issue.summary?.status?.lowercase()) {
                "open" -> loanStatusCardRed       // Red color for Open
                "processing" -> backgroundOrange// Blue color for Processing
                "resolved" -> loanIssueCardLightGreen
                "closed" ->loanIssueCardLightGreen// Green color for Resolved
                else -> Color.Transparent         // Default color for other statuses
            }

            issue.summary?.status?.let { it ->
                StatusChip(
                    statusText = it.replaceFirstChar { it.uppercase() }, backGroundColor =backGroundColorColor, borderColor = preferredColor,
                    textColor = preferredColor, cardWidth = 1.dp,
                    modifier = Modifier.weight(0.3f)
                )
            }
        }
        MultiStyleText(stringResource(id = R.string.issue_id) +": ", appOrange,"${issue.summary?.id}", appBlack,
            normal16Text500, normal16Text500,top=8.dp, start = 5.dp, bottom = 10.dp)
    }
}

@Composable
fun StatusChip(
    statusText: String, modifier: Modifier, backGroundColor: Color = greenColour,
    borderColor: Color = greenColour, cardWidth: Dp = 0.dp, textColor: Color = Color.White,
    textStyle: TextStyle=normal14Text500
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(4.dp).width(110.dp)
            .background(color = backGroundColor, shape = RoundedCornerShape(4.dp))
            .border(width = cardWidth, color = borderColor, shape = RoundedCornerShape(4.dp))
    ) {
        Text(
            text = statusText, color = textColor, style = textStyle,
            modifier = Modifier.padding(8.dp)
        )
    }
}

fun onRaiseIssueClick(
    orderIssuesResponse: OrderIssueResponse?, navController: NavHostController, loanState: String,
    orderId: String, providerId: String,
) {
    orderIssuesResponse?.data?.data?.forEach { issue ->
        issue.summary?.loanType?.let { loanType ->
            val setFlow = CommonMethods().setFromFlow(loanType)
            navigateToCreateIssueScreen(
                navController = navController, orderId = orderId, providerId = providerId,
                orderState = loanState, fromFlow = setFlow,
            )
        }
    }
}

fun onIssueBackClick(fromScreen: String, navController: NavHostController) {
    if (fromScreen.equals("HAMBURGER", ignoreCase = true)) {
        navigateApplyByCategoryScreen(navController)
    } else {
        navigateToLoanStatusScreen(navController)
    }
}
@Composable
fun EmptyListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_existing_issues),
            textAlign = TextAlign.Center, fontSize = 32.sp, color = negativeGray,
            fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontWeight = FontWeight(800),

            )
    }
}