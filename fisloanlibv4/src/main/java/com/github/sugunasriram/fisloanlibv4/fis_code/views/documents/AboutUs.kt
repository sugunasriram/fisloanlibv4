package com.github.sugunasriram.fisloanlibv4.fis_code.views.documents

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
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.AboutUsResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.DocumentItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.document.DocumentViewModel

@Composable
fun AboutUsScreen(navController: NavHostController) {

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
    val aboutUsResponse by documentViewModel.aboutUsResponse.collectAsState()
    val navigationToSignIn by documentViewModel.navigationToSignIn.collectAsState()
    when {
        navigationToSignIn -> navigateSignInPage(navController)
        showInternetScreen -> CommonMethods().ShowInternetErrorScreen(navController)
        showTimeOutScreen -> CommonMethods().ShowTimeOutErrorScreen(navController)
        showServerIssueScreen -> CommonMethods().ShowServerIssueErrorScreen(navController)
        unexpectedErrorScreen -> CommonMethods().ShowUnexpectedErrorScreen(navController)
        unAuthorizedUser -> CommonMethods().ShowUnAuthorizedErrorScreen(navController)
        middleLoan -> CommonMethods().ShowMiddleLoanErrorScreen(navController)
        else -> {
            AboutUsView(
                navController = navController, isLoaded = isLoaded, isLoading = isLoading,
                aboutUsResponse = aboutUsResponse, documentViewModel = documentViewModel,
                context = context
            )
        }
    }
}

@Composable
fun AboutUsView(
    navController: NavHostController, isLoaded: Boolean, isLoading: Boolean,
    aboutUsResponse: AboutUsResponse?, documentViewModel: DocumentViewModel,
    context: Context
) {
    if (isLoading) {
        CenterProgress()
    } else {
        if (isLoaded) {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.about_us),
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                backgroundColor = backgroundOrange
            ) {
                Column(
                    modifier = Modifier
                        .background(appWhite)
                        .padding(2.dp)
                ) {
                    aboutUsResponse?.data?.forEach { aboutData ->
                        ParaGraphSection1(aboutData)
                        ParaGraphSection2(aboutData)
                    }
                }
            }
        } else {
            documentViewModel.aboutUs(context = context)
        }
    }
}

@Composable
fun ParaGraphSection1(aboutData: DocumentItem?) {
    aboutData?.paragraph1?.let { paragraph1 ->
        RegisterText(
            text = "welcome to nearshop".uppercase(), start = 5.dp, top = 20.dp,
            style = bold14Text500, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp
        )
        RegisterText(
            text = paragraph1, start = 20.dp, top = 20.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    aboutData?.subheading1?.let { subheading1 ->
        RegisterText(
            text = "1.$subheading1", start = 15.dp, top = 20.dp,
            style = bold12Text400, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp
        )
    }
    aboutData?.paragraph2?.let { paragraph2 ->
        RegisterText(
            text = paragraph2, start = 20.dp, top = 20.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    aboutData?.subheading2?.let { subheading2 ->
        RegisterText(
            text = "2.$subheading2", start = 15.dp, top = 20.dp,
            style = bold12Text400, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp
        )
    }
    aboutData?.paragraph3?.let { paragraph3 ->
        RegisterText(
            text = paragraph3, start = 20.dp, top = 20.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun ParaGraphSection2(aboutData: DocumentItem?) {
    aboutData?.paragraph4?.let { paragraph4 ->
        BoldWithNormalText(text = paragraph4, version = "a")
    }
    aboutData?.paragraph5?.let { paragraph5 ->
        BoldWithNormalText(text = paragraph5, version = "b")
    }
    aboutData?.paragraph6?.let { paragraph6 ->
        BoldWithNormalText(text = paragraph6, version = "c")
    }
    aboutData?.subheading3?.let { subheading3 ->
        RegisterText(
            text = "3.$subheading3", start = 15.dp, top = 20.dp,
            style = bold12Text400, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp
        )
    }
    aboutData?.paragraph7?.let { paragraph7 ->
        BoldWithNormalText(text = paragraph7, version = "a")
    }
    aboutData?.paragraph8?.let { paragraph8 ->
        BoldWithNormalText(text = paragraph8, version = "b")
    }
    aboutData?.paragraph9?.let { paragraph9 ->
        BoldWithNormalText(text = paragraph9, version = "c")
    }
    aboutData?.paragraph10?.let { paragraph10 ->
        BoldWithNormalText(text = paragraph10, version = "d")
    }
    aboutData?.subheading4?.let { subheading4 ->
        RegisterText(
            text = "4.$subheading4", start = 15.dp, top = 20.dp,
            style = bold12Text400, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp
        )
    }
    aboutData?.paragraph11?.let { paragraph11 ->
        RegisterText(
            text = paragraph11, start = 20.dp, top = 20.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify, bottom = 20.dp
        )
    }
}

@Composable
fun BoldWithNormalText(text: String, version: String) {
    val header = text.substringAfter("**").substringBefore("**")
    val primaryHeader = text.substringAfter(":")
    Column {
        RegisterText(
            text = "$version. $header", start = 25.dp, top = 20.dp,
            style = bold12Text400, boxAlign = Alignment.TopStart,
            textColor = Color.Black, end = 5.dp, textAlign = TextAlign.Start
        )
        RegisterText(
            text = primaryHeader, start = 40.dp, top = 5.dp, style = normal12Text400,
            textAlign = TextAlign.Start, end = 10.dp, boxAlign = Alignment.TopStart,
            textColor = Color.Black,
        )
    }
}


