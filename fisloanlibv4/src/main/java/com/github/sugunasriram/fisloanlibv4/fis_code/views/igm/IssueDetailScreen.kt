package com.github.sugunasriram.fisloanlibv4.fis_code.views.igm

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.BorderCardWithElevation
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.DisplayCard
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToIssueListScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.CloseIssueBody
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.ComplainantActionsItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueByIdResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueResolutions
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueResolutionsProviders
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.IssueSummary
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.igm.RespondentActionsItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appGreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.loanIssueCardRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.softSteelGray
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.igm.CreateIssueViewModel

@Composable
fun IssueDetailScreen(navController: NavHostController, issueId: String, fromFlow: String) {

    val createIssuePLViewModel: CreateIssueViewModel = viewModel()
    val context = LocalContext.current

    val issueClosing by createIssuePLViewModel.issueClosing.collectAsState()
    val issueClosed by createIssuePLViewModel.issueClosed.collectAsState()
    val checkingStatus by createIssuePLViewModel.checkingStatus.collectAsState()
    val checkedStatus by createIssuePLViewModel.checkedStatus.collectAsState()
    val loadingIssue by createIssuePLViewModel.loadingIssue.collectAsState()
    val loadedIssue by createIssuePLViewModel.loadedIssue.collectAsState()
    val issueByIdResponse by createIssuePLViewModel.issueByIdResponse.collectAsState()


    val showInternetScreen by createIssuePLViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by createIssuePLViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by createIssuePLViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by createIssuePLViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by createIssuePLViewModel.unAuthorizedUser.observeAsState(false)

    when {
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        else -> {
            IssueDetailView(
                issueClosing = issueClosing, checkingStatus = checkingStatus, context = context,
                loadingIssue = loadingIssue, checkedStatus = checkedStatus, issueId = issueId,
                loadedIssue = loadedIssue, issueClosed = issueClosed, fromFlow = fromFlow,
                issueByIdResponse = issueByIdResponse, navController = navController,
                createIssuePLViewModel = createIssuePLViewModel,
            )
        }
    }
}

@Composable
fun IssueDetailView(
    issueClosing: Boolean, checkingStatus: Boolean, loadingIssue: Boolean, context: Context,
    checkedStatus: Boolean, loadedIssue: Boolean, issueClosed: Boolean, issueId: String,
    issueByIdResponse: IssueByIdResponse?, navController: NavHostController,
    createIssuePLViewModel: CreateIssueViewModel, fromFlow: String
) {
    if (issueClosing || checkingStatus || loadingIssue) {
        CenterProgress()
    } else if (!issueClosing && issueClosed) {
        navigateToIssueListScreen(
            navController = navController, orderId = "12345", fromFlow = fromFlow,
            providerId = "12345", loanState = "No Need", fromScreen = "Create Issue"
        )
    } else {
        if (checkedStatus || loadedIssue) {
            issueByIdResponse?.let {
                IssueDetails(
                    response = issueByIdResponse, navController = navController, issueId = issueId,
                    createIssuePLViewModel = createIssuePLViewModel, context = context,
                )
            }
        } else if (issueClosed) {
            navigateToIssueListScreen(
                navController = navController, orderId = "12345", fromFlow = fromFlow,
                providerId = "12345", loanState = "No Need", fromScreen = "Create Issue"
            )
        } else {
            createIssuePLViewModel.issueById(issueId, context)
        }
    }
}

