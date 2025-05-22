package com.github.sugunasriram.fisloanlibv4.fis_code.views.gstLoan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.CenteredMoneyImage
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HeaderValueInARow
import com.github.sugunasriram.fisloanlibv4.fis_code.components.HorizontalDivider
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToGstInvoiceLoansScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.gst.GstInvoice
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlueTitle
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods
import kotlinx.serialization.json.Json

private val json = Json { prettyPrint = true }

@Composable
fun InvoiceDetailScreen(navController: NavHostController, fromFlow: String, invoiceId: String) {
    val invoiceDetails = json.decodeFromString(GstInvoice.serializer(), invoiceId)
    BackHandler {
        invoiceDetails.data?.id?.let { id ->
            navigateToGstInvoiceLoansScreen(
                navController = navController,
                fromFlow = fromFlow
            )
        }
    }
    FixedTopBottomScreen(
        navController = navController,
        primaryButtonText = stringResource(id = R.string.next),
        modifier = Modifier.background(Color.White),
        onBackClick = { navController.popBackStack() },
        onPrimaryButtonClick = {
            invoiceDetails.data?.id?.let { id ->
                navigateToLoanProcessScreen(
                    navController = navController,
                    transactionId = "Sugu",
                    statusId = 11,
                    responseItem = "No Need",
                    offerId = id,
                    fromFlow = fromFlow
                )
            }
        }
    ) {
        CenteredMoneyImage(imageSize = 65.dp, top = 15.dp)
        RegisterText(
            text = stringResource(id = R.string.invoice_detail),
            textColor = appBlueTitle,
            top = 15.dp,
            start = 0.dp,
            end = 15.dp,
            style = normal32Text700,
            textAlign = TextAlign.Start
        )

        HeaderValueInARow(
            textHeader = stringResource(id = R.string.invoice_company_name), top = 30.dp,
            textValue = "Padmavati Steel Corporation Pvt. Ltd.", start = 25.dp,
            textColorHeader = hintGray, textColorValue = appBlack,
            headerStyle = normal14Text400, valueStyle = normal14Text400,
            valueTextAlign = TextAlign.Start
        )

        HorizontalDivider(top = 10.dp)
        invoiceDetails.data?.gstinProfile?.forEach { gstProfile ->
            gstProfile?.gstin?.let { gstNumber ->
                HeaderValueInARow(
                    start = 25.dp, textHeader = stringResource(id = R.string.company_gst), top = 30.dp,
                    textValue = gstNumber, textColorHeader = hintGray,
                    textColorValue = appBlack, headerStyle = normal14Text400,
                    valueStyle = normal14Text400, valueTextAlign = TextAlign.Start
                )
                HorizontalDivider(top = 10.dp)
            }
            invoiceDetails.data.invoices?.forEach { invoice ->
                invoice?.value?.let { invoiceValue ->
                    HeaderValueInARow(
                        start = 25.dp, textHeader = stringResource(id = R.string.invoice_amount),
                        top = 30.dp, textValue = "₹ $invoiceValue", textColorHeader = hintGray,
                        textColorValue = appBlack, headerStyle = normal14Text400,
                        valueStyle = normal14Text400, valueTextAlign = TextAlign.Start
                    )
                    HorizontalDivider(top = 10.dp)
                }
                invoice?.ctin?.let { invoiceNumber ->
                    HeaderValueInARow(
                        start = 25.dp, textHeader = stringResource(id = R.string.invoice_number),
                        textValue = invoiceNumber, textColorHeader = hintGray, top = 30.dp,
                        textColorValue = appBlack, headerStyle = normal14Text400,
                        valueStyle = normal14Text400, valueTextAlign = TextAlign.Start
                    )
                    HorizontalDivider(top = 10.dp)
                }
                invoice?.idt?.let { date ->
                    val actualDate = CommonMethods().editingDate(date)
                    HeaderValueInARow(
                        start = 25.dp, textHeader = stringResource(id = R.string.date), top = 30.dp,
                        textValue = actualDate, textColorHeader = hintGray,
                        headerStyle = normal14Text400, valueStyle = normal14Text400,
                        valueTextAlign = TextAlign.Start, textColorValue = appBlack
                    )
                    HorizontalDivider(top = 10.dp)
                }
            }
            gstProfile?.adadr?.forEach { address ->
                address?.addr?.stcd?.let { state ->
                    HeaderValueInARow(
                        start = 25.dp, textHeader = stringResource(id = R.string.state), top = 30.dp,
                        textValue = state, textColorHeader = hintGray,
                        headerStyle = normal14Text400, valueStyle = normal14Text400,
                        valueTextAlign = TextAlign.Start, textColorValue = appBlack
                    )
                    HorizontalDivider(top = 10.dp)
                }
            }
        }
    }
}
