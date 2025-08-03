package com.github.sugunasriram.fisloanlibv4.fiscode.views.documents

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.DocumentItem
import com.github.sugunasriram.fisloanlibv4.fiscode.network.model.document.PrivacyPolicyResponse
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fiscode.viewModel.document.DocumentViewModel
import com.github.sugunasriram.fisloanlibv4.fiscode.views.invalid.MiddleOfTheLoanScreen

@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    val context = LocalContext.current
    val documentViewModel: DocumentViewModel = viewModel()

    val showInternetScreen by documentViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by documentViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by documentViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by documentViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by documentViewModel.unAuthorizedUser.observeAsState(false)
    val middleLoan by documentViewModel.middleLoan.observeAsState(false)
    val errorMessage by documentViewModel.errorMessage.collectAsState()
    val isLoading by documentViewModel.isLoading.collectAsState()
    val isLoaded by documentViewModel.isLoaded.collectAsState()
    val privacyPolicyResponse by documentViewModel.privacyPolicyResponse.collectAsState()
    val navigationToSignIn by documentViewModel.navigationToSignIn.collectAsState()

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
            PrivacyPolicyView(
                navController = navController,
                isLoaded = isLoaded,
                isLoading = isLoading,
                privacyPolicyResponse = privacyPolicyResponse,
                documentViewModel = documentViewModel,
                context = context
            )
        }
    }
}

@Composable
fun PrivacyPolicyView(
    navController: NavHostController,
    isLoaded: Boolean,
    isLoading: Boolean,
    privacyPolicyResponse: PrivacyPolicyResponse?,
    documentViewModel: DocumentViewModel,
    context: Context
) {
    if (isLoading) {
        CenterProgress()
    } else {
        if (isLoaded) {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.privacy_policy),
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                backgroundColor = backgroundOrange
            ) {
                Column(
                    modifier = Modifier
                        .background(appWhite)
                        .padding(2.dp)
                ) {
                    privacyPolicyResponse?.data?.forEach { privacyData ->

                        PrivacyHeaderSection(privacyData = privacyData)

                        SubHeading2(privacyData = privacyData)

                        SubHeading3(privacyData = privacyData)

                        SubHeading4(privacyData = privacyData)

                        SubHeading5(privacyData = privacyData)

                        SubHeading6(privacyData = privacyData)

                        SubHeading7(privacyData = privacyData)

                        SubHeading8(privacyData = privacyData)

                        SubHeading9(privacyData = privacyData)

                        SubHeading10(privacyData = privacyData)
                    }
                }
            }
        } else {
            documentViewModel.privacyPolicy(context = context)
        }
    }
}