@Composable
fun IssueDetails(
    response: IssueByIdResponse, navController: NavHostController, context: Context,
    issueId: String, createIssuePLViewModel: CreateIssueViewModel,
) {
    val showCloseButton = !response.data?.data?.summary?.status?.lowercase().equals("close") &&
            !response.data?.data?.summary?.status?.lowercase().equals("closed")

    FixedTopBottomScreen(
        navController = navController,
        topBarBackgroundColor = appOrange,
        topBarText = stringResource(R.string.issue_details),
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        showBottom = true,
        showDoubleButton = showCloseButton,
        primaryButtonText = stringResource(R.string.close),
        onPrimaryButtonClick = {
            response.data?.data?.summary?.loanType?.let { loanType ->
                response.data.data.summary.id.let { issueId ->
                    createIssuePLViewModel.closeIssue(
                        CloseIssueBody(
                            loanType = loanType, issueId = issueId, status = "CLOSED",
                            rating = "THUMBS-UP"
                        ),
                        context = context
                    )
                }
            }
        },
        secondaryButtonText = stringResource(R.string.refresh),
        onSecondaryButtonClick = { createIssuePLViewModel.issueStatus(issueId, context) },
        backgroundColor = appWhite
    ) {
        if (response.data?.data?.summary?.status?.lowercase().equals("open") ||
            response.data?.data?.summary?.status?.lowercase().equals("processing")
        ) {
            IssueHeader(
                headerText = stringResource(id = R.string.issue_has_been_reported_successfully),
                subHeaderText = stringResource(id = R.string.issue_has_been_reported_successfully_discription)
            )
        } else if (response.data?.data?.summary?.status?.lowercase().equals("resolved")) {
            IssueHeader(
                headerText = stringResource(id = R.string.issue_has_been_resolved_successfully),
                subHeaderText = stringResource(id = R.string.issue_has_been_resolved_successfully_discription)
            )
        } else if (response.data?.data?.summary?.status?.lowercase().equals("closed")) {
            IssueHeader(
                headerText = stringResource(id = R.string.issue_has_been_closed_successfully),
                subHeaderText = stringResource(id = R.string.issue_has_been_closed_successfully_discription)
            )
        }

        when (response.data?.data?.summary?.status?.lowercase()) {
            "open" -> loanIssueCardRed     // Red color for Open
            "processing" -> appOrange// Blue color for Processing
            "resolved" -> appGreen
            "closed" -> appGreen// Green color for Resolved
            else -> Color.Transparent // Default color for other statuses
        }

        //
        var updatedDate: String;
        if (response.data?.data?.details?.issueClose?.message?.issue?.updatedAt != null) {
            updatedDate = response.data.data.details.issueClose.message.issue.updatedAt
        } else if (response.data?.data?.details?.onIssueStatus?.message?.issue?.updatedAt != null) {
            updatedDate = response.data.data.details.onIssueStatus.message.issue.updatedAt
        } else if (response.data?.data?.details?.onIssue?.message?.issue?.updatedAt != null) {
            updatedDate = response.data.data.details.onIssue.message.issue.updatedAt
        } else if (response.data?.data?.details?.issueOpen?.message?.issue?.updatedAt != null) {
            updatedDate = response?.data?.data?.details?.issueOpen?.message?.issue?.updatedAt
        } else {
            updatedDate = response.data?.data?.createdAt.toString()
        }

        response.data?.data?.createdAt?.let { createdAt ->
            response.data.data.summary?.id?.let { issueId ->
                response.data.data.summary.status?.let { status ->
                    response.data.data.summary.orderId?.let { orderId ->
                        IssueStatusCard(
                            createdDate = CommonMethods().displayFormattedDate(createdAt),
                            updatedDate = CommonMethods().displayFormattedDate(updatedDate),
                            issueId = issueId,
                            status = status,
                            loanId = orderId,
                        )
                    }
                }
            }
        }
        response.data?.data?.summary?.let { IssueDetailsCard(it) }

        if (!response.data?.data?.summary?.respondentActions.isNullOrEmpty()) {
            response.data?.data?.summary?.respondentActions?.let { respondentActions ->
                IssueRespondentActionCard(respondentActions)
            }
        }
        if (!response.data?.data?.summary?.complainantActions.isNullOrEmpty()) {
            response.data?.data?.summary?.complainantActions?.let { complainantActions ->
                IssueComplainantActionCard(complainantActions)
            }
        }
        if (response.data?.data?.summary?.resolutions != null) {
            response.data?.data?.summary?.resolutions?.let { resolutions ->
                response.data?.data?.summary?.resolutionsProviders?.let { resolutionsProviders ->
                    IssueResolutionCard(resolutions, resolutionsProviders)
                }
            }
        }
    }
}

@Composable
fun IssueHeader(headerText: String, subHeaderText: String) {
    RegisterText(
        text = headerText, style = normal16Text700,
        top = 10.dp, end = 10.dp, bottom = 8.dp
    )
    Text(
        text = subHeaderText,
        modifier = Modifier.padding(horizontal = 25.dp),
        textAlign = TextAlign.Start, style = normal16Text500, color = hintGray
    )
}

