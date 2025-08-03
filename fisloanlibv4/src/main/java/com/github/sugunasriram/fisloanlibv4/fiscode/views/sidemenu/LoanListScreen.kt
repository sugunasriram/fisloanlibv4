package com.github.sugunasriram.fisloanlibv4.fiscode.views.sidemenu

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FullWidthRoundShapedCard
import com.github.sugunasriram.fisloanlibv4.fiscode.components.OnlyReadAbleText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fiscode.components.ScreenWithHamburger
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateLoanStatusDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.hyperTextColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal20Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal35Text700
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.whiteGreenColor

@Composable
fun LoanListScreen(navController: NavHostController) {
    ScreenWithHamburger(isSelfScrollable = true, navController = navController) {
        LazyColumn {
            items(6) { item ->
                if (item == 0) {
                    CenteredMoneyImage(imageSize = 95.dp, top = 10.dp)
                    RegisterText(
                        text = stringResource(id = R.string.loan_list),
                        textColor = appBlueTitle,
                        bottom = 20.dp,
                        top = 20.dp,
                        style = normal35Text700
                    )
                }

                FullWidthRoundShapedCard(
                    onClick = { /*TODO*/ },
                    cardColor = hyperTextColor,
                    bottomPadding = 0.dp,
                    start = 10.dp,
                    end = 10.dp
                ) {
                    FullWidthRoundShapedCard(
                        start = 8.dp,
                        end = 8.dp,
                        top = 5.dp,
                        bottom = 5.dp,
                        cardColor = whiteGreenColor,
                        onClick = { navigateLoanStatusDetailScreen(navController) }
                    ) {
                        OnlyReadAbleText(
                            textHeader = stringResource(id = R.string.loan_amount),
                            textValue = stringResource(id = R.string.loan_amount_value),
                            style = normal20Text400
                        )
                        OnlyReadAbleText(
                            textHeader = stringResource(id = R.string.invoice_no),
                            textValue = stringResource(id = R.string.invoice_value),
                            style = normal20Text400
                        )
                        OnlyReadAbleText(
                            textHeader = stringResource(id = R.string.lender),
                            textValue = stringResource(id = R.string.lender_value),
                            style = normal20Text400
                        )
                    }
                }
            }
        }
    }
}