@Composable
fun PrivacyHeaderSection(privacyData: DocumentItem?) {
    privacyData?.subheading1?.let { subheading1 ->
        RegisterText(
            text = "A.$subheading1",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph1?.let { paragraph1 ->
        RegisterText(
            text = paragraph1,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph2?.let { paragraph2 ->
        RegisterText(
            text = paragraph2,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    privacyData?.paragraph3?.let { paragraph3 ->
        RegisterText(
            text = paragraph3,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph4?.let { paragraph4 ->
        RegisterText(
            text = paragraph4,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph5?.let { paragraph5 ->
        RegisterText(
            text = paragraph5,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph6?.let { paragraph6 ->
        RegisterText(
            text = paragraph6,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading2(privacyData: DocumentItem?) {
    privacyData?.subheading2?.let { subheading2 ->
        RegisterText(
            text = "B.$subheading2",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }

    privacyData?.paragraph7?.let { paragraph7 ->
        TextBoldWithNormal(paragraph7, version = "1")
    }

    privacyData?.paragraph8?.let { paragraph8 ->
        TextBoldWithNormal(paragraph8, version = "2")
    }
    privacyData?.paragraph9?.let { paragraph9 ->
        TextBoldWithNormal(paragraph9, version = "3")
    }
    privacyData?.paragraph10?.let { paragraph10 ->
        TextBoldWithNormal(paragraph10, version = "4")
    }
}

@Composable
fun SubHeading3(privacyData: DocumentItem?) {
    privacyData?.subheading3?.let { subheading2 ->
        RegisterText(
            text = "C.$subheading2",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.subheading3a?.let { subheading3a ->
        RegisterText(
            text = "1.$subheading3a",
            start = 20.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph11?.let { paragraph11 ->
        RegisterText(
            text = "1.1.$paragraph11",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph12?.let { paragraph12 ->
        RegisterText(
            text = "1.2.$paragraph12",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph13?.let { paragraph13 ->
        RegisterText(
            text = "1.3.$paragraph13",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.subheading3b?.let { subheading3b ->
        RegisterText(
            text = "2.$subheading3b",
            start = 20.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }

    privacyData?.paragraph14?.let { paragraph14 ->
        RegisterText(
            text = "1.$paragraph14",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph15?.let { paragraph15 ->
        RegisterText(
            text = "2.$paragraph15",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph16?.let { paragraph16 ->
        RegisterText(
            text = "3.$paragraph16",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph17?.let { paragraph17 ->
        RegisterText(
            text = "4.$paragraph17",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph18?.let { paragraph18 ->
        RegisterText(
            text = "C.$paragraph18",
            start = 20.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph19?.let { paragraph19 ->
        RegisterText(
            text = "1.$paragraph19",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph20?.let { paragraph20 ->
        RegisterText(
            text = "2.$paragraph20",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph21?.let { paragraph21 ->
        RegisterText(
            text = "3.$paragraph21",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph22?.let { paragraph22 ->
        RegisterText(
            text = "4.$paragraph22",
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading4(privacyData: DocumentItem?) {
    privacyData?.subheading4?.let { subheading4 ->
        RegisterText(
            text = "D.$subheading4",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph23?.let { paragraph23 ->
        RegisterText(
            text = paragraph23,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph24?.let { paragraph24 ->
        RegisterText(
            text = paragraph24,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph25?.let { paragraph25 ->
        RegisterText(
            text = paragraph25,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph26?.let { paragraph26 ->
        RegisterText(
            text = paragraph26,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph27?.let { paragraph27 ->
        RegisterText(
            text = paragraph27,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph28?.let { paragraph28 ->
        RegisterText(
            text = paragraph28,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading5(privacyData: DocumentItem?) {
    privacyData?.subheading5?.let { subheading5 ->
        RegisterText(
            text = "E.$subheading5",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph29?.let { paragraph29 ->
        RegisterText(
            text = paragraph29,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph30?.let { paragraph30 ->
        RegisterText(
            text = paragraph30,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph31?.let { paragraph31 ->
        RegisterText(
            text = paragraph31,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph32?.let { paragraph32 ->
        RegisterText(
            text = paragraph32,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph33?.let { paragraph33 ->
        RegisterText(
            text = paragraph33,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph34?.let { paragraph34 ->
        RegisterText(
            text = paragraph34,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph35?.let { paragraph35 ->
        RegisterText(
            text = paragraph35,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph36?.let { paragraph36 ->
        RegisterText(
            text = paragraph36,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph37?.let { paragraph37 ->
        RegisterText(
            text = paragraph37,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph38?.let { paragraph38 ->
        RegisterText(
            text = paragraph38,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph39?.let { paragraph39 ->
        RegisterText(
            text = paragraph39,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph40?.let { paragraph40 ->
        RegisterText(
            text = paragraph40,
            start = 30.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading6(privacyData: DocumentItem?) {
    privacyData?.subheading6?.let { subheading6 ->
        RegisterText(
            text = "F.$subheading6",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph41?.let { paragraph41 ->
        RegisterText(
            text = paragraph41,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph42?.let { paragraph42 ->
        RegisterText(
            text = paragraph42,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph43?.let { paragraph43 ->
        RegisterText(
            text = paragraph43,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph44?.let { paragraph44 ->
        RegisterText(
            text = paragraph44,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph45?.let { paragraph45 ->
        RegisterText(
            text = paragraph45,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph46?.let { paragraph46 ->
        RegisterText(
            text = paragraph46,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph47?.let { paragraph47 ->
        RegisterText(
            text = paragraph47,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph48?.let { paragraph48 ->
        RegisterText(
            text = paragraph48,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading7(privacyData: DocumentItem?) {
    privacyData?.subheading7?.let { subheading7 ->
        RegisterText(
            text = "G.$subheading7",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph49?.let { paragraph49 ->
        RegisterText(
            text = paragraph49,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading8(privacyData: DocumentItem?) {
    privacyData?.subheading8?.let { subheading7 ->
        RegisterText(
            text = "H.$subheading7",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph50?.let { paragraph50 ->
        RegisterText(
            text = paragraph50,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph51?.let { paragraph51 ->
        RegisterText(
            text = paragraph51,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading9(privacyData: DocumentItem?) {
    privacyData?.subheading9?.let { subheading9 ->
        RegisterText(
            text = "I.$subheading9",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph52?.let { paragraph50 ->
        RegisterText(
            text = paragraph50,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.paragraph53?.let { paragraph51 ->
        RegisterText(
            text = paragraph51,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeading10(privacyData: DocumentItem?) {
    privacyData?.subheading10?.let { subheading10 ->
        RegisterText(
            text = "J.$subheading10",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph54?.let { paragraph50 ->
        RegisterText(
            text = paragraph50,
            start = 15.dp,
            top = 12.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    privacyData?.subheading11?.let { subheading11 ->
        RegisterText(
            text = "K.$subheading11",
            start = 15.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp
        )
    }
    privacyData?.paragraph55?.let { paragraph55 ->
        RegisterText(
            text = paragraph55, start = 15.dp, top = 12.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify, bottom = 30.dp
        )
    }
}

@Composable
fun TextBoldWithNormal(text: String, version: String) {
    val header = text.substringAfter("\"").substringBefore("\"")
    val cleanedText = text.replace("\"", "")
    Column {
        RegisterText(
            text = "$version. $header",
            start = 25.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            end = 5.dp,
            textAlign = TextAlign.Start
        )
        RegisterText(
            text = cleanedText,
            start = 40.dp,
            top = 5.dp,
            style = normal12Text400,
            textAlign = TextAlign.Justify,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black
        )
    }
}
