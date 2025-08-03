package com.github.sugunasriram.fisloanlibv4.fiscode.views.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToContactUsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues.Data
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold20Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.checkBoxGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanIssueCardGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text700
import io.ktor.client.features.ResponseException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReportedIssuesScreen(navController: NavHostController) {

    val listOfIssues = remember { mutableStateListOf<Data>() }
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedIssue: Data? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        listOfIssues.addAll(getAllIssues({ isLoading = it }))
    }
    if (isLoading) {
        CenterProgress()
    } else {
        FixedTopBottomScreen(
            navController = navController,
            topBarBackgroundColor = appOrange,
            showBackButton = true,
            onBackClick = {
                navigateToContactUsScreen(navController)
            },
            showBottom = false,
            showSingleButton = true,
            showErrorMsg = false,
            errorMsg = stringResource(R.string.please_enter_all_mandatory_details_and_upload_photo),
            backgroundColor = appWhite
        ) {
            StartingText(
                text = stringResource(R.string.reported_issues),
                textColor = appOrange,
                start = 15.dp,
                end = 30.dp,
                top = 30.dp,
                bottom = 10.dp,
                style = bold20Text700,
                alignment = Alignment.Center
            )

            if (listOfIssues.isNotEmpty()) {
                listOfIssues.forEach { issue ->
                    IssueCard(issue, {
                        showDialog = true
                        selectedIssue = issue
                    })
                }
            }else{
                Text(
                    modifier = Modifier.padding(50.dp),
                    text = "No Reported Issues",
                    style = normal32Text700,
                    color = checkBoxGray,
                )

            }
        }

        if (showDialog) {
            OpenedIssuesScreen(selectedIssue) { showDialog = it }
        }

    }


}


private suspend fun getAllIssues(
    setIsLoading: (Boolean) -> Unit,
    checkForAccessToken: Boolean = true
): List<Data> {
    setIsLoading(true)
    runCatching {
        ApiRepository.getAllUserReportedIssueFindUserId()?.data
    }.onSuccess { response ->
        response?.let {
            setIsLoading(false)
            return it
        }
    }.onFailure { error ->
        setIsLoading(false)
        if (checkForAccessToken &&
            error is ResponseException && error.response.status.value == 401
        ) {
            if (handleAuthGetAccessTokenApi()) {
                getAllIssues(
                    setIsLoading = { setIsLoading(it) },
                    checkForAccessToken = false
                )
            } else {
                return emptyList()
            }
        }
    }
    return emptyList()
}


@Composable
fun IssueCard(issue: Data, openIssue: () -> Unit) {
    Spacer(Modifier.padding(top = 17.dp))
    Column {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(100.dp)
                .padding(horizontal = 12.dp, vertical = 5.dp)
                .background(loanIssueCardGray),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 100.dp)
                        .padding(vertical = 16.dp, horizontal = 9.dp),
                    text = issue.message
                )

                Text(
                    "View",
                    style = normal16Text400.copy(textDecoration = TextDecoration.Underline),
                    color = appOrange,
                    modifier = Modifier.clickable {
                        openIssue()
                    }
                )


            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatToDate(issue.createdAt), style = normal14Text500, color = appOrange)

            MultiStyleText(
                "Time: ",
                appBlack,
                formatToTime(issue.createdAt),
                appOrange,
                normal14Text500,
                normal14Text500,
                modifier = Modifier
                    .padding(top = 0.dp, start = 0.dp, bottom = 0.dp)
                    .wrapContentWidth()
            )
        }

    }
}


fun formatToTime(input: String): String {
    val utcZonedDateTime = ZonedDateTime.parse(input)
    val istZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return istZonedDateTime.format(formatter)
}


fun formatToDate(input: String): String {
    val zonedDateTime = ZonedDateTime.parse(input)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return zonedDateTime.format(formatter)
}


@Composable
fun OpenedIssuesScreen(selectedIssue: Data?, setDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setDialog(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 500.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = { setDialog(false) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable content
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .weight(1f, fill = false)
                ) {
                    selectedIssue?.message?.let {
                        Text(
                            text = it,
                            style = normal18Text400,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

            }
        }
    }
}


@Preview
@Composable
private fun ReportedIssuesScreenPrev() {
    ReportedIssuesScreen(rememberNavController())
}
