package com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.BackButton
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.ClickableTextWithIcon
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.TopBottomBarForNegativeScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorBlue
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.negativeGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal20Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal24Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal30Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import kotlinx.coroutines.delay

@Composable
fun NegativeCommonScreen(
    navController: NavHostController,
    showRefreshButton: Boolean = true,
    showBottom: Boolean = true,
    errorText: String = stringResource(id = R.string.page_notfound),
    onClick: () -> Unit,
    solutionText: String = stringResource(id = R.string.please_try_again_after_sometime),
    errorImage: Painter = painterResource(id = R.drawable.error_404_image),
    buttonText: String = stringResource(id = R.string.retry),
    errorTextTop: Dp = 50.dp

) {
    TopBottomBarForNegativeScreen(showTop = false, showBottom = showBottom, navController = navController) {
        Image(
            painter = errorImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .fillMaxSize()
        )

        StartingText(
            text = errorText,
            alignment = Alignment.TopCenter,
            style = normal24Text700,
            textColor = errorRed,
            top = errorTextTop
        )
        StartingText(
            text = solutionText,
            alignment = Alignment.TopCenter,
            style = normal20Text400,
            textColor = negativeGray,
            top = 5.dp,
            bottom = if (showRefreshButton) 80.dp else 5.dp
        )
        if (!showRefreshButton) {
            StartingText(
                text = stringResource(id = R.string.try_again),
                alignment = Alignment.TopCenter,
                style = normal18Text500,
                textColor = errorBlue,
                top = 5.dp,
                bottom = 80.dp,
                modifier = Modifier.clickable { onClick() }
            )
        }
        if (showRefreshButton) {
            ClickableTextWithIcon(text = buttonText, image = R.drawable.refresh_icon) {
                onClick()
            }
        }
    }
}

@Preview
@Composable
fun PagesNotFoundPreview() {
    val navController = rememberNavController()
    Surface {
//        NegativeCommonScreen(rememberNavController(), onClick = {})
//        UnAuthorizedScreen(rememberNavController(), onClick = {})
//        UnexpectedErrorScreen(rememberNavController(), onClick = {})
//        RequestTimeOutScreen( rememberNavController(), onClick = {})
//        SessionTimeOutScreen( rememberNavController(),onClick = {})
//        LoanNotApprovedScreen(navController)
        UnexpectedErrorScreen(navController)
//        FormRejectionScreen(navController,"PersonalLoan")
    }
}

@Composable
fun UnexpectedErrorScreen(
    navController: NavHostController,
    errorMsgShow: Boolean = true,
    onClick: () -> Unit = { navigateApplyByCategoryScreen(navController = navController) },
    errorText: String = stringResource(id = R.string.please_try_again_after_sometime),
    errorMsg: String = stringResource(id = R.string.something_went_wrong)
) {
    FixedTopBottomScreen(
        navController = navController,
        backgroundColor = appWhite,
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        topBarBackgroundColor = appWhite
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_unexpected_image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(300.dp)
        )
        StartingText(
            text = stringResource(id = R.string.oops),
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )
        StartingText(
            text = errorMsg,
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            top = 10.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )
        StartingText(
            text = errorText,
            alignment = Alignment.TopCenter,
            style = normal20Text400,
            textColor = negativeGray,
            top = 5.dp,
            bottom = 80.dp
        )
        ClickableTextWithIcon(
            text = stringResource(id = R.string.retry),
            image = R.drawable.refresh_icon
        ) { onClick() }
    }
}

@Composable
fun RequestTimeOutScreen(navController: NavHostController, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        BackButton(navController = navController)
        Box(
            modifier = Modifier
                .padding(top = 90.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.error_request_time_out_image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(300.dp)
                )

                StartingText(
                    text = stringResource(id = R.string.request_timed_out),
                    textColor = errorGray,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal30Text700,
                    alignment = Alignment.TopCenter
                )
                StartingText(
                    text = stringResource(id = R.string.please_try_again_after_sometime),
                    alignment = Alignment.TopCenter,
                    style = normal20Text400,
                    textColor = negativeGray,
                    top = 5.dp,
                    bottom = 80.dp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ClickableText(
                        text = stringResource(id = R.string.go_back),
                        style = normal20Text500
                    ) { navController.popBackStack() }
                    ClickableTextWithIcon(
                        backgroundColor = appOrange,
                        borderColor = appOrange,
                        color = appWhite,
                        style = normal20Text500,
                        imageStart = 25.dp,
                        imageEnd = 25.dp,
                        text = stringResource(id = R.string.retry),
                        image = R.drawable.refresh_icon
                    ) { onClick() }
                }
            }
        }
    }
}

@Composable
fun UnAuthorizedScreen(navController: NavHostController, onClick: () -> Unit) {
    FixedTopBottomScreen(
        navController = navController,
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        backgroundColor = appWhite,
        topBarBackgroundColor = appWhite
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_un_authorized_image),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(300.dp)
                .padding(top = 100.dp)
        )

        StartingText(
            text = stringResource(id = R.string.un_authorized_access),
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            top = 30.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )
        StartingText(
            text = stringResource(id = R.string.please_try_again_after_sometime),
            alignment = Alignment.TopCenter,
            style = normal20Text400,
            textColor = negativeGray,
            top = 5.dp,
            bottom = 80.dp
        )
        ClickableText(
            text = stringResource(id = R.string.retry_login),
            style = normal20Text500
        ) { onClick() }
    }
}

