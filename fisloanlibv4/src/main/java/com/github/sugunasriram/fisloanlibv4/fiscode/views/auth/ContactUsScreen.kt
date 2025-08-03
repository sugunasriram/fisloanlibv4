package com.github.sugunasriram.fisloanlibv4.fiscode.views.authauth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ClickableText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToReportedIssuesScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository
import com.github.sugunasriram.fisloanlibv4.fiscode.network.core.ApiRepository.handleAuthGetAccessTokenApi
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.userIssues.UserReportedIssueCreateRequest
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appTheme
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.cursorColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.loanIssueCardGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.slateGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.textBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager
import io.ktor.client.features.ResponseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun ContactUsScreen(navController: NavHostController) {
    var issueText = ""
    var noInternet : Boolean = false
    var issueTextError: String? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    if (isLoading) {
        CenterProgress()
    } else {
        FixedTopBottomScreen(
            navController = navController,
            topBarBackgroundColor = appOrange,
            topBarText = stringResource(R.string.contact_us),
            showBackButton = true,
            onBackClick = {
                navigateApplyByCategoryScreen(navController)
            },
            showBottom = true,
            showSingleButton = true,
            showErrorMsg = false,
            errorMsg = stringResource(R.string.please_enter_all_mandatory_details_and_upload_photo),
            primaryButtonText = stringResource(R.string.submit),
            onPrimaryButtonClick = {
                createIssue(
                    issueText,
                    setIsLoading = { isLoading = it },
                    coroutineScope,
                    navController,
                    context,
                    setIssueTextError = { issueTextError = it }
                )
            },
            backgroundColor = appWhite
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Row(Modifier.padding(vertical = 15.dp)) {
                    ClickableText(
                        text = stringResource(id = R.string.view_all_issue),
                        textAlign = TextAlign.Justify,
                        backgroundColor = Color.White,
                        borderColor = appOrange,
                        textColor = appOrange,
                        style = normal18Text500,
                        width = 2.dp,
                    ) {
                        navigateToReportedIssuesScreen(navController)
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 20.dp)
                            .background(loanIssueCardGray),
                        shape = RoundedCornerShape(16.dp),
                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Row(horizontalArrangement = Arrangement.Center) {
                                RegisterText(
                                    text = stringResource(R.string.reach_out_to_us),
                                    style = normal20Text500,
                                    textColor = textBlack,
                                    top = 15.dp,
                                    bottom = 15.dp
                                )

                            }

                            Column(Modifier.padding(vertical = 15.dp)) {

                                MultiStyleText(
                                    stringResource(id = R.string.phone) + ": ",
                                    appOrange,
                                    stringResource(R.string.issue_phoneNumber),
                                    appBlack,
                                    normal16Text500,
                                    normal16Text500,
                                    modifier = Modifier
                                        .padding(top = 0.dp, start = 0.dp, bottom = 0.dp)
                                        .wrapContentWidth()
                                )

                                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                                MultiStyleText(
                                    stringResource(id = R.string.email) + ": ",
                                    appOrange,
                                    stringResource(R.string.ondc_support_nearshop_in),
                                    appBlack,
                                    normal16Text500,
                                    normal16Text500,
                                    modifier = Modifier
                                        .padding(top = 0.dp, start = 0.dp, bottom = 0.dp)
                                        .wrapContentWidth()
                                )

                            }
                        }


                    }
                }


                Spacer(modifier = Modifier.padding(vertical = 20.dp))


                Row {

                    IssueInputField(
                        value = "",
                        onValueChange = {
                            issueText = it
                            issueTextError = null
                        },
                        error = issueTextError,
                        regexPattern = "^(?=.{2,})[a-zA-Z0-9@#%&*_+=\\-!?.,]+$",
                        inputLimit = 300,
                        textStyle = normal18Text400,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.please_write_your_concern_here_if_we_are_not_reachable_over_call_email_we_will_get_back_to_you_soon),
                                style = normal18Text400,
                                modifier = Modifier.padding(30.dp),
                                color = Color(0xFF828282)
                            )
                        },
                        modifier = Modifier.padding(30.dp)
                    )
                }
            }
        }
    }

}


