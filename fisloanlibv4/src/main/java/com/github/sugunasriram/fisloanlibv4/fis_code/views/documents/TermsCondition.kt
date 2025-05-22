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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenterProgress
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateSignInPage
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.DocumentItem
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.document.TermsConditionResponse
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.backgroundOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal12Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import com.github.sugunasriram.fisloanlibv4.fis_code.viewModel.document.DocumentViewModel

@Preview(showBackground = true)
@Composable
fun PreviewTermsConditionsScreen() {
    val navController = rememberNavController()
    TermsConditionsScreen(navController = navController)
}

@Composable
fun TermsConditionsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val documentViewModel: DocumentViewModel = viewModel()

    val showInternetScreen by documentViewModel.showInternetScreen.observeAsState(false)
    val showTimeOutScreen by documentViewModel.showTimeOutScreen.observeAsState(false)
    val showServerIssueScreen by documentViewModel.showServerIssueScreen.observeAsState(false)
    val unexpectedErrorScreen by documentViewModel.unexpectedError.observeAsState(false)
    val unAuthorizedUser by documentViewModel.unAuthorizedUser.observeAsState(false)
    val checkboxValue: Boolean by documentViewModel.checkBox.observeAsState(false)
    val middleLoan by documentViewModel.middleLoan.observeAsState(false)
    val errorMessage by documentViewModel.errorMessage.collectAsState()
    val isLoading by documentViewModel.isLoading.collectAsState()
    val isLoaded by documentViewModel.isLoaded.collectAsState()
    val termsConditionResponse by documentViewModel.termConditionResponse.collectAsState()
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
            TermsConditionsView(
                navController = navController,
                isLoaded = isLoaded,
                isLoading = isLoading,
                termsConditionResponse = termsConditionResponse,
                context = context,
                documentViewModel = documentViewModel,
                checkboxValue = checkboxValue
            )
        }
    }
}

@Composable
fun TermsConditionsView(
    navController: NavHostController,
    isLoaded: Boolean,
    isLoading: Boolean,
    termsConditionResponse: TermsConditionResponse?,
    documentViewModel: DocumentViewModel,
    context: Context,
    checkboxValue: Boolean
) {
    if (isLoading) {
        CenterProgress()
    } else {
        if (isLoaded) {
            FixedTopBottomScreen(
                navController = navController,
                topBarBackgroundColor = appOrange,
                topBarText = stringResource(R.string.terms_and_conditions),
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                showBottom = false,
                showCheckBox = false,
                checkboxState = checkboxValue,
                onCheckBoxChange = { documentViewModel.onCheckChanged(it) },
                checkBoxText = stringResource(R.string.i_agree_the_terms_and_conditions),
                showDoubleButton = true,
                onPrimaryButtonClick = {
                    if (checkboxValue) {
                        navController.popBackStack()
                    } else {
                        CommonMethods().toastMessage(
                            context = context,
                            toastMsg = context.getString(R.string.please_agree_terms)
                        )
                    }
                },
                onSecondaryButtonClick = { navController.popBackStack() },
                backgroundColor = backgroundOrange
            ) {
                Column(
                    modifier = Modifier
                        .background(appWhite)
                        .padding(2.dp)
                ) {
                    termsConditionResponse?.data?.forEach { termData ->
                        HeaderSection(termData)

                        SubHeadingSection1(termData)

                        SubHeadingSection2(termData)

                        SubHeadingSection3(termData)

                        SubHeadingSection4(termData)

                        SubHeadingSection5(termData)
                    }
                }
            }
        } else {
            documentViewModel.termsCondition(context)
        }
    }
}

