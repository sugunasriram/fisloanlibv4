package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CheckBoxText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.LoanStatusTracker
import com.github.sugunasriram.fisloanlibv4.fis_code.components.MultiStyleText
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToBasicDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.errorRed
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text700

@Composable
fun PersonaLoanScreen(navController: NavHostController,fromFlow:String) {
    val context = LocalContext.current
    var checkboxState by remember { mutableStateOf(false) }
    var havingDocuments by remember { mutableStateOf(true) }

    BackHandler { navigateApplyByCategoryScreen(navController) }

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
            if(checkboxState){
                havingDocuments=true
                navigateToBasicDetailsScreen(navController,fromFlow)
            }else{
                havingDocuments=false
            }
        },
        backgroundColor = appWhite
    ) {
        LoanStatusTracker(stepId = 1)
        CenteredMoneyImage(
            image = R.drawable.personal_loan,
            imageSize = 230.dp,
            top = 35.dp
        )
        Text(
            text = stringResource(id = R.string.please_have_following_documents),
            modifier = Modifier.padding(start = 20.dp,end = 20.dp, top = 40.dp, bottom = 15.dp),
            textAlign = TextAlign.Start,
            style = normal16Text700
        )
        MultiStyleText("*  ", appOrange, stringResource(id = R.string.pan_number), appBlack,
            style1 = normal16Text400, style2 = normal16Text400, start=50.dp)
        MultiStyleText("*  ", appOrange, stringResource(id = R.string.adhar_linked_number),
            appBlack,style1 = normal16Text400, style2 = normal16Text400, start=50.dp)
//        MultiStyleText("*  ", appOrange, stringResource(id = R.string.account_aggregator_consent),
//            appBlack, style1 = normal16Text400,style2 = normal16Text400, start=50.dp)
        CheckBoxText(textColor = appOrange,
            style = normal16Text400,
            boxState = checkboxState,
            text = stringResource(id = R.string.i_have_all_above_documents),
            bottom = 0.dp, start = 0.dp, end = 0.dp,top=30.dp) {
                isChecked -> checkboxState = isChecked
            havingDocuments=true
        }
        if (!havingDocuments) {
            StartingText(
                text = stringResource(R.string.please_confirm_you_have_all_the_documents),
                textColor = errorRed, alignment = Alignment.Center
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PersonaLoanScreenPreview(){
    PersonaLoanScreen(rememberNavController(),"Personal")
}