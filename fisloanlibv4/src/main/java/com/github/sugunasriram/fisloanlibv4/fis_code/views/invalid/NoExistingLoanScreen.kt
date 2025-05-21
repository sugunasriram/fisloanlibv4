package com.github.sugunasriram.fisloanlibv4.fis_code.views.invalid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.components.StartingText
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.negativeGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal30Text700

@Composable
fun NoExistingLoanScreen(displayText:String =stringResource(id = R.string.no_existing_loans),) {
    Column(modifier = Modifier.fillMaxSize().offset(y = (-30).dp), verticalArrangement = Arrangement.Center) {

        Image(
            painter = painterResource(id = R.drawable.error_no_existing_loan),
            contentDescription = "loan Status",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().size(200.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        StartingText(
            text =  displayText,textColor = negativeGray,
            start = 30.dp, end = 30.dp, top = 30.dp, bottom = 5.dp, style = normal30Text700,
            alignment = Alignment.TopCenter
        )

    }
}
@Preview
@Composable
private fun NoExistingLoanScreenPreview() {
    Surface {
        NoExistingLoanScreen()
    }

}