private fun createIssue(
    issueText: String,
    setIsLoading: (Boolean) -> Unit,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    context: android.content.Context,
    setIssueTextError: (String) -> Unit,
    checkForAccessToken: Boolean = true
) {
    if (issueText.isNotEmpty()) {
        setIsLoading(true)
        coroutineScope.launch {
            kotlin.runCatching {
                ApiRepository.userReportedIssueCreate(
                    UserReportedIssueCreateRequest(
                        email = TokenManager.read("email")!!,
                        message = issueText,
                        mobileNumber = TokenManager.read("mobileNumber")!!,
                        status = "OPEN"
                    )
                )
            }
                .onSuccess { response ->
                    response?.let {
                        setIsLoading(false)
                        navigateApplyByCategoryScreen(navController)
                        CommonMethods().toastMessage(
                            context = context, toastMsg = context.getString(R.string.your_issue_has_been_submitted)
                        )
                    }
                }.onFailure { error ->
                    if (checkForAccessToken &&
                        error is ResponseException && error.response.status.value == 401
                    ) {
                        if (handleAuthGetAccessTokenApi()) {
                            createIssue(
                                issueText,
                                setIsLoading = { setIsLoading(it) },
                                coroutineScope,
                                navController,
                                context,
                                setIssueTextError = { setIssueTextError(it) }
                            )
                        }
                    } else {
                        CommonMethods().toastMessage(
                            context = context,
                            toastMsg = context.getString(R.string.something_went_wrong)
                        )
                    }
                }
        }
    } else {
        setIssueTextError("Input Filed is Empty")
    }
}


@Composable
fun IssueInputField(
    value: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    error: String?,
    regexPattern: String = "",
    inputLimit: Int = 300,
    textStyle: TextStyle,
    placeholder: @Composable (() -> Unit)? = null,
) {
    val focusState = remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }
    var firstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            val selection = if (firstLaunch) TextRange(value.length) else textFieldValue.selection
            textFieldValue = TextFieldValue(text = value, selection = selection)
            firstLaunch = false
        }
    }

    LaunchedEffect(focusState.value) {
        if (focusState.value) {
            delay(100)
        }
    }

    Box() {

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val rawInput = newValue.text.replace(Regex("\\s+"), " ")
                val oldCursor = newValue.selection.end

                val sanitized: String
                val newCursorPos: Int

                if (regexPattern.isNotBlank()) {
                    val allowedPattern = Regex(regexPattern)
                    val filteredBuilder = StringBuilder()
                    var skippedBeforeCursor = 0

                    rawInput.forEachIndexed { index, char ->
                        if (!allowedPattern.matches(char.toString())) {
                            filteredBuilder.append(char)
                        } else {
                            if (index < oldCursor) {
                                skippedBeforeCursor++
                            }
                        }
                    }

                    sanitized = filteredBuilder.toString().take(inputLimit)
                    newCursorPos = (oldCursor - skippedBeforeCursor).coerceIn(0, sanitized.length)
                } else {
                    sanitized = rawInput.take(inputLimit)
                    newCursorPos = oldCursor
                }

                val newTextFieldValue = TextFieldValue(sanitized, TextRange(newCursorPos))
                textFieldValue = newTextFieldValue
                onValueChange(sanitized)
            },
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            singleLine = false,
            textStyle = textStyle,
            placeholder = placeholder,
            isError = error?.isNotEmpty() == true,
            maxLines = inputLimit,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = appTheme,
                unfocusedBorderColor = backgroundOrange,
                cursorColor = cursorColor,
                errorBorderColor = errorRed,
                disabledBorderColor = appTheme
            ),
        )
        Text(
            text = "${textFieldValue.text.length}/300",
            style = normal12Text400,
            color = slateGrayColor,
            modifier = Modifier
                .padding(bottom = 10.dp, end = 30.dp)
                .align(Alignment.BottomEnd)
        )
    }
}


@Preview
@Composable
private fun PreviewContactUsScreen() {
    ContactUsScreen(rememberNavController())
}