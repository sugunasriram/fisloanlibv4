package com.github.sugunasriram.fisloanlibv4.fiscode.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fiscode.components.BorderCardWithElevation
import com.github.sugunasriram.fisloanlibv4.fiscode.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateApplyByCategoryScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateTOUnexpectedErrorScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToBankKycVerificationScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToDownPaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToFISExitScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstInvoiceDetailScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstInvoiceLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToGstInvoiceLoanScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToKycAnimation
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanAgreementScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToLoanOffersListScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToPfLoanOfferScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToRepaymentScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.navigation.navigateToReviewDetailsScreen
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.bold16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.customGrayColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.customGreenColor
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.greenColour
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.lightGray
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal16Text400
import com.github.sugunasriram.fisloanlibv4.fiscode.ui.theme.normal32Text700

@Composable
fun LoanProcessScreen(
    navController: NavHostController,
    statusId: String,
    responseItem: String,
    transactionId: String,
    offerId: String,
    fromFlow: String
) {
    BackHandler {
//        navigateApplyByCategoryScreen(navController)
        navigateToFISExitScreen(navController, loanId="1234")
    }
    Log.d("test transactionId: ", transactionId)

    val loanProcess = true
    when (statusId.toIntOrNull()) {
        1 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAnnualIncomeScreen(navController, fromFlow)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )

                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        2 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToLoanOffersListScreen(navController, responseItem, fromFlow)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        3 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToKycAnimation(navController, transactionId, offerId, responseItem, fromFlow = fromFlow)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )

                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    textcolorChange = false,
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    processDone = false,
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue

                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    processDone = false,
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    processDone = false,
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    processDone = false,
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue
                )
            }
        }

        4 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToBankDetailsScreen(
                        navController = navController,
                        id = offerId,
                        fromFlow = fromFlow,
                        closeCurrent = false
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    showSubText = false,
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    processDone = false,
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    processDone = false,
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    processDone = false,
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue
                )
            }
        }

        5 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToRepaymentScreen(
                        navController,
                        transactionId,
                        responseItem,
                        offerId,
                        fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = if (fromFlow == "Purchase Finance") R.string.purchase_finance else R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                if (fromFlow != "Purchase Finance") {
                    StatusCard(
                        cardText = stringResource(id = R.string.add_bank_details),
                        image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                        processDone = true,
                        showSubText = false,
                        textcolorChange = false
                    )
                }
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (!loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        6 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    if (responseItem.equals(
                            "No need ResponseItem",
                            ignoreCase = true
                        ) || responseItem.contains("No need ResponseItem", ignoreCase = true)
                    ) {
                        navigateTOUnexpectedErrorScreen(navController, true)
                    } else {
                        navigateToLoanAgreementScreen(
                            navController,
                            transactionId,
                            offerId,
                            fromFlow = fromFlow,
                            responseItem,

                        )
                    }
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (!loanProcess) R.drawable.five_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (!loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        7 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAnnualIncomeScreen(navController, fromFlow)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (!loanProcess) R.drawable.five_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (!loanProcess) R.drawable.six_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (!loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = true,
                    showSubText = false
                )
            }
        }

        8 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAccountAggregatorScreen(navController, responseItem, fromFlow, false)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        9 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToGstInvoiceLoanScreen(
                        navController = navController,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = true,
                    showGstData = true,
                    subHeadText = stringResource(id = R.string.gst_details),
                    subHeadText2 = stringResource(id = R.string.give_consent_for_gst_invoice)
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        10 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToGstInvoiceDetailScreen(
                        navController,
                        fromFlow = fromFlow,
                        invoiceId = offerId
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = false,
                    showGstData = true,
                    subHeadText = stringResource(id = R.string.gst_details),
                    subHeadText2 = stringResource(id = R.string.give_consent_for_gst_invoice)
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        11 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAccountAggregatorScreen(
//                        navController = navController, loanPurpose = offerId,
//                        fromFlow = fromFlow, closeCurrent = true
//                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = false,
                    showGstData = true,
                    showCorrect = true,
                    subHeadText = stringResource(id = R.string.gst_details),
                    subHeadText2 = stringResource(id = R.string.give_consent_for_gst_invoice)
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        12 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToGstInvoiceLoanOfferScreen(
                        navController = navController,
                        offerResponse = responseItem,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        13 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToBankKycVerificationScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = responseItem,
                        offerId = offerId,
                        verificationStatus = "1",
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )

                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        17 -> { // Sugu
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToBankKycVerificationScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = responseItem,
                        offerId = offerId,
                        verificationStatus = "2",
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )

                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        14 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToBankDetailsScreen(
                        navController = navController,
                        id = offerId,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        15 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToRepaymentScreen(
                        navController = navController,
                        transactionId = transactionId,
                        url = responseItem,
                        id = offerId,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (!loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        16 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToLoanAgreementScreen(
                        navController = navController,
                        url = responseItem,
                        transactionId = transactionId,
                        id = offerId,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.emandate),
                    image = if (!loanProcess) R.drawable.five_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (!loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = true,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }

        17 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToReviewDetailsScreen(
                        navController = navController,
                        fromFlow = fromFlow,
                        loanPurpose = "No need"
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.purchase_finance),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = true,
                    showGstData = true,
                    subHeadText = stringResource(id = R.string.basic_detail),
                    subHeadText2 = stringResource(id = R.string.enter_down_payment)
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
                    processDone = false
                )
            }
        }

        //Sugu - don't remove
        //commented to check if its required in SuperApp flow
//        18 -> {
//            FixedTopBottomScreen(
//                navController = navController,
//                showBackButton = false,
//                isSelfScrollable = false,
//                primaryButtonText = stringResource(id = R.string.next),
//                onPrimaryButtonClick = {
//                    navigateToDownPaymentScreen(navController = navController, fromFlow =
//                        fromFlow, verifySessionResponse = null)
//                }
//            ) {
//                StartingText(
//                    text = stringResource(id = R.string.loan_process),
//                    textColor = appBlueTitle,
//                    start = 30.dp,
//                    end = 30.dp,
//                    top = 10.dp,
//                    bottom = 5.dp,
//                    style = normal32Text700
//                )
//
//                StartingText(
//                    text = stringResource(id = R.string.one_time_register),
//                    start = 30.dp,
//                    end = 30.dp,
//                    bottom = 5.dp,
//                    style = normal14Text400,
//                    textAlign = TextAlign.Start
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.purchase_finance),
//                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
//                    processDone = true,
//                    consentData = false,
//                    showGstData = true,
//                    subHeadText = stringResource(id = R.string.basic_detail),
//                    subHeadText2 = stringResource(id = R.string.enter_down_payment_details)
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.select_loan),
//                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
//                    processDone = false
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.complete_kyc),
//                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
//                    processDone = false,
//                    showSubText = false
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.set_up_repayment),
//                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
//                    processDone = false
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.loan_agreement),
//                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.four_image_blue,
//                    processDone = false
//                )
//                StatusCard(
//                    cardText = stringResource(id = R.string.loan_disbursed),
//                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
//                    processDone = false
//                )
//            }
//        }

        19 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAccountAggregatorScreen(
//                        navController = navController, loanPurpose = responseItem, fromFlow = fromFlow
//                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.purchase_finance),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = true,
                    consentData = false,
                    showGstData = true,
                    showCorrect = true,
                    subHeadText = stringResource(id = R.string.basic_detail),
                    subHeadText2 = stringResource(id = R.string.enter_down_payment_details)
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
                    processDone = false
                )
            }
        }

        20 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToPfLoanOfferScreen(
                        navController = navController,
                        offerResponse = responseItem,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    textColor = appBlueTitle,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.purchase_finance),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
                    processDone = false
                )
            }
        }

        21 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToBankKycVerificationScreen(
                        navController = navController,
                        transactionId = transactionId,
                        kycUrl = responseItem,
                        offerId = offerId,
                        verificationStatus = "1",
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    textColor = appBlueTitle,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.purchase_finance),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_blue else R.drawable.three_image_blue,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = true
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.four_image_blue,
                    processDone = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
                    processDone = false
                )
            }
        }

        22 -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
                    navigateToLoanAgreementScreen(
                        navController = navController,
                        url = responseItem,
                        transactionId = transactionId,
                        id = offerId,
                        fromFlow = fromFlow
                    )
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    textColor = appBlueTitle,
                    style = normal32Text700
                )

                StatusCard(
                    cardText = stringResource(id = R.string.purchase_finance),
                    image = if (!loanProcess) R.drawable.one_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    consentData = false,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (!loanProcess) R.drawable.two_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (!loanProcess) R.drawable.three_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (!loanProcess) R.drawable.four_image_black else R.drawable.completed_green_tick,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )

                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (!loanProcess) R.drawable.six_image_black else R.drawable.three_image_blue,
                    processDone = true,
                    showSubText = false,
                    textcolorChange = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.black_rupee_symbol else R.drawable.blue_rupee_symbol,
                    processDone = false
                )
            }
        }

        else -> {
            FixedTopBottomScreen(
                navController = navController,
                showBackButton = false,
                isSelfScrollable = false,
                primaryButtonText = stringResource(id = R.string.next),
                onPrimaryButtonClick = {
//                    navigateToAnnualIncomeScreen(navController, fromFlow)
                }
            ) {
                StartingText(
                    text = stringResource(id = R.string.loan_process),
                    textColor = appBlueTitle,
                    start = 30.dp,
                    end = 30.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    style = normal32Text700
                )

                StartingText(
                    text = stringResource(id = R.string.one_time_register),
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 5.dp,
                    style = normal14Text400,
                    textAlign = TextAlign.Start
                )
                StatusCard(
                    cardText = stringResource(id = R.string.request_a_loan),
                    image = if (loanProcess) R.drawable.one_image_black else R.drawable.one_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.select_loan),
                    image = if (loanProcess) R.drawable.two_image_black else R.drawable.two_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.complete_kyc),
                    image = if (loanProcess) R.drawable.three_image_black else R.drawable.three_image_blue,
                    processDone = false,
                    showSubText = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.add_bank_details),
                    image = if (loanProcess) R.drawable.four_image_black else R.drawable.four_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.set_up_repayment),
                    image = if (loanProcess) R.drawable.five_image_black else R.drawable.five_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_agreement),
                    image = if (loanProcess) R.drawable.six_image_black else R.drawable.six_image_blue,
                    processDone = false
                )
                StatusCard(
                    cardText = stringResource(id = R.string.loan_disbursed),
                    image = if (loanProcess) R.drawable.seven_image_black else R.drawable.seven_image_blue,
                    processDone = false
                )
            }
        }
    }
}

