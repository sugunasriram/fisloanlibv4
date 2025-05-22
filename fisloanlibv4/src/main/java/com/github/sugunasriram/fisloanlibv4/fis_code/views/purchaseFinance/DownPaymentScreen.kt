package com.github.sugunasriram.fisloanlibv4.fis_code.views.personalLoan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.FixedTopBottomScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.components.RegisterText
import com.github.sugunasriram.fisloanlibv4.fis_code.navigation.navigateToLoanProcessScreen
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal18Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal32Text700
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.primaryOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.utils.CommonMethods

@Composable
fun DownPaymentScreen(navController: NavHostController, fromFlow: String) {
    val amount = remember { mutableStateOf(TextFieldValue("20000")) }
    var maxAmount = "99000"

    FixedTopBottomScreen(
        navController,
        showHyperText = false,
//        backGroudColorChange = amount.value.text != "",
        primaryButtonText = stringResource(id = R.string.submit),
        onBackClick = { navController.popBackStack() },
        onPrimaryButtonClick = {
            if (amount.value.text.isNotEmpty()) {
                navigateToLoanProcessScreen(
                    navController = navController,
                    transactionId = "Sugu",
                    statusId = 19,
                    responseItem = amount.value.text,
                    offerId = "1234",
                    fromFlow = fromFlow
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RegisterText(
                text = stringResource(id = R.string.enter_downpayment_details),
                style = normal32Text700
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProductDetails(maxAmount)
            Spacer(modifier = Modifier.height(16.dp))
            DownpaymentField(amount.value, maxAmount) { amount.value = it }
        }
    }
}

@Composable
fun ProductDetails(maxAmount: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        border = BorderStroke(color = primaryOrange, width = 1.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.retail_power_tiller),
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Product Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Brand : Tata")
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "Product : Power Tiller Maestro 65P")
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = buildAnnotatedString {
                    append("Price : ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("₹" + CommonMethods().formatWithCommas(maxAmount.toDouble().toInt()) + " ")
                    }
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    ) {
                        append("₹1,10,000 ")
                    }
                    withStyle(style = SpanStyle(color = primaryOrange)) {
                        append("10% OFF")
                    }
                },
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "Sold by : Tradeline", fontWeight = FontWeight.Light, fontSize = 14.sp)
        }
    }
}

@Composable
fun DownpaymentField(
    amount: TextFieldValue,
    maxAmount: String,
    onAmountChange: (TextFieldValue) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Enter the amount you are willing to pay as down payment for this product",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                val valueAsLong = newValue.text.toLongOrNull()
                val maxAmountVal = maxAmount.toLongOrNull()

                if (valueAsLong != null && valueAsLong > 0) {
                    if (valueAsLong <= maxAmountVal!!) {
                        onAmountChange(newValue)
                    } else {
                        CommonMethods().toastMessage(context, "Amount cannot exceed ₹$maxAmount")
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = stringResource(id = R.string.enter_amount),
                    color = hintGray,
                    style = normal18Text400,
                    textAlign = TextAlign.Center
                )
            },
            visualTransformation = VisualTransformation.None,
            textStyle = LocalTextStyle.current,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appOrange,
                unfocusedBorderColor = appOrange,
                cursorColor = appOrange,
                textColor = appBlack
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display amount in words
        val amountInWords =
            amount.text.toLongOrNull()?.let { CommonMethods().numberToWords(it) }
                ?: ""
        if (amountInWords.isNotEmpty()) {
            Text(
                text = "( $amountInWords )",
                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDownpaymentScreen() {
    DownPaymentScreen(rememberNavController(), "Purchase Finance")
}