@Composable
fun IssueStatusCard(
    createdDate: String, updatedDate: String, issueId: String,
    status: String, loanId: String,
) {
    BorderCardWithElevation(
        borderColor = appOrange,
        top = 10.dp, bottom = 5.dp, start = 8.dp, end = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 3.dp, end = 3.dp)
        ) {
            Column(modifier = Modifier.weight(0.7f)) {
                StartingText(
                    text = "Created On : $createdDate",
                    textColor = hintGray,
                    style = normal16Text400, bottom = 8.dp
                )
                StartingText(
                    text = "Last Updated : $updatedDate",
                    textColor = hintGray,
                    style = normal16Text400,
                )
            }
            StatusChip(statusText = status, modifier = Modifier.weight(0.3f))
        }
        MultiStyleText(
            stringResource(id = R.string.loan_id)+" : ", appOrange, loanId, appBlack,
            normal16Text500, normal16Text500, top = 8.dp, bottom = 8.dp, start = 3.dp
        )
        MultiStyleText(
            stringResource(id = R.string.issue_id)+" : ", appOrange, issueId, appBlack,
            normal16Text500, normal16Text500, top = 0.dp, bottom = 8.dp, start = 3.dp
        )

    }
}

@Composable
fun IssueDetailsCard(issueSummary: IssueSummary) {
    StartingText(
        text = stringResource(id = R.string.other_details),
        textColor = appBlack,
        style = normal16Text700, start = 25.dp, bottom = 8.dp, top = 10.dp
    )
    DisplayCard(
        cardColor = backgroundOrange, borderColor = Color.Transparent,
        start = 25.dp, end = 25.dp
    ) {
        StartingText(
            text = stringResource(id = R.string.item_name),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp, top = 8.dp
        )
        issueSummary.loanType?.let {
            val loanName =  if (it == "INVOICE_BASED_LOAN"){
                "Invoice Based Loan"
            } else {
                "Personal Loan"
            }
            StartingText(
                text = loanName,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StartingText(
            text = stringResource(id = R.string.issue_description),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp
        )
        issueSummary.subCategoryDesc?.let {
            StartingText(
                text = it,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StartingText(
            text = stringResource(id = R.string.category_name),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp
        )
        issueSummary.category?.let {
            StartingText(
                text = it,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StartingText(
            text = stringResource(id = R.string.short_description),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp
        )
        issueSummary.shortDescription?.let {
            StartingText(
                text = it,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StartingText(
            text = stringResource(id = R.string.long_description),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp
        )
        issueSummary.longDescription?.let {
            StartingText(
                text = it,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StartingText(
            text = stringResource(id = R.string.issue_image),
            textColor = appOrange,
            style = normal16Text400, bottom = 3.dp, start = 10.dp
        )
        issueSummary.images?.let {
            IssueImagesSection(images = it)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun IssueImagesSection(images: List<String?>) {
    var previewUrl by remember { mutableStateOf<String?>(null) }
    Row(
      horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        images.filterNotNull().forEach { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Issue Image",
                    modifier = Modifier.size(120.dp)
//                        .height(120.dp)
                        .padding(horizontal = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { previewUrl = imageUrl },
                    contentScale = ContentScale.Fit
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))


    // Show dialog when image is clicked
    if (previewUrl != null) {
        Dialog(onDismissRequest = { previewUrl = null }) {
            Box(
                modifier = Modifier.wrapContentSize()
//                    .background(Color.Black.copy(alpha = 0.9f))
                    .clickable { previewUrl = null },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = previewUrl,
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
@Composable
fun IssueResolutionCard(
    issueResolutions: IssueResolutions,
    issueResolutionsProviders: IssueResolutionsProviders
) {
    StartingText(
        text = stringResource(id = R.string.resolutions),
        textColor = appBlack,
        style = normal16Text700, start = 25.dp, bottom = 8.dp, top = 15.dp
    )
    DisplayCard(
        cardColor = backgroundOrange, borderColor = Color.Transparent,
        start = 25.dp, end = 25.dp
    ) {
        issueResolutions.shortDesc?.let { shortDesc ->
            StartingText(
                text = stringResource(id = R.string.short_description),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = shortDesc,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        issueResolutions.longDesc?.let { longDesc ->
            StartingText(
                text = stringResource(id = R.string.long_description),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = longDesc,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        issueResolutionsProviders.contactPerson?.let { contactPerson ->
            StartingText(
                text = stringResource(id = R.string.name),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = contactPerson,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        issueResolutionsProviders.contactEmail?.let { email ->
            StartingText(
                text = stringResource(id = R.string.email),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = email,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        issueResolutionsProviders.contactPhone?.let { phone ->
            StartingText(
                text = stringResource(id = R.string.mobile_number),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = phone,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        issueResolutionsProviders.organizationName?.let { org ->
            StartingText(
                text = stringResource(id = R.string.mobile_number),
                textColor = appOrange,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            StartingText(
                text = org,
                textColor = appBlack,
                style = normal16Text400, bottom = 3.dp, start = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun IssueRespondentActionCard(respondentActionList: List<RespondentActionsItem?>) {
    StartingText(
        text = stringResource(id = R.string.respondent_actions),
        textColor = appBlack,
        style = normal16Text700, start = 25.dp, bottom = 8.dp, top = 15.dp
    )
    respondentActionList.forEach { respondentAction ->
        DisplayCard(
            cardColor = backgroundOrange, borderColor = Color.Transparent,
            start = 25.dp, end = 25.dp
        ) {
            respondentAction?.respondentAction?.let { respondentAction ->
                StartingText(
                    text = stringResource(id = R.string.action),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp, top = 10.dp
                )
                StartingText(
                    text = respondentAction,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            respondentAction?.shortDesc?.let { shortDesc ->
                StartingText(
                    text = stringResource(id = R.string.description),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = shortDesc,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            respondentAction?.updatedBy?.person?.name?.let { personName ->
                StartingText(
                    text = stringResource(id = R.string.updated_by),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = personName,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
            }
            respondentAction?.updatedBy?.org?.name?.let { organisationName ->
                StartingText(
                    text = organisationName,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 8.dp
                )
            }
            respondentAction?.updatedBy?.contact?.phone?.let { phone ->
                StartingText(
                    text = phone,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
            }
            respondentAction?.updatedBy?.contact?.email?.let { email ->
                StartingText(
                    text = email,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            respondentAction?.updatedAt?.let { updatedAt ->
                StartingText(
                    text = stringResource(id = R.string.updated_on),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = CommonMethods().displayFormattedDate(updatedAt),
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun IssueComplainantActionCard(complainantActionList: List<ComplainantActionsItem?>) {
    StartingText(
        text = stringResource(id = R.string.complainant_actions),
        textColor = appBlack,
        style = normal16Text700, start = 25.dp, bottom = 8.dp, top = 15.dp
    )
    complainantActionList.forEach { complainantAction ->
        DisplayCard(
            cardColor = backgroundOrange, borderColor = Color.Transparent,
            start = 25.dp, end = 25.dp
        ) {
            complainantAction?.complainantAction?.let { complainantAction ->
                StartingText(
                    text = stringResource(id = R.string.action),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp ,top = 10.dp
                )
                StartingText(
                    text = complainantAction,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            complainantAction?.shortDesc?.let { shortDesc ->
                StartingText(
                    text = stringResource(id = R.string.description),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = shortDesc,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            complainantAction?.updatedBy?.orgName?.let { orgName ->
                StartingText(
                    text = stringResource(id = R.string.updated_by),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = orgName,
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
            }

            complainantAction?.updatedBy?.let { update ->
                update.org?.name?.let { name ->
                    StartingText(
                        text = name,
                        textColor = appBlack,
                        style = normal16Text400, bottom = 3.dp, start = 10.dp
                    )
                }
                update.contactPhone?.let { contactPhone ->
                    StartingText(
                        text = contactPhone,
                        textColor = appBlack,
                        style = normal16Text400, bottom = 3.dp, start = 10.dp
                    )
                }
                update.contactEmail?.let { contactEmail ->
                    StartingText(
                        text = contactEmail,
                        textColor = appBlack,
                        style = normal16Text400, bottom = 3.dp, start = 10.dp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            complainantAction?.updatedAt?.let { updatedAt ->
                StartingText(
                    text = stringResource(id = R.string.updated_on),
                    textColor = appOrange,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                StartingText(
                    text = CommonMethods().displayFormattedDate(updatedAt),
                    textColor = appBlack,
                    style = normal16Text400, bottom = 3.dp, start = 10.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun HorizontalDashedLine(
    color: Color = Color.Gray, lineHeight: Dp = 2.dp, dashLength: Dp = 10.dp, gapLength: Dp = 5.dp
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        val strokeWidth = lineHeight.toPx()
        val dashOn = dashLength.toPx()
        val dashOff = gapLength.toPx()

        drawLine(
            color = color, start = androidx.compose.ui.geometry.Offset(0f, center.y),
            end = androidx.compose.ui.geometry.Offset(size.width, center.y),
            strokeWidth = strokeWidth, cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashOn, dashOff), 0f),

            )
    }
}

@Composable
fun ReusableText(
    text: String, color: Color = softSteelGray, style: TextStyle = normal16Text700,
    textAlign: TextAlign = TextAlign.Start, modifier: Modifier = Modifier,
    start: Dp = 20.dp, end: Dp = 0.dp, top: Dp = 0.dp, bottom: Dp = 10.dp
) {
    Text(
        text = text, color = color, style = style, textAlign = textAlign,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = start, bottom = bottom, end = end, top = top)
    )
}