@Composable
fun StatusCard(
    cardText: String,
    @DrawableRes image: Int,
    top: Dp = 15.dp,
    processDone: Boolean = false,
    showSubText: Boolean = true,
    subProcess: Boolean = true,
    consentData: Boolean = true,
    textcolorChange: Boolean = true,
    showGstData: Boolean = false,
    showCorrect: Boolean = false,
    subHeadText: String = stringResource(id = R.string.personal_details),
    subHeadText2: String = stringResource(id = R.string.give_consent_for_bank_data),
    subHeadText3: String = stringResource(id = R.string.give_consent_for_bank_data)
) {
    Box(modifier = Modifier.padding(top = top)) {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
            BorderCardWithElevation(
                borderColor = if (processDone) greenColour else lightGray,
                contentColor = if (processDone) customGreenColor else appWhite,
                start = 0.dp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = cardText,
                    modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, start = 20.dp),
                    style = if (textcolorChange) bold16Text400 else normal16Text400,
                    color = if (processDone) appBlack else customGrayColor
                )
                if (processDone) {
                    if (showSubText) {
                        SubHeading(
                            image = if (consentData) R.drawable.circle_tick_one_blue else R.drawable.completed_circle_image,
                            text = subHeadText,
                            subProcess = subProcess
                        )
                        SubHeading(
                            image = if (showCorrect) {
                                R.drawable.completed_circle_image
                            } else {
                                if (consentData) R.drawable.circle_tick_two_black else R.drawable.circle_tick_two_blue
                            },
                            text = subHeadText2,
                            subProcess = subProcess
                        )
                        if (showGstData) {
                            SubHeading(
                                image = if (showCorrect) R.drawable.circle_tick_three_blue else R.drawable.circle_tick_three_black,
                                text = subHeadText3,
                                subProcess = subProcess
                            )
                        }
                    }
                }
            }
        }
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.image),
            modifier = Modifier
                .size(40.dp)
                .padding(top = 10.dp)
                .align(Alignment.TopStart)
        )
    }
}

@Composable
fun SubHeading(@DrawableRes image: Int, text: String, subProcess: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 5.dp, end = 5.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.image),
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 20.dp, top = 5.dp),
            color = if (subProcess) appBlack else customGrayColor,
            style = normal14Text500
        )
    }
}

@Preview
@Composable
fun LoanProcessScreenPreview() {
    LoanProcessScreen(
        navController = rememberNavController(),
        transactionId = "12232",
        statusId = "19",
        responseItem = "String",
        offerId = "1234",
        fromFlow = "Personal Loan"
    )
}