@Composable
fun HeaderSection(termData: DocumentItem?) {
    termData?.subheading1?.let { subheading1 ->
        RegisterText(
            text = "A.$subheading1".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph1?.let { paragraph1 ->
        RegisterText(
            text = paragraph1,
            start = 10.dp,
            top = 8.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.paragraph2?.let { paragraph2 ->
        RegisterText(
            text = paragraph2,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.paragraph3?.let { paragraph3 ->
        RegisterText(
            text = paragraph3,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.paragraph4?.let { paragraph4 ->
        RegisterText(
            text = paragraph4,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.paragraph5?.let { paragraph5 ->
        RegisterText(
            text = paragraph5,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.paragraph6?.let { paragraph6 ->
        RegisterText(
            text = paragraph6,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.paragraph7?.let { paragraph7 ->
        RegisterText(
            text = paragraph7,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.paragraph8?.let { paragraph8 ->
        RegisterText(
            text = paragraph8,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.paragraph9?.let { paragraph9 ->
        RegisterText(
            text = paragraph9,
            start = 10.dp,
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
fun SubHeadingSection1(termData: DocumentItem?) {
    termData?.subheading2?.let { subheading2 ->
        RegisterText(
            text = "B.$subheading2".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph10?.let { paragraph10 ->
        RegisterText(
            text = paragraph10,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading3?.let { subheading3 ->
        RegisterText(
            text = "C.$subheading3".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold12Text400,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph11?.let { paragraph11 ->
        RegisterText(
            text = paragraph11,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list1?.let { list1 ->
        RegisterText(
            text = "a.$list1",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list2?.let { list2 ->
        RegisterText(
            text = "b.$list2",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.list3?.let { list3 ->
        RegisterText(
            text = "c.$list3",
            start = 20.dp,
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
fun SubHeadingSection2(termData: DocumentItem?) {
    termData?.subheading4?.let { subheading4 ->
        RegisterText(
            text = "D.$subheading4".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph12?.let { paragraph10 ->
        RegisterText(
            text = paragraph10,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list4?.let { list4 ->
        RegisterText(
            text = "a.$list4",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list5?.let { list5 ->
        RegisterText(
            text = "b.$list5",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list6?.let { list6 ->
        RegisterText(
            text = "c.$list6",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading5?.let { subheading5 ->
        RegisterText(
            text = "E.$subheading5".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph13?.let { paragraph13 ->
        RegisterText(
            text = paragraph13,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list7?.let { list7 ->
        RegisterText(
            text = "a.$list7",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list8?.let { list8 ->
        RegisterText(
            text = "b.$list8",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list9?.let { list9 ->
        RegisterText(
            text = "c.$list9",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list10?.let { list10 ->
        RegisterText(
            text = "d.$list10",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading6?.let { subheading6 ->
        RegisterText(
            text = "F.$subheading6".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph14?.let { paragraph14 ->
        RegisterText(
            text = paragraph14,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list11?.let { list11 ->
        RegisterText(
            text = "a.$list11",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list12?.let { list12 ->
        RegisterText(
            text = "b.$list12",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading7?.let { subheading7 ->
        RegisterText(
            text = "G.$subheading7".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph15?.let { paragraph15 ->
        RegisterText(
            text = paragraph15,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading8?.let { subheading8 ->
        RegisterText(
            text = "H.$subheading8".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph16?.let { paragraph16 ->
        RegisterText(
            text = paragraph16,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.list13?.let { list13 ->
        RegisterText(
            text = "a.$list13",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list14?.let { list14 ->
        RegisterText(
            text = "b.$list14",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.paragraph16a?.let { paragraph16a ->
        RegisterText(
            text = paragraph16a,
            start = 15.dp,
            top = 20.dp,
            style = bold12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SubHeadingSection3(termData: DocumentItem?) {
    termData?.subheading9?.let { subheading9 ->
        RegisterText(
            text = "I.$subheading9".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph17?.let { paragraph17 ->
        RegisterText(
            text = paragraph17,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading10?.let { subheading10 ->
        RegisterText(
            text = "10.$subheading10".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph18?.let { paragraph18 ->
        RegisterText(
            text = paragraph18,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.list15?.let { list15 ->
        RegisterText(
            text = "a.$list15",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list16?.let { list16 ->
        RegisterText(
            text = "b.$list16",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list17?.let { list17 ->
        RegisterText(
            text = "c.$list17",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.list18?.let { list18 ->
        RegisterText(
            text = "d.$list18",
            start = 20.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading11?.let { subheading11 ->
        RegisterText(
            text = "J.$subheading11".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph19?.let { paragraph19 ->
        RegisterText(
            text = paragraph19,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading12?.let { subheading12 ->
        RegisterText(
            text = "K.$subheading12".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph20?.let { paragraph20 ->
        RegisterText(
            text = paragraph20,
            start = 10.dp,
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
fun SubHeadingSection4(termData: DocumentItem?) {
    termData?.subheading13?.let { subheading13 ->
        RegisterText(
            text = "L.$subheading13".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph21?.let { paragraph21 ->
        RegisterText(
            text = paragraph21,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading14?.let { subheading14 ->
        RegisterText(
            text = "M.$subheading14".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph22?.let { paragraph22 ->
        RegisterText(
            text = paragraph22,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading15?.let { subheading15 ->
        RegisterText(
            text = "N.$subheading15".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph23?.let { paragraph23 ->
        RegisterText(
            text = paragraph23,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }

    termData?.subheading16?.let { subheading16 ->
        RegisterText(
            text = "O.$subheading16".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }
    termData?.paragraph24?.let { paragraph24 ->
        RegisterText(
            text = paragraph24,
            start = 10.dp,
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
fun SubHeadingSection5(termData: DocumentItem?) {
    termData?.subheading17?.let { subheading17 ->
        RegisterText(
            text = "P.$subheading17".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph25?.let { paragraph25 ->
        RegisterText(
            text = paragraph25,
            start = 10.dp,
            top = 20.dp,
            style = normal12Text400,
            end = 10.dp,
            boxAlign = Alignment.TopStart,
            textColor = Color.Black,
            textAlign = TextAlign.Justify
        )
    }
    termData?.subheading18?.let { subheading18 ->
        RegisterText(
            text = "Q.$subheading18".uppercase(),
            start = 5.dp,
            top = 20.dp,
            style = bold14Text500,
            boxAlign = Alignment.TopStart,
            end = 5.dp,
            textColor = Color.Black
        )
    }

    termData?.paragraph26?.let { paragraph26 ->
        RegisterText(
            text = paragraph26, start = 10.dp, top = 20.dp, style = normal12Text400,
            end = 10.dp, boxAlign = Alignment.TopStart, textColor = Color.Black,
            textAlign = TextAlign.Justify, bottom = 20.dp
        )
    }
}
