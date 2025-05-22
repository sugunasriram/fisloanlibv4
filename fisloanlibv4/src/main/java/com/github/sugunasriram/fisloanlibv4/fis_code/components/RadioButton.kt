package com.github.sugunasriram.fisloanlibv4.fis_code.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.github.sugunasriram.fisloanlibv4.R
import com.github.sugunasriram.fisloanlibv4.fis_code.network.model.auth.BankItem
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appBlack
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appOrange
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.appWhite
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.bold14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.customGrayColor
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.grayD9
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.hintGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.lightishGray
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text400
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal14Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.normal16Text500
import com.github.sugunasriram.fisloanlibv4.fis_code.ui.theme.primaryOrange

@Composable
fun RadioButtonWithText(
    text: String,
    selected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    readOnly: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = { if (!readOnly) onCheckedChange(!selected) },
            colors = RadioButtonDefaults.colors(selectedColor = appOrange),
            enabled = !readOnly
        )
        Text(
            text = text,
            color = customGrayColor,
            style = normal14Text500,
            modifier = if (readOnly) Modifier else Modifier.clickable { onCheckedChange(!selected) }
        )
    }
}

@Composable
fun TextWithRadioButton(
    text: String,
    selected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .background(grayD9, shape = RoundedCornerShape(16.dp))
    ) {
        Text(
            text = text,
            color = appBlack,
            style = normal16Text500,
            modifier = Modifier.padding(start = 10.dp)
                .clickable(onClick = { onCheckedChange(!selected) })
        )
        RadioButton(
            selected = selected,
            onClick = { onCheckedChange(!selected) },
            colors = RadioButtonDefaults.colors(selectedColor = appOrange)
        )
    }
}

@Composable
fun ImageTextWithRadioButton(
    backGroundColor: Color = appWhite,
    radioOptions: ArrayList<BankItem?>,
    top: Dp = 15.dp,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    radioOptions.forEach { data ->
        data?.let {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        data.bankName?.let { onOptionSelected(it) }
                    }
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = top)
                    .background(backGroundColor)
            ) {
                data.imageBank?.let { imageUrl ->
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.bank_icon)
                        }).build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = stringResource(id = R.string.bank_image),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 10.dp)
                    )
                }
                data.bankName?.let { bankName ->
                    Text(
                        text = bankName,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 30.dp, end = 30.dp, bottom = 5.dp),
                        style = bold14Text500,
                        textAlign = TextAlign.Start
                    )
                }

                RadioButton(
                    selected = (data.bankName == selectedOption),
                    modifier = Modifier.padding(all = 8.dp),
                    onClick = { data.bankName?.let { onOptionSelected(it) } },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = primaryOrange
                    )
                )
            }
        }
    }
}

@Composable
fun TextDescriptionWithRadioButton(
    text: String,
    description: String,
    selectedOption: String,
    optionValue: String,
    onSelectOption: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(lightishGray, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp).clickable { onSelectOption() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption == optionValue,
            onClick = onSelectOption,
            colors = RadioButtonDefaults.colors(
                selectedColor = appOrange,
                unselectedColor = appOrange
            )
        )
        Column {
            StartingText(text = text, textColor = appBlack, style = normal16Text500, textAlign = TextAlign.Start)
            Spacer(modifier = Modifier.height(4.dp))
            StartingText(text = description, textColor = hintGray, style = normal14Text400, textAlign = TextAlign.Start)
        }
    }
}
