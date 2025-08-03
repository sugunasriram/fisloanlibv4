package com.github.sugunasriram.fisloanlibv4.fiscode.views.personalLoan

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fiscode.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ProcessingAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.personalLoan.PersonalLoanViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@SuppressLint("ResourceType")
@Composable
fun PersonaLoanScreen(navController: NavHostController, fromFlow: String) {
    val context = LocalContext.current
    val personalLoanViewModel: PersonalLoanViewModel = viewModel()
    val showInternetScreen by personalLoanViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by personalLoanViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by personalLoanViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by personalLoanViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by personalLoanViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by personalLoanViewModel.middleLoan.observeAsState(false)
    val navigationToSignIn by personalLoanViewModel.navigationToSignIn.collectAsState()
    val errorMessage by personalLoanViewModel.errorMessage.collectAsState()
    val searchInProgress by personalLoanViewModel.searchInProgress.collectAsState()
    val searchLoaded by personalLoanViewModel.searchLoaded.collectAsState()
    val pLSearchResponse by personalLoanViewModel.pLSearchResponse.collectAsState()

    BackHandler { navigateApplyByCategoryScreen(navController) }

    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
//        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        middleLoan -> MiddleOfTheLoanScreen(navController, errorMessage)
        else -> {
            if (searchInProgress) {
                ProcessingAnimation(
                    text = "Processing Please Wait...",
                    image = R.raw.we_are_currently_processing_hour_glass
                )
            } else {
                if (searchLoaded) {
                    val jsonString = Json.encodeToString(pLSearchResponse)
                    navigateToBasicDetailsScreen(navController, fromFlow)
                } else {
                    FixedTopBottomScreen(
                        navController = navController,
                        topBarBackgroundColor = appOrange,
                        topBarText = stringResource(R.string.personal_loan),
                        showBackButton = true,
                        onBackClick = { navigateApplyByCategoryScreen(navController) },
                        showBottom = true,
                        showSingleButton = true,
                        primaryButtonText = stringResource(R.string.next),
                        onPrimaryButtonClick = {
                            navigateToBasicDetailsScreen(navController, fromFlow)
                        },
                        backgroundColor = appWhite
                    ) {
                        LoanStatusTracker(stepId = 1)
                        CenteredMoneyImage(
                            image = R.drawable.personal_loan,
                            imageSize = 230.dp,
                            top = 35.dp
                        )
                        MultiStyleText(
                            "*  ",
                            appOrange,
                            stringResource(id = R.string.pan_number),
                            appBlack,
                            style1 = normal16Text400,
                            style2 = normal16Text400,
                            start = 50.dp
                        )
                        MultiStyleText(
                            "*  ",
                            appOrange,
                            stringResource(id = R.string.adhar_number),
                            appBlack,
                            style1 = normal16Text400,
                            style2 = normal16Text400,
                            start = 50.dp
                        )
        MultiStyleText("*  ", appOrange, stringResource(id = R.string.mobile_number_linked_with_your_aadhaar),
            appBlack, style1 = normal16Text400,style2 = normal16Text400, start=50.dp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonaLoanScreenPreview() {
    PersonaLoanScreen(rememberNavController(), "Personal")
}