@Composable
fun EmptyLoanStatus(navController: NavHostController) {
    TopBottomBarForNegativeScreen(navController = navController) {
        StartingText(
            text = stringResource(id = R.string.loan_status),
            textColor = appBlueTitle,
            start = 30.dp,
            end = 30.dp,
            top = 30.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )
        Image(
            painter = painterResource(id = R.drawable.loan_not_found),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 50.dp)
        )
        StartingText(
            text = stringResource(id = R.string.no_existing_loans),
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            top = 10.dp,
            bottom = 5.dp,
            style = normal14Text500,
            alignment = Alignment.TopCenter
        )
    }
}

@Composable
fun SessionTimeOutScreen(navController: NavHostController, onClick: () -> Unit) {
    FixedTopBottomScreen(
        navController = navController,
        showBackButton = false,
        backgroundColor = appWhite,
        topBarBackgroundColor = appWhite
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_session_time_out),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(330.dp)
                .padding(top = 130.dp)
        )

        StartingText(
            text = stringResource(id = R.string.session_timed_out),
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            top = 30.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )
        StartingText(
            text = stringResource(id = R.string.please_try_again_after_sometime),
            alignment = Alignment.TopCenter,
            style = normal20Text400,
            textColor = negativeGray,
            top = 5.dp,
            bottom = 50.dp
        )
        ClickableText(
            text = stringResource(id = R.string.re_login),
            style = normal20Text500
        ) { onClick() }
    }
}

@Composable
fun LoanNotApprovedScreen(
    navController: NavHostController,
    text: String = stringResource(id = R.string.loan_not_approved)
) {
    LaunchedEffect(Unit) {
        delay(5000)
        navigateApplyByCategoryScreen(navController = navController)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        BackButton(navController = navController)
        Box(
            modifier = Modifier
                .padding(top = 150.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                StartingText(
                    text = text,
                    textColor = errorRed,
                    style = normal32Text700,
                    alignment = Alignment.TopCenter
                )
                Image(
                    painter = painterResource(id = R.drawable.error_loan_not_approved_image),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(300.dp)
                )

                RegisterText(
                    text = stringResource(id = R.string.we_regret_loan_not_approved),
                    style = normal14Text700,
                    textColor = errorRed,
                    top = 5.dp,
                    bottom = 80.dp
                )
            }
        }
    }
}

@Composable
fun FormRejectionScreen(
    navController: NavHostController,
    fromFlow: String,
    errorMsg: String? = null,
    onClick: () -> Unit = { navigateApplyByCategoryScreen(navController = navController) },
    errorTitle: String = stringResource(id = R.string.kyc_failed)
) {
    LaunchedEffect(Unit) {
        delay(5000)
        navigateApplyByCategoryScreen(navController = navController)
    }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            StartingText(
                text = errorTitle,
                textColor = errorRed,
                style = normal32Text700,
                alignment = Alignment.TopCenter
            )

            Image(
                painter = painterResource(id = R.drawable.error_loan_not_approved_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp)
            )

            val displayedSubText = errorMsg?.takeIf { it.isNotEmpty() }
                ?: stringResource(id = R.string.form_submission_rejected_or_pending)

            StartingText(
                text = displayedSubText,
                textColor = errorGray,
                start = 30.dp,
                end = 30.dp,
                top = 30.dp,
                bottom = 5.dp,
                style = normal14Text500,
                alignment = Alignment.TopCenter
            )

            ClickableTextWithIcon(
                text = stringResource(id = R.string.retry),
                image = R.drawable.refresh_icon
            ) {
                onClick()
            }
        }
    }

    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateApplyByCategoryScreen(navController = navController)
            }
        }
    }

    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(callback)
        onDispose { callback.remove() }
    }
}

@Composable
fun MiddleOfTheLoanScreen(navController: NavHostController) {
    FixedTopBottomScreen(
        navController = navController,
        backgroundColor = appWhite,
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        topBarBackgroundColor = appWhite
    ) {
        Image(
            painter = painterResource(id = R.drawable.middle0ftheloan),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        StartingText(
            text = "You have an ongoing loan application.",
            textColor = errorGray,
            start = 30.dp,
            end = 30.dp,
            bottom = 5.dp,
            style = normal30Text700,
            alignment = Alignment.TopCenter
        )

        StartingText(
            text = "We’re awaiting for response from the lender",
            alignment = Alignment.TopCenter,
            style = normal20Text400,
            textColor = negativeGray,
            top = 5.dp,
            bottom = 80.dp
        )
        ClickableTextWithIcon(
            text = stringResource(id = R.string.retry),
            image = R.drawable.refresh_icon
        ) { navigateApplyByCategoryScreen(navController = navController) }
    }
}

@Preview
@Composable
private fun MiddleOfTheLoanScreenPreview() {
    Surface {
        MiddleOfTheLoanScreen(rememberNavController())
    }
}
